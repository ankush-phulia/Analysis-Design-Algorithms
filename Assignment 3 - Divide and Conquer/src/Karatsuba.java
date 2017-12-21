import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Karatsuba {

	public static void shift(int s,ArrayList<Integer> a){
		for (int i = 0;i<s;i++){
			a.add(0);
		}
	}
	
	public static void pad(int s,ArrayList<Integer> a){
		for (int i = 0;i<s;i++){
			a.add(0,0);
		}
	}
	
	public static void padto(int s,ArrayList<Integer> a){
		if (a.size()<s){
			while (a.size()<s){
				a.add(0,0);
			}
		}		
	}
	
	public static ArrayList<Integer> complement(ArrayList<Integer> a){
		ArrayList<Integer> c = new ArrayList<Integer>();
		for (int i = 0;i<a.size();i++){
			c.add((a.get(i)+1)%2);
		}
		return c;
	}
	
	public static ArrayList<Integer> add(ArrayList<Integer> a, ArrayList<Integer> b, int carry_in){
		int lenA = a.size();
		int lenB = b.size();
		if (lenA>lenB){
			pad(lenA-lenB,b);
		}
		else{
			pad(lenB-lenA,a);
		}
		ArrayList<Integer> c = new ArrayList<Integer>();
		int carry = carry_in;
		for (int i = a.size()-1;i>=0;i--){
			int s = a.get(i) + b.get(i) + carry;
			c.add(0,(s%2));
			carry = s/2;
		}
		c.add(0,carry);
		if (carry_in == 1){
			c.remove(0);
		}
		else if (c.get(0) == 0){
			c.remove(0);
		}
		return c;
	}
	
	public static ArrayList<Integer> kmultiply(int n,ArrayList<Integer> a,ArrayList<Integer> b){
		if (n==0){
			//System.out.println("length is 0");
			ArrayList<Integer> c = new ArrayList<Integer>();
			c.add(0);
			return c;
		}
		else if (n==1){
			//System.out.println("length is 1");
			ArrayList<Integer> c = new ArrayList<Integer>();
			c.add((a.get(0))*(b.get(0)));
			return c;
		}
		else{
			//System.out.println("length is more");
			int exp = (n-((n+1)/2));
			ArrayList<Integer> al =  new ArrayList<Integer>(a.subList(0, (n+1)/2));
			//System.out.println("al size : " + al.size());
			ArrayList<Integer> ar =  new ArrayList<Integer>(a.subList((n+1)/2, n));
			//System.out.println("ar size : " + ar.size());
			ArrayList<Integer> bl =  new ArrayList<Integer>(b.subList(0, (n+1)/2));
			//System.out.println("bl size : " + bl.size());
			ArrayList<Integer> br =  new ArrayList<Integer>(b.subList((n+1)/2, n));
			//System.out.println("br size : " + br.size());
			ArrayList<Integer> Z2 = kmultiply((n+1)/2,al,bl);
//			System.out.print("Z2 calculated : " + Z2.size() + " ");
//			for (int i =0;i<Z2.size();i++){
//				System.out.print(Z2.get(i));
//			}
//			System.out.println();
			ArrayList<Integer> Z0 = kmultiply(exp,ar,br);
//			System.out.print("Z0 calculated : " + Z0.size() + " ");
//			for (int i =0;i<Z0.size();i++){
//				System.out.print(Z0.get(i));
//			}
//			System.out.println();
			ArrayList<Integer> sa = add(al,ar,0);
//			System.out.print("sa calculated " + sa.size() + " ");
//			for (int i =0;i<sa.size();i++){
//				System.out.print(sa.get(i));
//			}
//			System.out.println();
			ArrayList<Integer> sb = add(bl,br,0);
//			System.out.print("sb calculated " + sb.size() + " ");
//			for (int i =0;i<sb.size();i++){
//				System.out.print(sb.get(i));
//			}
//			System.out.println();
			padto(sa.size(),sb);
			padto(sb.size(),sa);			
			ArrayList<Integer> P = kmultiply(sa.size(),sa,sb);
//			System.out.print("P calculated " + P.size() + " ");
//			for (int i =0;i<P.size();i++){
//				System.out.print(P.get(i));
//			}
//			System.out.println();
			ArrayList<Integer> S1 = add(Z2,Z0,0);
//			System.out.print("S1 calculated " + S1.size() + " ");
//			for (int i =0;i<S1.size();i++){
//				System.out.print(S1.get(i));
//			}
//			System.out.println();
			padto(P.size(),S1);
			padto(S1.size(),P);
			ArrayList<Integer> Z1 = add(P,complement(S1),1);
//			System.out.print("Z1 calculated " + Z1.size() + " ");
//			for (int i =0;i<Z1.size();i++){
//				System.out.print(Z1.get(i));
//			}
//			System.out.println();
//			System.out.println(exp);
			shift(2*exp,Z2);
//			System.out.print("Z2 calculated : " + Z2.size() + " ");
//			for (int i =0;i<Z2.size();i++){
//				System.out.print(Z2.get(i));
//			}
//			System.out.println();
			shift(exp,Z1);
			ArrayList<Integer> S = add(Z2,Z0,0);
			return add(S,Z1,0);
		}		
		
	}

	public static void main(String[] args) throws IOException {

		/*File input_file = new File("input.txt");
		Scanner s = new Scanner(input_file);
		
		int n = Integer.parseInt(s.nextLine());
		String a = s.nextLine();
		String b = s.nextLine();
		s.close();
		ArrayList<Integer> numA = new ArrayList<Integer>();
		ArrayList<Integer> numB = new ArrayList<Integer>();
		
		for (int i = 0;i<n;i++){
			numA.add(Character.getNumericValue(a.charAt(i)));
			numB.add(Character.getNumericValue(b.charAt(i)));
		}
		
//		numA.add(1);
//		numA.add(0);
//		numA.add(0);
//		numA.add(1);
//		numB.add(1);
//		numB.add(1);
//		numB.add(0);
//		numB.add(1);
	
		Long start = System.currentTimeMillis();
		
		//ArrayList<Integer> numC = add(numA,numB,0);	
		ArrayList<Integer> numC = kmultiply(numA.size(),numA,numB);
		
		Long end = System.currentTimeMillis();
		
		System.out.println("Time taken: " + (end - start) + "ms");
		
		if (numC.get(0) == 0){
			while (numC.get(0) == 0){
				numC.remove(0);
			}
		}		
		
		String output = "";
		
		for (int i = 0;i<numC.size();i++){
			output += numC.get(i);
			//System.out.print(numC.get(i));
		}
				
		FileWriter fwrite = new FileWriter("output.txt");		
		fwrite.write(output);
		fwrite.close();*/
		int n = 1;
		for (int p = 0;p<17;p++){
			
			ArrayList<Integer> numA = new ArrayList<Integer>();
			ArrayList<Integer> numB = new ArrayList<Integer>();
			
			for (int i = 0;i<n;i++){
				numA.add(1);
				numB.add(1);
			}
			
			Long start = System.currentTimeMillis();
			
			ArrayList<Integer> numC = kmultiply(n,numA,numB);		
			
			Long end = System.currentTimeMillis();
			
			System.out.println(n + "," + (end - start));
			n *= 2;
		}
	}

}
