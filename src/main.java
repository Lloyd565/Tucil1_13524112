import java.util.*;
import java.io.*;

class Pos {
    int r, c;
    Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nama file input: ");
        String filePath = "test/" + input.nextLine().trim();
        input.close();

        List<String> baris = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filePath));
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    baris.add(line);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + filePath);
            return;
        }

        int n = baris.size();
        char[][] papan = new char[n][n];
        List<Pos>[] list_Warna = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            list_Warna[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            String row = baris.get(i);
            if (row.length() != n) {
                System.out.println("Panjang baris ke-" + i + " tidak sama dengan " + n);
                return;
            }
            for (int j = 0; j < n; j++) {
                char ch = row.charAt(j);
                if (ch < 'A' || ch > 'Z') {
                    System.out.println("Ada input warna tidak valid");
                    return;
                }
                papan[i][j] = ch;
                int idx = ch - 'A';
                list_Warna[idx].add(new Pos(i, j));
            }
        }

        // opt_solve opt_solver = new opt_solve(papan, n, list_Warna);
        solve solver = new solve(papan, n);
        boolean ditemukan = solver.cariSolusi();
        // boolean ditemukan = opt_solver.cariSolusi();
        if (ditemukan) {
            solver.tampilkanSolusi();
            // opt_solver.viewSol();
        } else {
            System.out.println("\nTidak ada solusi yang ditemukan");
        }
    }
}
