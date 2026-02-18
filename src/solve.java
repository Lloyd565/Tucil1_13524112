import java.util.*;

public class solve {
    private char[][] papan;
    private int n;
    private long iter = 0;
    private int[] solusi;
    private boolean dapat_Solusi = false;

    // buat GUI live update
    public interface SolveListener {
        void onTry(int[] letak, int n, long iterasi);
        void onSolved(int[] solusi, int n, long iterasi);
    }

    private SolveListener listener;
    private long lastUpdateTime = 0;
    private static final long intervalms = 30; //bisa diedit agar lama live updatenya

    public void setListener(SolveListener listener) {
        this.listener = listener;
    }

    public solve(char[][] papan, int n) {
        this.papan = papan;
        this.n = n;
        this.solusi = new int[n];
    }

    public boolean cariSolusi() {
        long startTime = System.currentTimeMillis();
        int[] letak = new int[n];
        bruteForce(letak, 0);
        long endTime = System.currentTimeMillis();
        System.out.println("\nWaktu pencarian: " + (endTime - startTime) + " ms");
        System.out.println("Banyak kasus yang ditinjau: " + iter + " kasus");
        return dapat_Solusi;
    }

    //gettter
    public int[] getSolusi() {
        return solusi;
    }
    public long getIter() {
        return iter;
    }


    private void bruteForce(int[] koord_Queen, int col) {
        long jlh_kom = 1;
        for (int i = 0; i < n; i++) {
            jlh_kom *= n;
        }
        for (long i = 0; i < jlh_kom; i++) {
            long temp = i;
            for (int i_col = 0; i_col < n; i_col++) {
                koord_Queen[i_col] = (int) (temp % n);
                temp /= n;
            }
            iter++;
            // tiap coba kemungkinan, langsung ke main buat tampilin di GUI
            if (listener != null) {
                long now = System.currentTimeMillis();
                if (now - lastUpdateTime >= intervalms) {
                    lastUpdateTime = now;
                    listener.onTry(koord_Queen.clone(), n, iter);
                    try { Thread.sleep(1); } 
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }
            if (cekVal(koord_Queen)) {
                if (!dapat_Solusi) {
                    dapat_Solusi = true;
                    solusi = koord_Queen.clone();
                    if (listener != null) {
                        listener.onSolved(solusi.clone(), n, iter);
                    }
                }
            }
        }
    }

    private boolean cekVal(int[] koord_Queen) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int row1 = koord_Queen[i], col1 = i;
                int row2 = koord_Queen[j], col2 = j;
                // baris
                if (row1 == row2) return false;
                // warna
                if (papan[row1][col1] == papan[row2][col2]) return false;
                // sekitar
                if (Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void tampilkanSolusi() {
        if (!dapat_Solusi) {
            System.out.println("\nTidak ada solusi!");
            return;
        }

        System.out.println("\nSolusi ditemukan:");
        char[][] hasil = new char[n][n];

        for (int i = 0; i < n; i++) hasil[i] = papan[i].clone();
        for (int col = 0; col < n; col++) hasil[solusi[col]][col] = '#';

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) System.out.print(hasil[i][j]);
            System.out.println();
        }
    }
}