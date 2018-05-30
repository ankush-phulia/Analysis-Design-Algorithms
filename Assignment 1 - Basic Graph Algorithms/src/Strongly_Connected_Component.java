import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class Strongly_Connected_Component {

    public static int getID(String s) {
        return Integer.parseInt(s.substring(1));
    }

    public static void TarjanSCC(
            Graph graph,
            int u,
            int time,
            int[] disc,
            int[] low,
            boolean[] instack,
            Stack<Integer> stack) {
        disc[u] = low[u] = time;
        stack.add(u);
        instack[u] = true;

        Iterator<Integer> it = graph.adjList.get(u).iterator();
        while (it.hasNext()) {
            int v = it.next();
            if (disc[v] == 0) {
                TarjanSCC(graph, v, (time + 1), disc, low, instack, stack);
                low[u] = Math.min(low[u], low[v]);
            } else if (instack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            while (stack.peek() != u) {
                int h = stack.pop();
                System.out.print(h + " ");
                instack[h] = false;
            }
            System.out.println(stack.pop());
            instack[u] = false;
        }
    }

    public static void main(String[] args) {
        File input_file = new File("input1.txt");
        try {
            Scanner s = new Scanner(input_file);

            Graph graph = new Graph();
            int V = Integer.parseInt(s.nextLine());
            for (int i = 1; i < V + 1; i++) {
                String line = s.nextLine();
                String[] adjVertices = line.split("->");
                int vertex = getID(adjVertices[0]);
                graph.addVertex(vertex);
                ;
                for (int j = 1; j < adjVertices.length; j++) {
                    graph.addEdge(vertex, getID(adjVertices[j]));
                }
            }
            int u = getID(s.nextLine());
            s.close();

            int[] disc = new int[V + 1];
            int[] low = new int[V + 1];
            Arrays.fill(low, V + 2);
            boolean[] instack = new boolean[V + 1];
            TarjanSCC(graph, u, 1, disc, low, instack, new Stack<Integer>());

            File output_file = new File("output.txt");
            FileWriter fwrite = new FileWriter(output_file);

            fwrite.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
