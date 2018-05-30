public class Pair implements Comparable<Pair> {

  int key;
  int value;

  public Pair(int k, int v) {
    this.key = k;
    this.value = v;
  }

  @Override
  public int compareTo(Pair o) {
    if (this.value > o.value) {
      return 1;
    } else if (this.value < o.value) {
      return -1;
    }
    return 0;
  }
}
