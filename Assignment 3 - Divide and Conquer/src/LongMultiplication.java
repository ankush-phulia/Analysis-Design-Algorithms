import java.io.IOException;
import java.util.ArrayList;

public class LongMultiplication {

    public static ArrayList<Boolean> add(int n, ArrayList<Boolean> a, ArrayList<Boolean> b) {
        ArrayList<Boolean> c = new ArrayList<Boolean>();
        boolean carry_in = false;
        for (int i = n - 1; i >= 0; i--) {
            boolean aa = a.get(i);
            boolean bb = b.get(i);
            boolean s = aa ^ bb ^ carry_in;
            c.add(0, s);
            carry_in = (aa && bb) || (aa && carry_in) || (bb && carry_in);
        }
        c.add(0, carry_in);
        return c;
    }

    public static void shift(int s, ArrayList<Boolean> a) {
        for (int i = 0; i < s; i++) {
            a.add(false);
        }
    }

    public static ArrayList<Boolean> multiply(int n, ArrayList<Boolean> a, ArrayList<Boolean> b) {
        ArrayList<Boolean> c = new ArrayList<Boolean>();
        for (int i = 0; i < n; i++) {
            c.add(false);
        }

        for (int i = n - 1; i >= 0; i--) {
            if (b.get(i)) {
                c = add(a.size(), a, c);
                a.add(false);
            } else {
                c.add(0, false);
                a.add(false);
            }
        }
        if (!c.get(0)) {
            c.remove(0);
        }
        return c;
    }

    public static void main(String[] args) throws IOException {

        /*File input_file = new File("input.txt");
        Scanner s = new Scanner(input_file);

        int n = Integer.parseInt(s.nextLine());
        String a = s.nextLine();
        String b = s.nextLine();
        s.close();*/

        int n = 1;
        for (int p = 1; p <= 16; p++) {

            ArrayList<Boolean> numA = new ArrayList<Boolean>();
            ArrayList<Boolean> numB = new ArrayList<Boolean>();

            for (int i = 0; i < n; i++) {
                numA.add(true);
                numB.add(true);
            }

            Long start = System.currentTimeMillis();

            ArrayList<Boolean> numC = multiply(n, numA, numB);

            Long end = System.currentTimeMillis();

            System.out.println(n + "," + (end - start));

            n *= 2;
        }
    }
}

 /*ArrayList<Boolean> numA = new ArrayList<Boolean>();
 ArrayList<Boolean> numB = new ArrayList<Boolean>();

 for (int i = 0;i<n;i++){
   numA.add((Character.getNumericValue(a.charAt(i)))==1);
   numB.add(Character.getNumericValue(b.charAt(i))==1);
 }

 Long start = System.currentTimeMillis();

 ArrayList<Boolean> numC = multiply(n,numA,numB);

 Long end = System.currentTimeMillis();

 System.out.println("Time taken: " + (end - start) + "ms");

 String output = "";
 for (int i =0;i<numC.size();i++){
   if (numC.get(i)){
     output += (1);
   }
   else{
     output += (0);
   }
 }

 FileWriter fwrite = new FileWriter("output.txt");
 fwrite.write(output);
 fwrite.close();*/
