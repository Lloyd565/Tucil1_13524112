package src;
import java.util.*;

class Pos {
    int r, c;
    Pos(int r, int c) {
        this.r = r;
        this.c = c;
    }
}

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Masukkan ukuran papan : ");
        int n = sc.nextInt();
        sc.nextLine();
        char[][] papan = new char[n][n];
        List<Pos>[] pos_Warna = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            pos_Warna[i] = new ArrayList<>();
        }

        System.out.println("Masukkan papan (" + n + " baris):");
        for (int i = 0; i < n; i++) {
            String row = sc.nextLine().trim();

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
                pos_Warna[idx].add(new Pos(i, j));
            }
        }

        // System.out.println("\nIsi tiap region warna:");
        // for (int i = 0; i < 26; i++) {
        //     if (!pos_Warna[i].isEmpty()) {
        //         char region = (char) ('A' + i);
        //         System.out.print(region + ": ");
        //         for (Pos p : pos_Warna[i]) {
        //             System.out.print("(" + p.r + "," + p.c + ") ");
        //         }
        //         System.out.println();
        //     }
        // }
    }
}
