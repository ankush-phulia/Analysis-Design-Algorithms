import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class check {

  public static void main(String[] args) throws IOException {

    File input_file = new File("input.txt");
    Scanner s = new Scanner(input_file);

    int n = Integer.parseInt(s.nextLine());
    int[][] grid = new int[n][n];
    int cnt1 = 0;
    int cnt2 = 0;
    for (int i = 0; i < n; i++) {
      String row = s.nextLine();
      for (int j = 0; j < n; j++) {
        grid[i][j] = (row.charAt(j) - '0');
      }
    }
    s.close();

    input_file = new File("output.txt");
    s = new Scanner(input_file);
    s.nextLine();
    while (s.hasNextLine()) {
      String str = s.nextLine();
      if (!s.equals("") || !s.equals(" ")) {
        String[] tile = str.split("[(),]");
        // for (int i = 0;i<6;i++){
        // 	System.out.println("yo" + tile[i]);
        // }
        grid[Integer.parseInt(tile[1]) - 1][Integer.parseInt(tile[2]) - 1]++;
        grid[Integer.parseInt(tile[4]) - 1][Integer.parseInt(tile[5]) - 1]++;
      }
    }
    s.close();

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(grid[i][j]);
      }
      System.out.println();
    }
  }
}
