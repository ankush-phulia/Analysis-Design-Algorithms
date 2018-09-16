import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Tiling {

    static boolean hasPath(
            boolean[] visited, ArrayList<HashSet<Integer>> graph, int s, int t, int parent[]) {
        //		int n = graph.size();
        visited[s] = true;

        for (int v : graph.get(s)) {
            if (v == t) {
                parent[v] = s;
                return true;
            } 
            else if (!visited[v]) {
                //				visited[v] = true;
                parent[v] = s;
                if (hasPath(visited, graph, v, t, parent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int maxFlow(ArrayList<HashSet<Integer>> graph, int s, int t) {
        int n = graph.size();
        int u, v;
        int flow = 0;
        int parent[] = new int[n];
        boolean[] visited = new boolean[n];
        while (hasPath(visited, graph, s, t, parent)) {
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                graph.get(u).remove(v);
                graph.get(v).add(u);
                visited[v] = false;
            }
            flow++;
        }
        for (int i = 1; i < n; i++) {
            visited[i] = false;
        }
        while (hasPath(visited, graph, s, t, parent)) {
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                graph.get(u).remove(v);
                graph.get(v).add(u);
                visited[v] = false;
            }
            flow++;
        }

        return flow;
    }

    public static void main(String[] args) throws IOException {

        File input_file = new File("input.txt");
        Scanner s = new Scanner(input_file);

        int n = Integer.parseInt(s.nextLine());
        boolean[][] grid = new boolean[n][n];
        int cnt1 = 0;
        int cnt2 = 0;
        for (int i = 0; i < n; i++) {
            String row = s.nextLine();
            for (int j = 0; j < n; j++) {
                grid[i][j] = (row.charAt(j) == '1');
                if (!grid[i][j]) {
                    if ((i + j) % 2 == 0) {
                        cnt1++;
                    }
                    else {
                        cnt2++;
                    }
                }
            }
        }
        s.close();

        ArrayList<HashSet<Integer>> graph = new ArrayList<HashSet<Integer>>(n * n + 2);
        HashSet<Integer> source = new HashSet<Integer>();
        graph.add(source);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                HashSet<Integer> adj = new HashSet<Integer>();
                if (grid[i][j]) {
                    if ((i + j) % 2 == 0) {
                        graph.get(0).add(n * i + j + 1); // from source
                        if (i > 0 && grid[i - 1][j]) { // just above
                            adj.add(1 + n * (i - 1) + j);
                        }
                        if (i < n - 1 && grid[i + 1][j]) { // just below
                            adj.add(1 + n * (i + 1) + j);
                        }
                        if (j > 0 && grid[i][j - 1]) { // just left
                            adj.add(n * (i) + j);
                        }
                        if (j < n - 1 && grid[i][j + 1]) { // just right
                            adj.add(n * (i) + j + 2);
                        }
                    }
                    else { // to sink
                        adj.add(n * n + 1);
                    }
                }
                graph.add(adj);
            }
        }
        HashSet<Integer> sink = new HashSet<Integer>();
        graph.add(sink);
        String out = "";

        if (cnt1 - cnt2 != 0) {
            out = "0";
        } else {
            long time = System.currentTimeMillis();
            int flow = (maxFlow(graph, 0, n * n + 1));
            System.out.println(flow);
            if (flow == (n * n - cnt1 - cnt2) / 2) {
                out += "1\n";
                int x, y, xx, yy;
                int adj = 0;
                for (int i = 1; i < n * n; i++) {
                    x = (i - 1) % n + 1;
                    y = (i - 1) / n + 1;
                    if ((x + y) % 2 == 1 && !graph.get(i).isEmpty()) {
                        for (int a : graph.get(i)) {
                            adj = a;
                            break;
                        }
                        xx = ((adj - 1) % n + 1);
                        yy = ((adj - 1) / n + 1);

                        out += ("(" + x + "," + y + ")" + "(" + xx + "," + yy + ")\n");
                    }
                }
            } 
            else {
                out = "0";
            }
            System.out.println("Time taken : " + (System.currentTimeMillis() - time));
        }

        FileWriter fwrite = new FileWriter("output.txt");
        fwrite.write(out);
        fwrite.close();
    }
}
