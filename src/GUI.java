import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GUI extends JFrame {

    private JComboBox<String> fileCombo;
    private JComboBox<String> algoCombo;
    private JPanel papanPanel;
    private JLabel[][] sel;
    private JTextArea statusLabel;
    private JLabel iterLabel;
    private JButton solveBtn;
    private JButton loadBtn;
    private int n;
    private char[][] papan;
    private List<Pos>[] list_Warna;

    static final Color[] REGION_COLORS = {
        new Color(231, 76, 60),    // A
        new Color(52, 152, 219),   // B
        new Color(46, 204, 113),   // C
        new Color(243, 156, 18),   // dst
        new Color(155, 89, 182),
        new Color(241, 196, 15),   
        new Color(26, 188, 156),   
        new Color(230, 126, 34),   
        new Color(236, 240, 241),  
        new Color(52, 73, 94),     
        new Color(192, 57, 43),    
        new Color(41, 128, 185),   
        new Color(39, 174, 96),    
        new Color(211, 84, 0),     
        new Color(142, 68, 173),  
        new Color(22, 160, 133),   
        new Color(44, 62, 80),     
        new Color(127, 140, 141),  
        new Color(189, 195, 199),  
        new Color(245, 183, 177),  
        new Color(174, 214, 241),  
        new Color(169, 223, 191),  
        new Color(249, 231, 159),  
        new Color(215, 189, 226),  
        new Color(171, 235, 198),  
        new Color(250, 219, 216),  
    };

    public GUI() {
        setTitle("Tucil 1 Stima");
        setSize(750, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(44, 62, 80));

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));
        sidebar.setPreferredSize(new Dimension(280, getHeight()));

        JLabel fileLabel = new JLabel("Pilih Input File:");
        fileLabel.setForeground(Color.WHITE);
        fileLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(fileLabel);
        sidebar.add(Box.createVerticalStrut(5));

        JPanel filePanel = new JPanel(new BorderLayout(5, 0));
        filePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        filePanel.setMaximumSize(new Dimension(300, 30));        
        fileCombo = new JComboBox<>();
        fileCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        loadFile();
        filePanel.add(fileCombo, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("↻");
        refreshBtn.setMargin(new Insets(0, 5, 0, 5));
        refreshBtn.setToolTipText("Refresh list");
        refreshBtn.addActionListener(e -> loadFile());
        filePanel.add(refreshBtn, BorderLayout.EAST);
        sidebar.add(filePanel);
        sidebar.add(Box.createVerticalStrut(10));

        loadBtn = new JButton("Load Board");
        loadBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadBtn.setMaximumSize(new Dimension(300, 35));
        loadBtn.setForeground(Color.BLUE);
        loadBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        loadBtn.setFocusPainted(false);
        loadBtn.addActionListener(e -> loadPapan());
        sidebar.add(loadBtn);
        sidebar.add(Box.createVerticalStrut(25));

        JLabel algoLabel = new JLabel("Pilih Algoritma:");
        algoLabel.setForeground(Color.WHITE);
        algoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        algoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(algoLabel);
        sidebar.add(Box.createVerticalStrut(5));

        algoCombo = new JComboBox<>(new String[]{
            "Brute Force Optimal",
            "Brute Force Murni"
        });
        algoCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        algoCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        algoCombo.setMaximumSize(new Dimension(300, 30));
        sidebar.add(algoCombo);
        sidebar.add(Box.createVerticalStrut(25));
        
        solveBtn = new JButton("SOLVE");
        solveBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        solveBtn.setForeground(Color.BLUE);
        solveBtn.setFocusPainted(false);
        solveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        solveBtn.setMaximumSize(new Dimension(300, 50));
        solveBtn.setEnabled(false);
        solveBtn.addActionListener(e -> solvePapan());
        sidebar.add(solveBtn);

        sidebar.add(Box.createVerticalStrut(30));

        // status2
        statusLabel = new JTextArea("Belum dimulai");
        statusLabel.setLineWrap(true);
        statusLabel.setWrapStyleWord(true);
        statusLabel.setEditable(false);
        statusLabel.setOpaque(false);
        statusLabel.setForeground(new Color(241, 196, 15));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setMaximumSize(new Dimension(280, 100));
        sidebar.add(statusLabel);
        sidebar.add(Box.createVerticalStrut(5));
        iterLabel = new JLabel("Iterasi: 0");
        iterLabel.setForeground(new Color(189, 195, 199));
        iterLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
        iterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(iterLabel);
        sidebar.add(Box.createVerticalGlue());
        add(sidebar, BorderLayout.WEST);

        papanPanel = new JPanel();
        papanPanel.setBackground(new Color(44, 62, 80));
        papanPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(papanPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void loadFile() {
        fileCombo.removeAllItems();
        File testDir = new File("test");
        if (testDir.exists() && testDir.isDirectory()) {
            String[] files = testDir.list((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                Arrays.sort(files);
                for (String f : files) {
                    fileCombo.addItem(f);
                }
            }
        }
        if (fileCombo.getItemCount() == 0) {
            fileCombo.addItem("(tidak ada file .txt di test/)");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPapan() {
        String fileName = (String) fileCombo.getSelectedItem();
        if (fileName == null || fileName.startsWith("(")) return;

        String filePath = "test/" + fileName;

        // Baca semua baris
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
            JOptionPane.showMessageDialog(this, "File tidak ditemukan: " + filePath);
            return;
        }

        n = baris.size();
        papan = new char[n][n];
        list_Warna = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            list_Warna[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            String row = baris.get(i);
            if (row.length() != n) {
                JOptionPane.showMessageDialog(this,
                    "Error: baris ke-" + (i+1) + " panjangnya " + row.length() + ", harusnya " + n);
                return;
            }
            for (int j = 0; j < n; j++) {
                char ch = row.charAt(j);
                if (ch < 'A' || ch > 'Z') {
                    JOptionPane.showMessageDialog(this, "Karakter tidak valid: " + ch);
                    return;
                }
                papan[i][j] = ch;
                list_Warna[ch - 'A'].add(new Pos(i, j));
            }
        }

        tampilkanPapan();
        solveBtn.setEnabled(true);
        statusLabel.setText("Papan dimuat (" + n + "×" + n + ")");
        statusLabel.setForeground(new Color(241, 196, 15));
        iterLabel.setText("Iterasi: 0");
    }

    private void tampilkanPapan() {
        papanPanel.removeAll();
        papanPanel.setLayout(new GridLayout(n, n, 2, 2));

        sel = new JLabel[n][n];
        int cellSize = Math.min(600 / n, 80);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setFont(new Font("SansSerif", Font.BOLD, Math.max(cellSize / 2, 14)));
                cell.setPreferredSize(new Dimension(cellSize, cellSize));

                int colorIdx = papan[i][j] - 'A';
                Color bg = REGION_COLORS[colorIdx % REGION_COLORS.length];
                cell.setBackground(bg);
                cell.setBorder(BorderFactory.createLineBorder(new Color(44, 62, 80), 1));

                sel[i][j] = cell;
                papanPanel.add(cell);
            }
        }

        papanPanel.revalidate();
        papanPanel.repaint();
    }

    private void clearQueens() {
        if (sel == null) return;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sel[i][j].setText("");
            }
        }
    }

    private void showQueensOpt(int[] posArr, List<Character> akt, List<Pos>[] lw) {
        clearQueens();
        for (int i = 0; i < akt.size(); i++) {
            char warna = akt.get(i);
            int idx = warna - 'A';
            int posIdx = posArr[i];
            if (posIdx >= 0 && posIdx < lw[idx].size()) {
                Pos p = lw[idx].get(posIdx);
                sel[p.r][p.c].setText("♛"); //icon terdekat, input imagenya kurang pas
            }
        }
    }

    private void showQueensMurni(int[] letak, int size) {
        clearQueens();
        for (int col = 0; col < size; col++) {
            int row = letak[col];
            if (row >= 0 && row < n && col < n) {
                sel[row][col].setText("♛");//sama
            }
        }
    }

    private void solvePapan() {
        solveBtn.setEnabled(false);
        loadBtn.setEnabled(false);
        algoCombo.setEnabled(false);
        statusLabel.setText("Mencari solusi...");
        statusLabel.setForeground(new Color(243, 156, 18));
        clearQueens();

        int algoIdx = algoCombo.getSelectedIndex();

        if (algoIdx == 0) {
            solveOptimal();
        } else {
            solveBruteForceMurni();
        }
    }

    @SuppressWarnings("unchecked")
    private void solveOptimal() {
        final List<Pos>[] lwCopy = list_Warna;
        opt_solve solver = new opt_solve(papan, n, lwCopy);

        SwingWorker<Boolean, Object[]> worker = new SwingWorker<Boolean, Object[]>() {
            private long startTime;
            @Override
            protected Boolean doInBackground() {
                startTime = System.currentTimeMillis();
                solver.setListener(new opt_solve.SolveListener() {
                    @Override
                    public void onTry(int[] currentPos, List<Character> akt, List<Pos>[] lw, long iterasi) {
                        publish(new Object[]{"try", currentPos, akt, lw, iterasi});
                    }
                    @Override
                    public void onSolved(int[] bestPos, List<Character> akt, List<Pos>[] lw, long iterasi) {
                        publish(new Object[]{"solved", bestPos, akt, lw, iterasi});
                    }
                });

                return solver.cariSolusi();
            }
            @Override
            protected void process(List<Object[]> chunks) {
                Object[] latest = chunks.get(chunks.size() - 1);
                String type = (String) latest[0];
                int[] posArr = (int[]) latest[1];
                List<Character> akt = (List<Character>) latest[2];
                List<Pos>[] lw = (List<Pos>[]) latest[3];
                long iterasi = (long) latest[4];
                showQueensOpt(posArr, akt, lw);
                iterLabel.setText("Iterasi: " + String.format("%,d", iterasi));
                if ("solved".equals(type)) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    statusLabel.setText("Solusi ditemukan! (" + elapsed + " ms)");
                    statusLabel.setForeground(new Color(46, 204, 113));
                }
            }

            @Override
            protected void done() {
                try {
                    boolean found = get();
                    long elapsed = System.currentTimeMillis() - startTime;
                    if (found) {
                        showQueensOpt(solver.best, solver.akt, lwCopy);
                        statusLabel.setText("Solusi ditemukan (" + elapsed + " ms, " +
                            String.format("%,d", solver.getI()) + " iterasi)");
                        statusLabel.setForeground(new Color(46, 204, 113));
                        iterLabel.setText("Iterasi: " + String.format("%,d", solver.getI()));
                    } else {
                        statusLabel.setText("Tidak ada solusi (" + elapsed + " ms)");
                        statusLabel.setForeground(new Color(231, 76, 60));
                        clearQueens();
                    }
                } catch (Exception e) {
                    statusLabel.setText("Error: " + e.getMessage());
                    statusLabel.setForeground(new Color(231, 76, 60));
                }

                solveBtn.setEnabled(true);
                loadBtn.setEnabled(true);
                algoCombo.setEnabled(true);
            }
        };

        worker.execute();
    }

    private void solveBruteForceMurni() {
        solve solver = new solve(papan, n);
        SwingWorker<Boolean, Object[]> worker = new SwingWorker<Boolean, Object[]>() {
            private long startTime;
            @Override
            protected Boolean doInBackground() {
                startTime = System.currentTimeMillis();
                solver.setListener(new solve.SolveListener() {
                    @Override
                    public void onTry(int[] letak, int size, long iterasi) {
                        publish(new Object[]{"try", letak, size, iterasi});
                    }
                    @Override
                    public void onSolved(int[] solusi, int size, long iterasi) {
                        publish(new Object[]{"solved", solusi, size, iterasi});
                    }
                });

                return solver.cariSolusi();
            }

            @Override
            protected void process(List<Object[]> chunks) {
                Object[] latest = chunks.get(chunks.size() - 1);
                String type = (String) latest[0];
                int[] letak = (int[]) latest[1];
                int size = (int) latest[2];
                long iterasi = (long) latest[3];

                showQueensMurni(letak, size);
                iterLabel.setText("Iterasi: " + String.format("%,d", iterasi));

                if ("solved".equals(type)) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    statusLabel.setText("Solusi ditemukan (" + elapsed + " ms)");
                    statusLabel.setForeground(new Color(46, 204, 113));
                }
            }

            @Override
            protected void done() {
                try {
                    boolean found = get();
                    long elapsed = System.currentTimeMillis() - startTime;

                    if (found) {
                        showQueensMurni(solver.getSolusi(), n);
                        statusLabel.setText("Solusi ditemukan (" + elapsed + " ms, " +
                            String.format("%,d", solver.getIter()) + " iterasi)");
                        statusLabel.setForeground(new Color(46, 204, 113));
                        iterLabel.setText("Iterasi: " + String.format("%,d", solver.getIter()));
                    } else {
                        statusLabel.setText("Tidak ada solusi (" + elapsed + " ms)");
                        statusLabel.setForeground(new Color(231, 76, 60));
                        clearQueens();
                    }
                } catch (Exception e) {
                    statusLabel.setText("Error: " + e.getMessage());
                    statusLabel.setForeground(new Color(231, 76, 60));
                }

                solveBtn.setEnabled(true);
                loadBtn.setEnabled(true);
                algoCombo.setEnabled(true);
            }
        };

        worker.execute();
    }
}
