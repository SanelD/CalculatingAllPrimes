import java.util.ArrayList;

public class Prim {

	ArrayList<char[][]> L = new ArrayList<char[][]>();

	public static void main(String[] args) {
		// TODO Auto-generated method stu
		new Prim();
	}

	Prim() {
		
		int[] facs = Factorizing("1010101001010101"); //input in least significant bit first encoding
		if (facs != null) {
			for (int i = 0; i < facs.length; i++) {
				System.out.println("Factor" + i + ": " + facs[i]);
			}
		} else {
			System.out.println("nesta jos neradi brate"); //Something went wrong
		}
	}

	int[] Factorizing(String z) {
		//z = msbfTOlsbf(z);
		int n = z.length();
		char[][] M = init(n);
		char[][] M_links, M_rechts;
		L.add(M);
		while (L.isEmpty() == false) {
			M = L.get(0);
			M_links = Schritt(M, z, "links");
			M_rechts = Schritt(M, z, "rechts");
			if (M_links != null && Summe(M_links).equals(z)) {
				return readFactors(M_links);
			} else if (M_rechts != null && Summe(M_rechts).equals(z)) {
				return readFactors(M_rechts);
			} else {
				L.remove(0);
				if (M_links != null)
					L.add(M_links);
				if (M_rechts != null)
					L.add(M_rechts);
			}
		}
		return null;
	}

	char[][] Schritt(char[][] M, String z, String r) {
		int k = M.length;
		int n = M[0].length;
		char[][] M_neu = new char[k + 1][n];
		if (k < n) {
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < n; j++) {
					M_neu[i][j] = M[i][j];
				}
			}
			for (int j = 0; j < n; j++) {
				if (j == k && r.equals("rechts")) {
					M_neu[k][j] = '1';
				} else {
					M_neu[k][j] = '0';
				}
			}
			if (Summe(M_neu).charAt(k) != z.charAt(k)) {
				M_neu[0][k] = '1';
			}
			update(M_neu);
		} else {
			M_neu = null;
		}
		return M_neu;
	}

	char[][] update(char[][] M) {
		int k = M.length;
		int n = M[0].length;
		try {
			if (M[k - 1][k - 1] == '1') {
				int k_stern = k;
				while (k_stern < n) {
					M[k - 1][k_stern] = M[0][k_stern - k + 1];
					k_stern = k_stern + 1;
				}
			}
			if (M[0][k - 1] == '1') {
				for (int i = 0; i < k - 1; i++) {
					if (M[i][i] == '1') {
						M[i][i + k - 1] = '1';
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			M = null;
		}
		return M;
	}

	int[] readFactors(char[][] M) {
		String f_1 = "", f_2 = "";
		int k = M.length;
		int n = M[0].length;
		for (int i = 0; i < n; i++) {
			f_1 += M[0][(n - i) - 1];
			if (i < k) {
				f_2 += M[k - i - 1][k - i - 1];
			}
		}
		int[] factors = { Integer.parseInt(f_1), Integer.parseInt(f_2) };
		System.out.println("The factors are :" + f_1 + " & " + f_2);
		return factors;
	}

	String Summe(char[][] M) {
		int k = M.length;
		int n = M[0].length;
		char[] e = new char[n];
		char c = '0';
		for (int i = 0; i < n; i++) {
			e[i] = '0';
		}
		for (int i = 0; i < k; i++) {
			for (int j = 0; j < n; j++) {
				if (e[j] == '0' && M[i][j] == '0') {
					if (c == '0') {
						e[j] = '0';
					} else {
						e[j] = '1';
					}
					c = '0';
				} else if ((e[j] == '1' && M[i][j] == '0') || (e[j] == '0' && M[i][j] == '1')) {
					if (c == '0') {
						e[j] = '1';
					} else {
						e[j] = '0';
						c = '1';
					}
				} else {
					if (c == '0') {
						e[j] = '0';
					} else {
						e[j] = '1';
					}
					c = '1';
				}
			}
		}
		String sum = "";
		for (int i = 0; i < n; i++) {
			sum += e[i];
		}
		return sum;
	}

	char[][] init(int n) {
		char[][] M = new char[1][n];
		M[0][0] = '1';
		for (int i = 1; i < n; i++) {
			M[0][i] = '0';
		}
		return M;
	}

}