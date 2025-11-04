// AdvancedAlgorithms.java
// Single-file collection of advanced algorithms and demo harness.
// Compile: javac AdvancedAlgorithms.java
// Run:     java AdvancedAlgorithms
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;

public class AdvancedAlgorithms {

    // ---------------------------
    // Utilities
    // ---------------------------
    static final Random R = new Random(42);

    static void pr(Object o){ System.out.println(o); }
    static void pln(String s){ System.out.println(s); }
    static int[] randIntArray(int n, int min, int max){
        int[] a = new int[n];
        for(int i=0;i<n;i++) a[i] = ThreadLocalRandom.current().nextInt(min, max+1);
        return a;
    }

    // ---------------------------
    // Union-Find (Disjoint Set)
    // ---------------------------
    static class UnionFind {
        int[] p, r;
        UnionFind(int n){ p=new int[n]; r=new int[n]; for(int i=0;i<n;i++) p[i]=i;}
        int find(int a){ return p[a]==a?a:(p[a]=find(p[a])); }
        boolean union(int a, int b){
            a=find(a); b=find(b);
            if(a==b) return false;
            if(r[a]<r[b]) { int t=a; a=b; b=t; }
            p[b]=a; if(r[a]==r[b]) r[a]++; return true;
        }
    }

    // ---------------------------
    // Fenwick / BIT
    // ---------------------------
    static class Fenwick {
        int n; long[] bit;
        Fenwick(int n){ this.n=n; bit=new long[n+1]; }
        void add(int i, long delta){ for(; i<=n; i+=i&-i) bit[i]+=delta; }
        long sum(int i){ long s=0; for(; i>0; i-=i&-i) s+=bit[i]; return s; }
        long rangeSum(int l,int r){ return sum(r)-sum(l-1); }
    }

    // ---------------------------
    // Segment Tree with Lazy Propagation (range add, range max query)
    // ---------------------------
    static class SegmentTree {
        int n; long[] tree, lazy;
        SegmentTree(int n){
            this.n = n;
            int size = 1;
            while(size < n) size <<= 1;
            size <<= 1;
            tree = new long[size];
            lazy = new long[size];
        }
        void apply(int idx, int l, int r, long val){
            tree[idx] += val;
            lazy[idx] += val;
        }
        void push(int idx, int l, int r){
            if(lazy[idx] != 0){
                int mid = (l+r)/2;
                apply(idx*2, l, mid, lazy[idx]);
                apply(idx*2+1, mid+1, r, lazy[idx]);
                lazy[idx] = 0;
            }
        }
        void update(int idx, int l, int r, int ql, int qr, long val){
            if(ql > r || qr < l) return;
            if(ql <= l && r <= qr){
                apply(idx, l, r, val);
                return;
            }
            push(idx,l,r);
            int mid = (l+r)/2;
            update(idx*2, l, mid, ql, qr, val);
            update(idx*2+1, mid+1, r, ql, qr, val);
            tree[idx] = Math.max(tree[idx*2], tree[idx*2+1]);
        }
        long queryMax(int idx, int l, int r, int ql, int qr){
            if(ql > r || qr < l) return Long.MIN_VALUE/4;
            if(ql <= l && r <= qr) return tree[idx];
            push(idx,l,r);
            int mid=(l+r)/2;
            return Math.max(queryMax(idx*2, l, mid, ql, qr),
                            queryMax(idx*2+1, mid+1, r, ql, qr));
        }
        // wrappers
        void rangeAdd(int l,int r,long val){ update(1,1,n,l,r,val); }
        long rangeMax(int l,int r){ return queryMax(1,1,n,l,r); }
    }

    // ---------------------------
    // Trie + KMP
    // ---------------------------
    static class Trie {
        static class Node {
            Node[] next = new Node[26];
            boolean end = false;
        }
        Node root = new Node();
        void insert(String s){
            Node cur = root;
            for(char c: s.toCharArray()){
                if(c < 'a' || c > 'z') continue;
                int i = c - 'a';
                if(cur.next[i] == null) cur.next[i] = new Node();
                cur = cur.next[i];
            }
            cur.end = true;
        }
        boolean search(String s){
            Node cur = root;
            for(char c: s.toCharArray()){
                if(c < 'a' || c > 'z') continue;
                int i = c - 'a';
                if(cur.next[i]==null) return false;
                cur = cur.next[i];
            }
            return cur.end;
        }
    }

    static int[] kmpPrefix(String s){
        int n=s.length(); int[] pi=new int[n];
        for(int i=1;i<n;i++){
            int j=pi[i-1];
            while(j>0 && s.charAt(i)!=s.charAt(j)) j=pi[j-1];
            if(s.charAt(i)==s.charAt(j)) j++;
            pi[i]=j;
        }
        return pi;
    }
    static List<Integer> kmpSearch(String text, String pattern){
        List<Integer> res=new ArrayList<>();
        if(pattern.isEmpty()) return res;
        String s = pattern + "#" + text;
        int[] pi = kmpPrefix(s);
        int pLen = pattern.length();
        for(int i = pLen+1; i < s.length(); i++){
            if(pi[i] == pLen) res.add(i - 2*pLen);
        }
        return res;
    }

    // ---------------------------
    // Suffix Array (doubling) + LCP construction (Kasai)
    // ---------------------------
    static class SuffixArray {
        int[] sa;
        int[] lcp;
        SuffixArray(String s){
            buildSA(s);
            buildLCP(s);
        }
        void buildSA(String s){
            int n=s.length();
            sa = new int[n];
            int[] r = new int[n];
            for(int i=0;i<n;i++){ sa[i]=i; r[i]=s.charAt(i); }
            int[] tmp = new int[n];
            for(int k=1;;k<<=1){
                final int K=k;
                Arrays.sort(sa, (a,b)->{
                    if(r[a]!=r[b]) return Integer.compare(r[a], r[b]);
                    int ra = a+K<n? r[a+K] : -1;
                    int rb = b+K<n? r[b+K] : -1;
                    return Integer.compare(ra, rb);
                });
                tmp[sa[0]] = 0;
                for(int i=1;i<n;i++){
                    tmp[sa[i]] = tmp[sa[i-1]] + (compareRank(r, sa[i-1], sa[i], k, n) ? 1 : 0);
                }
                for(int i=0;i<n;i++) r[i]=tmp[i];
                if(r[sa[n-1]] == n-1) break;
            }
        }
        boolean compareRank(int[] r, int a, int b, int k, int n){
            if(r[a]!=r[b]) return true;
            int ra = a+k<n? r[a+k] : -1;
            int rb = b+k<n? r[b+k] : -1;
            return ra != rb;
        }
        void buildLCP(String s){
            int n=s.length();
            lcp = new int[n];
            int[] rank = new int[n];
            for(int i=0;i<n;i++) rank[sa[i]] = i;
            int h=0;
            for(int i=0;i<n;i++){
                int r = rank[i];
                if(r > 0){
                    int j = sa[r-1];
                    while(i+h < n && j+h < n && s.charAt(i+h) == s.charAt(j+h)) h++;
                    lcp[r] = h;
                    if(h>0) h--;
                }
            }
        }
    }

    // ---------------------------
    // Convex Hull (Graham scan)
    // ---------------------------
    static class Point implements Comparable<Point>{
        double x,y;
        Point(double x,double y){this.x=x;this.y=y;}
        public int compareTo(Point o){
            if(this.x==o.x) return Double.compare(this.y, o.y);
            return Double.compare(this.x, o.x);
        }
        public String toString(){return String.format("(%.3f,%.3f)",x,y);}
    }
    static double cross(Point o, Point a, Point b){
        return (a.x-o.x)*(b.y-o.y) - (a.y-o.y)*(b.x-o.x);
    }
    static List<Point> convexHull(List<Point> pts){
        Collections.sort(pts);
        int n=pts.size();
        if(n<=1) return new ArrayList<>(pts);
        List<Point> lower = new ArrayList<>();
        for(Point p: pts){
            while(lower.size() >= 2 && cross(lower.get(lower.size()-2), lower.get(lower.size()-1), p) <= 0) lower.remove(lower.size()-1);
            lower.add(p);
        }
        List<Point> upper = new ArrayList<>();
        for(int i=n-1;i>=0;i--){
            Point p = pts.get(i);
            while(upper.size() >= 2 && cross(upper.get(upper.size()-2), upper.get(upper.size()-1), p) <= 0) upper.remove(upper.size()-1);
            upper.add(p);
        }
        lower.remove(lower.size()-1); upper.remove(upper.size()-1);
        lower.addAll(upper);
        return lower;
    }

    // ---------------------------
    // Graphs: Dijkstra, Bellman-Ford, A*, Topo DP
    // ---------------------------
    static class Edge {
        int to; long w;
        Edge(int t,long w){this.to=t; this.w=w;}
    }
    static class Graph {
        int n;
        List<Edge>[] adj;
        Graph(int n){ this.n=n; adj = new List[n]; for(int i=0;i<n;i++) adj[i] = new ArrayList<>(); }
        void addEdge(int u,int v,long w){ adj[u].add(new Edge(v,w)); }
    }

    static long[] dijkstra(Graph g, int src){
        long[] dist = new long[g.n];
        Arrays.fill(dist, Long.MAX_VALUE/4);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingLong(a->a[0]));
        pq.add(new int[]{0,src});
        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            long d = cur[0]; int u = cur[1];
            if(d != dist[u]) continue;
            for(Edge e: g.adj[u]){
                if(dist[e.to] > d + e.w){
                    dist[e.to] = d + e.w;
                    pq.add(new int[]{(int)dist[e.to], e.to});
                }
            }
        }
        return dist;
    }

    static long[] bellmanFord(Graph g, int src){
        int n=g.n;
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE/4);
        dist[src]=0;
        for(int k=0;k<n-1;k++){
            boolean any=false;
            for(int u=0;u<n;u++){
                if(dist[u] >= Long.MAX_VALUE/8) continue;
                for(Edge e: g.adj[u]){
                    if(dist[e.to] > dist[u] + e.w){
                        dist[e.to] = dist[u] + e.w; any=true;
                    }
                }
            }
            if(!any) break;
        }
        // detect negative cycle: optional
        return dist;
    }

    static class AStar {
        static long[] aStar(Graph g, int src, int target, LongUnaryOperator heuristic){
            long[] gscore = new long[g.n]; Arrays.fill(gscore, Long.MAX_VALUE/4);
            gscore[src]=0;
            long[] fscore = new long[g.n]; Arrays.fill(fscore, Long.MAX_VALUE/4);
            fscore[src] = heuristic.applyAsLong(src);
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingLong(a->a[0]));
            pq.add(new int[]{(int)fscore[src], src});
            while(!pq.isEmpty()){
                int[] cur = pq.poll(); long f = cur[0]; int u = cur[1];
                if(u==target) return gscore;
                if(f != fscore[u]) continue;
                for(Edge e: g.adj[u]){
                    long tentative = gscore[u] + e.w;
                    if(tentative < gscore[e.to]){
                        gscore[e.to]=tentative;
                        fscore[e.to] = tentative + heuristic.applyAsLong(e.to);
                        pq.add(new int[]{(int)fscore[e.to], e.to});
                    }
                }
            }
            return gscore;
        }
    }

    static long[] topoDP(Graph g, int src){
        // only valid for DAG: compute longest distances from src
        int n=g.n;
        int[] indeg = new int[n];
        for(int u=0;u<n;u++) for(Edge e: g.adj[u]) indeg[e.to]++;
        Deque<Integer> dq = new ArrayDeque<>();
        for(int i=0;i<n;i++) if(indeg[i]==0) dq.add(i);
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MIN_VALUE/4);
        dp[src]=0;
        while(!dq.isEmpty()){
            int u = dq.poll();
            for(Edge e: g.adj[u]){
                dp[e.to] = Math.max(dp[e.to], dp[u] + e.w);
                indeg[e.to]--;
                if(indeg[e.to]==0) dq.add(e.to);
            }
        }
        return dp;
    }

    // ---------------------------
    // Dinic Max Flow
    // ---------------------------
    static class Dinic {
        static class FEdge { int to; int rev; long cap; FEdge(int t,int r,long c){to=t;rev=r;cap=c;} }
        List<FEdge>[] g;
        int n, s, t;
        Dinic(int n){ this.n=n; g=new List[n]; for(int i=0;i<n;i++) g[i]=new ArrayList<>(); }
        void addEdge(int u,int v,long c){
            g[u].add(new FEdge(v, g[v].size(), c));
            g[v].add(new FEdge(u, g[u].size()-1, 0));
        }
        int[] level, it;
        boolean bfs(){
            level = new int[n];
            Arrays.fill(level, -1);
            Queue<Integer> q = new ArrayDeque<>();
            q.add(s); level[s]=0;
            while(!q.isEmpty()){
                int u=q.poll();
                for(FEdge e: g[u]){
                    if(level[e.to] < 0 && e.cap > 0){
                        level[e.to] = level[u] + 1;
                        q.add(e.to);
                    }
                }
            }
            return level[t] >= 0;
        }
        long dfs(int u, long f){
            if(u==t) return f;
            for(; it[u] < g[u].size(); it[u]++){
                FEdge e = g[u].get(it[u]);
                if(e.cap > 0 && level[e.to] == level[u] + 1){
                    long pushed = dfs(e.to, Math.min(f, e.cap));
                    if(pushed > 0){
                        e.cap -= pushed;
                        g[e.to].get(e.rev).cap += pushed;
                        return pushed;
                    }
                }
            }
            return 0;
        }
        long maxflow(int s, int t){
            this.s=s; this.t=t;
            long flow=0;
            while(bfs()){
                it = new int[n];
                long pushed;
                while((pushed = dfs(s, Long.MAX_VALUE/4)) > 0) flow += pushed;
            }
            return flow;
        }
    }

    // ---------------------------
    // FFT (iterative Cooley-Tukey) for polynomial multiplication
    // ---------------------------
    static class FFT {
        static class Complex {
            double re, im;
            Complex(double r,double i){re=r; im=i;}
            Complex add(Complex o){ return new Complex(re+o.re, im+o.im); }
            Complex sub(Complex o){ return new Complex(re-o.re, im-o.im); }
            Complex mul(Complex o){ return new Complex(re*o.re - im*o.im, re*o.im + im*o.re); }
            Complex scale(double s){ return new Complex(re*s, im*s); }
        }
        static void fft(Complex[] a, boolean invert){
            int n=a.length;
            // bit-reverse
            for(int i=1,j=0;i<n;i++){
                int bit = n>>1;
                for(; (j & bit) != 0; bit >>= 1) j ^= bit;
                j ^= bit;
                if(i < j){
                    Complex tmp = a[i]; a[i] = a[j]; a[j] = tmp;
                }
            }
            for(int len=2; len<=n; len<<=1){
                double ang = 2*Math.PI/len * (invert ? -1 : 1);
                Complex wlen = new Complex(Math.cos(ang), Math.sin(ang));
                for(int i=0;i<n;i+=len){
                    Complex w = new Complex(1,0);
                    for(int j=0;j<len/2;j++){
                        Complex u = a[i+j];
                        Complex v = a[i+j+len/2].mul(w);
                        a[i+j] = u.add(v);
                        a[i+j+len/2] = u.sub(v);
                        w = w.mul(wlen);
                    }
                }
            }
            if(invert){
                for(int i=0;i<n;i++){ a[i] = a[i].scale(1.0/n); }
            }
        }
        static long[] multiply(long[] a, long[] b){
            int n1=a.length, n2=b.length;
            int n=1; while(n < n1 + n2) n <<= 1;
            Complex[] fa = new Complex[n];
            for(int i=0;i<n;i++) fa[i] = new Complex(i<n1? a[i] : 0, 0);
            Complex[] fb = new Complex[n];
            for(int i=0;i<n;i++) fb[i] = new Complex(i<n2? b[i] : 0, 0);
            fft(fa, false); fft(fb, false);
            for(int i=0;i<n;i++) fa[i] = fa[i].mul(fb[i]);
            fft(fa, true);
            long[] res = new long[n1+n2-1];
            for(int i=0;i<res.length;i++) res[i] = Math.round(fa[i].re);
            return res;
        }
    }

    // ---------------------------
    // DP examples: Knapsack 0/1, LIS (n log n), Bitmask TSP
    // ---------------------------
    static int knapsack01(int[] wt, int[] val, int W){
        int n = wt.length;
        int[] dp = new int[W+1];
        for(int i=0;i<n;i++){
            for(int w=W; w>=wt[i]; w--){
                dp[w] = Math.max(dp[w], dp[w-wt[i]] + val[i]);
            }
        }
        return dp[W];
    }
    static int LIS_nlogn(int[] a){
        ArrayList<Integer> piles = new ArrayList<>();
        for(int x: a){
            int pos = Collections.binarySearch(piles, x);
            if(pos < 0) pos = -pos - 1;
            if(pos == piles.size()) piles.add(x);
            else piles.set(pos, x);
        }
        return piles.size();
    }
    static int tspBitmask(int[][] dist){
        int n=dist.length;
        int FULL = 1<<n;
        int[][] dp = new int[FULL][n];
        for(int i=0;i<FULL;i++) Arrays.fill(dp[i], Integer.MAX_VALUE/4);
        dp[1][0]=0; // start at 0
        for(int mask=1; mask<FULL; mask++){
            for(int u=0; u<n; u++){
                if((mask & (1<<u))==0) continue;
                if(dp[mask][u] >= Integer.MAX_VALUE/8) continue;
                for(int v=0; v<n; v++){
                    if((mask & (1<<v))!=0) continue;
                    dp[mask | (1<<v)][v] = Math.min(dp[mask | (1<<v)][v], dp[mask][u] + dist[u][v]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for(int u=1; u<n; u++){
            ans = Math.min(ans, dp[FULL-1][u] + dist[u][0]);
        }
        return ans;
    }

    // ---------------------------
    // Misc: Simple Simulated Annealing for maximizing f(x) over integer domain
    // ---------------------------
    static class SimAnneal {
        static int optimize(Function<Integer,Double> f, int start, int minX, int maxX){
            int cur = start;
            double curVal = f.apply(cur);
            double T = 100.0;
            double cooling = 0.995;
            for(int iter=0; iter<20000; iter++){
                int step = ThreadLocalRandom.current().nextInt(-10, 11);
                int cand = cur + step;
                if(cand < minX) cand = minX;
                if(cand > maxX) cand = maxX;
                double candVal = f.apply(cand);
                if(candVal > curVal || Math.exp((candVal-curVal)/T) > Math.random()){
                    cur = cand; curVal = candVal;
                }
                T *= cooling;
                if(T < 1e-6) break;
            }
            return cur;
        }
    }

    // ---------------------------
    // Demo harness - runs small examples for each algorithm
    // ---------------------------
    public static void main(String[] args) {
        pr("=== Advanced Algorithms Demo ===\n");

        // 1) Union-Find quick demo
        UnionFind uf = new UnionFind(6);
        uf.union(0,1); uf.union(1,2); uf.union(3,4);
        pr("Union-Find: find(2) == find(0)? " + (uf.find(2)==uf.find(0)));
        pr("Union-Find: find(4) == find(5)? " + (uf.find(4)==uf.find(5)));

        // 2) Fenwick tree demo
        Fenwick fw = new Fenwick(10);
        for(int i=1;i<=10;i++) fw.add(i, i*10);
        pr("Fenwick sum(1..5): " + fw.rangeSum(1,5));

        // 3) Segment tree demo (range add, range max)
        SegmentTree st = new SegmentTree(10);
        st.rangeAdd(1,5, 3);
        st.rangeAdd(4,10, 7);
        pr("SegmentTree rangeMax(1..10): " + st.rangeMax(1,10));
        pr("SegmentTree rangeMax(1..3): " + st.rangeMax(1,3));

        // 4) Trie + KMP
        Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("world");
        pr("Trie search 'hello': " + trie.search("hello") + "  'wor' (should false): " + trie.search("wor"));
        String text = "ababcabcabababd";
        String pattern = "ababd";
        pr("KMP occurrences of 'ababd' in '" + text + "': " + kmpSearch(text, pattern));

        // 5) Suffix Array, LCP demo
        String s = "banana";
        SuffixArray sa = new SuffixArray(s);
        pr("Suffix Array of 'banana': " + Arrays.toString(sa.sa));
        pr("LCP array: " + Arrays.toString(sa.lcp));

        // 6) Convex hull demo
        List<Point> pts = new ArrayList<>();
        pts.add(new Point(0,0)); pts.add(new Point(1,1)); pts.add(new Point(2,2));
        pts.add(new Point(0,2)); pts.add(new Point(2,0)); pts.add(new Point(1,-1));
        List<Point> hull = convexHull(pts);
        pr("Convex hull: " + hull);

        // 7) FFT polynomial multiply demo
        long[] a = {3,2,1};
        long[] b = {1,2};
        long[] conv = FFT.multiply(a,b);
        pr("Polynomial multiply [3,2,1] * [1,2] = " + Arrays.toString(conv));

        // 8) Graph algorithms demo
        Graph g = new Graph(6);
        g.addEdge(0,1,7); g.addEdge(0,2,9); g.addEdge(0,5,14);
        g.addEdge(1,2,10); g.addEdge(1,3,15);
        g.addEdge(2,3,11); g.addEdge(2,5,2);
        g.addEdge(3,4,6); g.addEdge(4,5,9);
        long[] ds = dijkstra(g, 0);
        pr("Dijkstra distances from 0: " + Arrays.toString(ds));
        // A* with zero heuristic = Dijkstra; but show usage
        long[] ast = AStar.aStar(g, 0, 4, i -> 0L);
        pr("A* (zero heuristic) dist to node 4: " + ast[4]);

        // 9) Bellman-Ford with negative edge demo
        Graph g2 = new Graph(4);
        g2.addEdge(0,1,1); g2.addEdge(1,2,-2); g2.addEdge(2,3,1); g2.addEdge(0,3,5);
        long[] bf = bellmanFord(g2, 0);
        pr("Bellman-Ford distances: " + Arrays.toString(bf));

        // 10) Dinic max flow demo
        Dinic D = new Dinic(6);
        // create small network
        D.addEdge(0,1,16); D.addEdge(0,2,13);
        D.addEdge(1,2,10); D.addEdge(1,3,12);
        D.addEdge(2,1,4);  D.addEdge(2,4,14);
        D.addEdge(3,2,9);  D.addEdge(3,5,20);
        D.addEdge(4,3,7);  D.addEdge(4,5,4);
        long maxflow = D.maxflow(0,5);
        pr("Dinic max flow (0->5): " + maxflow);

        // 11) DP: Knapsack
        int[] wt = {3,4,6,5};
        int[] val = {2,3,1,4};
        int W = 10;
        pr("0/1 Knapsack best value: " + knapsack01(wt,val,W));

        // 12) LIS
        int[] arr = {3,10,2,1,20};
        pr("LIS length: " + LIS_nlogn(arr));

        // 13) TSP small using bitmask DP
        int[][] dist = {
            {0,10,15,20},
            {10,0,35,25},
            {15,35,0,30},
            {20,25,30,0}
        };
        pr("TSP bitmask minimal tour cost (start 0): " + tspBitmask(dist));

        // 14) Simulated annealing toy: maximize f(x) = -(x-37)^2 + noise
        Function<Integer, Double> f = x -> -Math.pow(x-37,2) + ThreadLocalRandom.current().nextGaussian()*2;
        int best = SimAnneal.optimize(f, 0, -100, 100);
        pr("Simulated Annealing best x approx: " + best + " f(x)=" + f.apply(best));

        // 15) Segment tree + Fenwick combined quick problem: prefix sums
        int n = 20;
        Fenwick bit = new Fenwick(n);
        for(int i=1;i<=n;i++) bit.add(i, i);
        pr("Fenwick prefix sum(1..10): " + bit.sum(10));

        // 16) Show suffix array usage to find longest repeated substring (LRS)
        String t = "ababa";
        SuffixArray sa2 = new SuffixArray(t);
        int maxL = 0; int idx = -1;
        for(int i=1;i<sa2.lcp.length;i++){
            if(sa2.lcp[i] > maxL){ maxL = sa2.lcp[i]; idx = sa2.sa[i]; }
        }
        if(maxL > 0) pr("Longest repeated substring in '" + t + "' is '" + t.substring(idx, idx+maxL) + "'");
        else pr("No repeated substring found in '" + t + "'");

        pln("\nDemo complete. Explore, modify, and reuse these classes for complex problems!");
    }
}
