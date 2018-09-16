import java.io.IOException;
import java.util.ArrayList;

public class Karatsuba {

    public static void shift(int s, ArrayList<Integer> a) {
        for (int i = 0; i < s; i++) {
            a.add(0);
        }
    }

    public static void pad(int s, ArrayList<Integer> a) {
        for (int i = 0; i < s; i++) {
            a.add(0, 0);
        }
    }

    public static void padto(int s, ArrayList<Integer> a) {
        if (a.size() < s) {
            while (a.size() < s) {
                a.add(0, 0);
            }
        }
    }

    public static ArrayList<Integer> complement(ArrayList<Integer> a) {
        ArrayList<Integer> c = new ArrayList<Integer>();
        for (int i = 0; i < a.size(); i++) {
            c.add((a.get(i) + 1) % 2);
        }
        return c;
    }

    public static ArrayList<Integer> add(ArrayList<Integer> a, ArrayList<Integer> b, int carry_in) {
        int lenA = a.size();
        int lenB = b.size();
        if (lenA > lenB) {
            pad(lenA - lenB, b);
        } 
        else {
            pad(lenB - lenA, a);
        }
        ArrayList<Integer> c = new ArrayList<Integer>();
        int carry = carry_in;
        for (int i = a.size() - 1; i >= 0; i--) {
            int s = a.get(i) + b.get(i) + carry;
            c.add(0, (s % 2));
            carry = s / 2;
        }
        c.add(0, carry);
        if (carry_in == 1) {
            c.remove(0);
        } 
        else if (c.get(0) == 0) {
            c.remove(0);
        }
        return c;
    }

    public static ArrayList<Integer> kmultiply(int n, ArrayList<Integer> a, ArrayList<Integer> b) {
        if (n == 0) {
            ArrayList<Integer> c = new ArrayList<Integer>();
            c.add(0);
            return c;
        } 
        else if (n == 1) {
            ArrayList<Integer> c = new ArrayList<Integer>();
            c.add((a.get(0)) * (b.get(0)));
            return c;
        } 
        else {
            int exp = (n - ((n + 1) / 2));
            ArrayList<Integer> al = new ArrayList<Integer>(a.subList(0, (n + 1) / 2));
            ArrayList<Integer> ar = new ArrayList<Integer>(a.subList((n + 1) / 2, n));
            ArrayList<Integer> bl = new ArrayList<Integer>(b.subList(0, (n + 1) / 2));
            ArrayList<Integer> br = new ArrayList<Integer>(b.subList((n + 1) / 2, n));

            ArrayList<Integer> Z2 = kmultiply((n + 1) / 2, al, bl);
            ArrayList<Integer> Z0 = kmultiply(exp, ar, br);
            ArrayList<Integer> sa = add(al, ar, 0);
            ArrayList<Integer> sb = add(bl, br, 0);
            padto(sa.size(), sb);
            padto(sb.size(), sa);

            ArrayList<Integer> P = kmultiply(sa.size(), sa, sb);
            ArrayList<Integer> S1 = add(Z2, Z0, 0);
            padto(P.size(), S1);
            padto(S1.size(), P);
            shift(2 * exp, Z2);
            shift(exp, Z1);
            ArrayList<Integer> S = add(Z2, Z0, 0);
            return add(S, Z1, 0);
        }
    }

    public static void main(String[] args) throws IOException {

        int n = 1;
        for (int p = 0; p < 17; p++) {

            ArrayList<Integer> numA = new ArrayList<Integer>();
            ArrayList<Integer> numB = new ArrayList<Integer>();

            for (int i = 0; i < n; i++) {
                numA.add(1);
                numB.add(1);
            }

            Long start = System.currentTimeMillis();

            ArrayList<Integer> numC = kmultiply(n, numA, numB);

            Long end = System.currentTimeMillis();

            System.out.println(n + "," + (end - start));
            n *= 2;
        }
    }
}
