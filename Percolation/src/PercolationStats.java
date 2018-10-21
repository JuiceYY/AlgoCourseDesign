import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by 12694 on 2018/10/21.
 */
public class PercolationStats {

    private int n;
    private int t;
    private Site[] sites;
    private double[] sampleOfP;
    private double smean;//样本平均值
    private double sstddev;//样本方差

    public PercolationStats(int n, int t)throws IllegalArgumentException{

        if(n <= 0 || t <= 0){
            throw new IllegalArgumentException();
        }

        this.t = t;
        this.n = n;
        sampleOfP = new double[t];
        for(int i= 0; i < t; i++){
            sampleOfP[i] = goTest(n);
        }
    }

    private double goTest(int n){
        Percolation percolation = new Percolation(n);
        for(int i = 0; i < n*n; i++){
            Random random = new Random();
            int x = random.nextInt(n)+1;
            int y = random.nextInt(n)+1;
            if(percolation.sites[(x-1)*n+y].getSiteStatus() == Percolation.SITE_OPEN){
                i--;
                continue;
            }
            //percolation.sites[(x-1)*n+y].setSiteStatus(Percolation.SITE_OPEN);
            percolation.open(x,y);
            if(percolation.percolates()){
                return (i+1)/(double)(n*n);
            }
        }
        System.out.println("error in goTest "+ Percolation.cnt);
        return 0;
    }

    public double mean(){
        //样本平均值
        double sum = 0;
        for(int i = 0; i < t; i++){
            sum += sampleOfP[i];
        }
        smean = sum/t;
        return smean;
    }

    public double stddev(){
        //样本标准差
        double s = 0;
        for(int i = 0; i < t; i++){
            s += (Math.abs(mean()-sampleOfP[i]) * Math.abs(mean()-sampleOfP[i]) );
        }
        sstddev = Math.sqrt(s/(t-1));
        return sstddev;
    }

    public double confidenceLo(){
        return smean - 1.96*sstddev/Math.sqrt((double)t);

    }

    public double confidenceHi(){
        return smean + 1.96*sstddev/Math.sqrt((double)t);

    }

}
