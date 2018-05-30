import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Adjacency_Matrix {

  public static int[][] AdjMatrix;

  public static int checker(Pair[] array) {
    for (Pair a : array) {
      if (a.value != 0) {
        return 0;
      }
    }
    return 1;
  }

  public static int Havels(int v, Pair[] outdeg, Pair[] indeg) {
    if (v > 0) {
      int d = outdeg[v - 1].value;
      outdeg[v - 1].value = 0;
      for (int i = indeg.length - 1; i >= indeg.length - d; i--) {
        indeg[i].value--;
        AdjMatrix[outdeg[v - 1].key][indeg[i].key]++;
      }
      Arrays.sort(indeg);
      return Havels(v - 1, outdeg, indeg);
    } else {
      return checker(indeg) * checker(outdeg);
    }
  }

  public static void main(String[] args) throws IOException {

    File input_file = new File("input.txt");
    Scanner s = new Scanner(input_file);

    int V = Integer.parseInt(s.nextLine());
    String r_sums = s.nextLine();
    String c_sums = s.nextLine();
    s.close();

    String[] row_sums = r_sums.split(",");
    String[] col_sums = c_sums.split(",");

    Pair[] out_degrees = new Pair[V];
    Pair[] in_degrees = new Pair[V];
    AdjMatrix = new int[V][V];

    for (int i = 0; i < V; i++) {
      out_degrees[i] = new Pair(i, Integer.parseInt(row_sums[i]));
      in_degrees[i] = new Pair(i, Integer.parseInt(col_sums[i]));
    }

    Arrays.sort(out_degrees);
    Arrays.sort(in_degrees);

    int chk = Havels(V, out_degrees, in_degrees);

    FileWriter fwrite = new FileWriter("output.txt");

    fwrite.write(chk + "\n");
    if (chk == 1) {
      for (int i = 0; i < V; i++) {
        for (int j = 0; j < V - 1; j++) {
          fwrite.write(AdjMatrix[i][j] + ",");
        }
        fwrite.write(AdjMatrix[i][V - 1] + "\n");
      }
    }
    fwrite.close();
  }
}
