/**
 * Created by 12694 on 2018/10/21.
 * lombok插件
 */
public class Percolation {

    public static final int SITE_OPEN = 1;
    public static final int SITE_BLOCKED = 2;
    public static final int SITE_FULL = 3;
    public static final int SITE_TOP = 4;
    public static final int SITE_BOTTOM = 5;

    public static int cnt = 0;

    private int n;
    public Site[] sites;

    public Percolation(int n) throws IndexOutOfBoundsException{
        cnt++;
        //创建一个n2的网格，每个点都blocked
        if(n <= 0) {
            throw new IndexOutOfBoundsException();
        }else {

            this.n = n;
            sites = new Site[n*n+2];
            for(int i = 0; i <= n*n+1; i++){
                sites[i] = new Site();
                sites[i].setN(n);
                sites[i].setRoot(i);
                sites[i].setSiteStatus(SITE_BLOCKED);
                sites[i].setWeight(1);
            }
            //0为top， n2+1为bottom
            sites[0].setSiteStatus(SITE_TOP);
            sites[n*n+1].setSiteStatus(SITE_BOTTOM);
        }
    }

    public void open(int i, int j) throws IllegalArgumentException{
        //open第（i,j）个点，如果他是blocked的
        if(i <= n && i >= 1 && j <= n && j >= 1){
            int id = (i-1)*n + j;
            if(sites[id].getSiteStatus() == SITE_BLOCKED){
                sites[id].setSiteStatus(SITE_OPEN);
            }

            int w = (i-1-1)*n + j;
            int s = i*n + j;
            int a = (i-1)*n + j - 1;
            int d = (i-1)*n +j + 1;

            if(id == 1){
                //左上角
                union(0, id);
                if(sites[d].getSiteStatus() == SITE_OPEN) union(id,d);
                if(sites[s].getSiteStatus() == SITE_OPEN) union(id,s);
            }else if(id == n){
                //右上角
                union(0, id);
                if(sites[a].getSiteStatus() == SITE_OPEN) union(id,a);
                if(sites[s].getSiteStatus() == SITE_OPEN) union(id,s);
            }else if(id == n*(n-1)+1){
                //左下角
                union(n*n+1, id);
                if(sites[w].getSiteStatus() == SITE_OPEN) union(id,w);
                if(sites[d].getSiteStatus() == SITE_OPEN) union(id,d);
            }else if(id == n*n){
                //右下角
                union(n*n+1, id);
                if(sites[w].getSiteStatus() == SITE_OPEN) union(id,w);
                if(sites[a].getSiteStatus() == SITE_OPEN) union(id,a);
            }else if(id > 1 && id < n){
                //上边
                union(0, id);
                if(sites[d].getSiteStatus() == SITE_OPEN) union(id,d);
                if(sites[s].getSiteStatus() == SITE_OPEN) union(id,s);
                if(sites[a].getSiteStatus() == SITE_OPEN) union(id,a);
            }else if(id > n*(n-1)+1 && id < n*n){
                //下边
                union(n*n+1, id);
                if(sites[w].getSiteStatus() == SITE_OPEN) union(id,w);
                if(sites[d].getSiteStatus() == SITE_OPEN) union(id,d);
                if(sites[a].getSiteStatus() == SITE_OPEN) union(id,a);
            }else if (id % n == 1){
                //左边
                if(sites[d].getSiteStatus() == SITE_OPEN) union(id,d);
                if(sites[w].getSiteStatus() == SITE_OPEN) union(id,w);
                if(sites[s].getSiteStatus() == SITE_OPEN) union(id,s);
            }else if (id % n == 0){
                //右边
                if(sites[a].getSiteStatus() == SITE_OPEN) union(id,a);
                if(sites[w].getSiteStatus() == SITE_OPEN) union(id,w);
                if(sites[s].getSiteStatus() == SITE_OPEN) union(id,s);
            }else{
                //中间
                if(sites[d].getSiteStatus() == SITE_OPEN) union(id,d);
                if(sites[a].getSiteStatus() == SITE_OPEN) union(id,a);
                if(sites[w].getSiteStatus() == SITE_OPEN) union(id,w);
                if(sites[s].getSiteStatus() == SITE_OPEN) union(id,s);
            }

        }else {
            throw new IllegalArgumentException();
        }


    }

    public boolean isOpen(int i, int j)throws IllegalArgumentException{
        //is site(i,j) open?

        if(i <= n && i > 0 && j <= n && j > 0){
            int id = (i-1)*n + j;
            if(sites[id].getSiteStatus() == SITE_BLOCKED){
                return false;
            }else{
                return true;
            }
        }else {
            throw new IllegalArgumentException();
        }

    }

    public boolean isFull(int i, int j){
        //is site(i,j) full?
        if(i <= n && i >= 1 && j <= n && j >= 1){
            int id = (i-1)*n + j;
            if(find(n*n+1) == find(id)){
                return true;
            }else{
                return false;
            }

        }else {
            throw new IllegalArgumentException();
        }
    }

    private int find(int x){
        while (x != sites[x].getRoot()){
            x = sites[x].getRoot();
        }
        return x;
    }

    private void union(int p, int q){
        int root1 = find(p);
        int root2 = find(q);
        if(root1 == root2){
            //已经连通无需操作
            return;
        }

        if(sites[root1].getWeight() < sites[root2].getWeight()){
            sites[root1].setRoot(root2);
        }else if(sites[root1].getWeight() > sites[root2].getWeight()){
            sites[root2].setRoot(root1);
        }else{
            //两树同高
            int h = sites[root2].getWeight();
            sites[root2].setWeight(h+1);
            sites[root1].setRoot(root2);
        }

    }


    public boolean percolates(){
        //is system percolate?
        for(int i = 1; i <= n; i++){
            if(isFull(1, i)){
                return true;
            }
        }
        return false;
    }
}
