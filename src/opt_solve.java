import java.util.*;

public class opt_solve {
    private char[][] papan;
    private int n;
    private List<Pos>[] list_Warna;
    public List<Character> akt;
    private long iter = 0;
    private int[] pos;
    private boolean found_sol = false;
    public int[] best;

    // live update GUI
    public interface SolveListener {
        void onTry(int[] currentPos, List<Character> akt, List<Pos>[] list_Warna, long iterasi);
        void onSolved(int[] bestPos, List<Character> akt, List<Pos>[] list_Warna, long iterasi);
    }

    private SolveListener listener;
    private long lastUpdateTime = 0;
    private static final long UPDATE_INTERVAL_MS = 30; //bisa diedit

    public void setListener(SolveListener listener) {
        this.listener = listener;
    }
    
    public opt_solve(char[][] papan, int n, List<Pos>[] list_Warna) {
        this.papan = papan;
        this.n = n;
        this.list_Warna = list_Warna;
        this.akt = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            if (!list_Warna[i].isEmpty()) {
                akt.add((char)('A' + i));
            }
        }
        this.pos = new int[akt.size()];
        this.best = new int[akt.size()];
    }
    
    public boolean cariSolusi() {
        long startTime = System.currentTimeMillis();        
        bruteForce(0);
        long endTime = System.currentTimeMillis();
        System.out.println("\nWaktu pencarian: " + (endTime - startTime) + " ms");
        System.out.println("Banyak kasus yang ditinjau: " + iter + " kasus");
        return found_sol;
    }

    public long getI() {
        return iter;
    }
    
    private void bruteForce(int indexWarna) {
        if (indexWarna == akt.size()) {
            iter++;
            if (listener != null) {
                long now = System.currentTimeMillis();
                if (now - lastUpdateTime >= UPDATE_INTERVAL_MS) {
                    lastUpdateTime = now;
                    listener.onTry(pos.clone(), akt, list_Warna, iter);
                    try { Thread.sleep(1); } 
                    catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                }
            }
            if (cekVal()) {
                found_sol = true;
                for (int i = 0; i < akt.size(); i++) {
                    best[i] = pos[i];
                }
                if (listener != null) {
                    listener.onSolved(best.clone(), akt, list_Warna, iter);
                }
            }
            return;
        }
        
        char warna = akt.get(indexWarna);
        int idx = warna - 'A';
        List<Pos> posisi_Warna = list_Warna[idx];
        for (int i = 0; i < posisi_Warna.size(); i++) {
            pos[indexWarna] = i;
            bruteForce(indexWarna + 1);
            if (found_sol) {
                return;
            }
        }
    }
    
    private boolean cekVal() {
        List<Pos> posisi_Queens = new ArrayList<>();        
        for (int i = 0; i < akt.size(); i++) {
            char warna = akt.get(i);
            int idx = warna - 'A';
            int posIdx = pos[i];
            Pos pos = list_Warna[idx].get(posIdx);
            posisi_Queens.add(pos);
        }        
        for (int i = 0; i < posisi_Queens.size(); i++) {
            for (int j = i + 1; j < posisi_Queens.size(); j++) {
                Pos q1 = posisi_Queens.get(i);
                Pos q2 = posisi_Queens.get(j);                
                if (q1.r == q2.r) {
                    return false;
                }
                if (q1.c == q2.c) {
                    return false;
                }
                int deltaRow = Math.abs(q1.r - q2.r);
                int deltaCol = Math.abs(q1.c - q2.c);                
                if ((deltaRow == 1 && deltaCol == 0) || 
                    (deltaRow == 0 && deltaCol == 1) ||
                    (deltaRow == 1 && deltaCol == 1)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public void tampilkanSolusi() {
        if (!found_sol) {
            System.out.println("\nTidak ada solusi!");
            return;
        }
        System.out.println("\nSolusi ditemukan:");
        char[][] hasil = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                hasil[i][j] = papan[i][j];
            }
        }
        for (int i = 0; i < akt.size(); i++) {
            char warna = akt.get(i);
            int idx = warna - 'A';
            int posIdx = best[i];
            Pos pos = list_Warna[idx].get(posIdx);
            hasil[pos.r][pos.c] = '#';
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(hasil[i][j]);
            }
            System.out.println();
        }
    }
    
}