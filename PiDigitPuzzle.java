import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PiDigitPuzzle {

	// initialString: the starting state. Note that 0s are added at the front and end
	// numMovesAllowed: set to 12 if you only want solutions of up to 12 steps 
	// numIterations: how long you want this to go on for
	static String startState = "3388766112";
	static int numMovesAllowed = 4;
	static int numIterations = 100_000_000;

	// dont't touch the rest unless you know what you are doing
	static String initialString = "0" + startState + "0";
	static Random r = new Random();

	
	public static void main(String[] args) {
		int win = 0;
		for (int attempts = 0; attempts < numIterations; attempts++) {
			char[] c = initialString.toCharArray();
			int[] a = new int[c.length];
			for (int i = 0; i < c.length; i++)
				a[i] = c[i] - '0';		
			solve(a, numMovesAllowed);
			//if (almostEqual(a)) break;
			//if (allEqual(a)) win++;
			if (allEqual(a))
				break;
			
			System.out.println(win + "/" + attempts);
		}
	}
	
	public static void solve(int[] a, int movesLeft) {
		
		int counter = 0;
		while (movesLeft > 0) {
			if (allEqual(a)) return;
			int one = 1 + r.nextInt(startState.length());
			int two = 1 + r.nextInt(startState.length());
			int left = Math.min(one, two);
			int right = Math.max(one, two);
			while (left > 0 && a[left] == a[left - 1]) left--;
			while (right < a.length - 1 && a[right] == a[right + 1]) right++;
			if (isValid(a, left, right) == false) continue;
			int operation;
			if (left == 0) operation = a[right + 1] - a[right];
			else operation = a[left - 1] - a[left];
			for (int i = left; i <= right; i++) a[i] += operation;
			movesLeft--;
			System.out.println(++counter + " " + Arrays.toString(a));
		}
	}

	public static boolean isValid(int[] a, int left, int right) {
		if (left == 0 && right == a.length-1) {
			for (int i = left; i <= right; i++) {
				if (a[i] != a[left]) return false;
			}
			return true;
		}
		if (left == 0) {
			int operation = a[right + 1] - a[right];

			for (int i = left; i <= right; i++) {
				if (a[i] + operation > 9 || a[i] + operation < 0) return false;
			}
			return true;
		}
		int operation = a[left - 1] - a[left];
		for (int i = left; i <= right; i++) {
			if (a[i] + operation > 9 || a[i] + operation < 0) return false;
		}
		return true;
	}
	
	public static boolean allEqual(int[] a) {
		for (int i=0; i<a.length; i++) {
			if (a[i] != a[0]) return false;
		}
		return true;
	}

	public static boolean almostEqual(int[] a) {
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			s.add(a[i]);
		}
		return s.size() <= 2;
	}

	public static int numDistinct(int[] a) {
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			s.add(a[i]);
		}
		return s.size();
	}
}
