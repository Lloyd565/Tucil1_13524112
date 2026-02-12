package src;
import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();

        String[] input = new String[n];
        for (int i = 0; i < n; i++) {
            input[i] = sc.nextLine();
        }
    }
}
