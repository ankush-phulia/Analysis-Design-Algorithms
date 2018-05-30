import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

    int V;
    HashMap<Integer, ArrayList<Integer>> adjList;

    public Graph() {
        this.V = 0;
        this.adjList = new HashMap<Integer, ArrayList<Integer>>();
    }

    public void addVertex(Integer v) {
        this.V++;
        this.adjList.put(v, new ArrayList<Integer>());
    }

    public boolean searchVertex(Integer v) {
        return this.adjList.containsKey(v);
    }

    public void addEdge(Integer v1, Integer v2) {
        if (searchVertex(v1)) {
            this.adjList.get(v1).add(v2);
        } else {
            System.out.println("Vertex " + v1 + " not present in the Graph");
        }
    }
}
