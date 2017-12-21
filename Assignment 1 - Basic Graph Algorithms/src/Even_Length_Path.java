import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Even_Length_Path {

	public static int getID(String s){
		return Integer.parseInt(s.substring(1));
	}
	
	public static void DFSmod(Graph graph, int u, boolean[] even_path,
			boolean[] visited, boolean mod){
		visited[u] = true;
		if (!even_path[u]){ 
			even_path[u] = !mod;
		}
		Iterator<Integer> it = graph.adjList.get(u).iterator();
		while (it.hasNext()){
			int v = it.next();
			if (!visited[v]){
				DFSmod(graph,v,even_path,visited,!mod);
			}
			else{
				if (!even_path[v]){
					even_path[v] = !even_path[u];
					DFSmod(graph,v,even_path,visited,!mod);
				}
				else if(even_path[v] && !mod){
					DFSmod(graph,v,even_path,visited,!mod);
				}				
			}
		}		
	}	
	
	public static void main(String[] args) {
		
		File input_file = new File("input2.txt");
		try {			
			Scanner s = new Scanner(input_file);
			
			Graph graph = new Graph();
			int V = Integer.parseInt(s.nextLine());
			for (int i = 1;i<V+1;i++){
				String line = s.nextLine();
				String[] adjVertices = line.split("->");
				int vertex = getID(adjVertices[0]);
				graph.addVertex(vertex);;
				for (int j = 1;j<adjVertices.length;j++){
					graph.addEdge(vertex,getID(adjVertices[j]));
				}
			}			
			int u = getID(s.nextLine());
			s.close();
			
			boolean[] even_path = new boolean[V+1];
			boolean[] visited = new boolean[V+1];
			DFSmod(graph,u,even_path,visited,false);
			
			int cnt = 0;
			ArrayList<String> Result = new ArrayList<String>();
			for(int i = 1;i<V+1;i++)
				if (even_path[i]){
					cnt++;
					Result.add("v"+i);
				}
			
			//File output_file = new File("output.txt");
			FileWriter fwrite = new FileWriter("output.txt");
			fwrite.write(cnt+"\n");
			String result = "";
			for (int i = 0;i<Result.size() - 1 ;i++){
				result += Result.get(i)+",";
			}
			result += Result.get(Result.size()-1);
			fwrite.write(result);
			fwrite.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
