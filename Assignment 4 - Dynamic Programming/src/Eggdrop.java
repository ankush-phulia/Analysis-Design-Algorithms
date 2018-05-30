import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Eggdrop {

  public static int minDropTests(int n, int k) {

    int[][] matrix = new int[n][k];

    for (int i = 0; i < n; i++) {
      // only 1 egg, n-1 trials
      matrix[i][0] = i - 1;
    }

    for (int i = 0; i < k; i++) {
      // only 1 floor
      matrix[0][i] = 0;
    }

    for (int i = 1; i < n; i++) {
      for (int j = 1; j < k; j++) {
        matrix[i][j] = 0;
      }
    }

    for (int i = 1; i < k; i++) {
      // for all the eggs
      for (int j = 2; j < n; j++) {
        // from all floors
        matrix[j][i] = Integer.MAX_VALUE;
        for (int l = 2; l <= j; l++) {
          // drop egg from lth floor
          int subSol = 1 + Math.max(matrix[l - 1][i - 1], matrix[j - l + 1][i]);
          matrix[j][i] = Math.min(matrix[j][i], subSol);
        }
      }
    }
    if (k == 1) {
      return matrix[n - 1][k - 1] + 1;
    } else {
      return matrix[n - 1][k - 1];
    }
  }

  public static void main(String[] args) throws IOException {

    File input_file = new File("input.txt");
    Scanner s = new Scanner(input_file);

    String[] nk = s.nextLine().split(",");
    int n = Integer.parseInt(nk[0]);
    int k = Integer.parseInt(nk[1]);
    s.close();

    Integer output = minDropTests(n, k);
    System.out.println(output);

    FileWriter fwrite = new FileWriter("output.txt");
    fwrite.write(output.toString());
    fwrite.close();
  }
}
