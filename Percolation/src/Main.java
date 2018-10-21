import java.util.Scanner;

/**
 * Created by 12694 on 2018/10/21.
 */
public class Main {

    public static void main(String[] args) {
        int n;
        int t;
        System.out.println("Input the matrix size n and the test times t in two lines:");

        Scanner scanner = new Scanner(System.in);
        n = Integer.parseInt(scanner.nextLine());
        t = Integer.parseInt(scanner.nextLine());
        scanner.close();

        PercolationStats percolationStats = new PercolationStats(n, t);

        System.out.println("With" +t+ " tests, each test has "+n+"*"+n +" sites:");
        System.out.println("the mean of p = "+ percolationStats.mean());
        System.out.println("the standard deviation of p = "+ percolationStats.stddev());
        System.out.println("the 95% confidence interval is from "+percolationStats.confidenceLo()+ " to " + percolationStats.confidenceHi());
    }
}
