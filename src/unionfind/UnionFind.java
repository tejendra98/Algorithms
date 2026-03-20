package unionfind;

/**
 * Union-Find (Disjoint Set Union) with path compression + union by rank.
 * Time: O(α(n)) ≈ O(1) amortized per operation  (α = inverse Ackermann)
 * Space: O(n)
 *
 * LeetCode problems:
 *   Number of Connected Components (#323), Redundant Connection (#684),
 *   Accounts Merge (#721), Number of Islands II (#305),
 *   Most Stones Removed (#947), Satisfiability of Equality Equations (#990)
 */
public class UnionFind {

    private final int[] parent;
    private final int[] rank;
    private int components;

    public UnionFind(int n) {
        parent = new int[n];
        rank   = new int[n];
        components = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    // Path compression — flatten the tree on every find
    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    // Union by rank — attach smaller tree under larger tree
    public boolean union(int x, int y) {
        int px = find(x), py = find(y);
        if (px == py) return false; // already connected

        if      (rank[px] < rank[py]) parent[px] = py;
        else if (rank[px] > rank[py]) parent[py] = px;
        else { parent[py] = px; rank[px]++; }

        components--;
        return true;
    }

    public boolean connected(int x, int y) { return find(x) == find(y); }

    public int getComponents() { return components; }

    // ------------------------------------------------------------------
    // Example 1: Number of Connected Components (#323)
    // ------------------------------------------------------------------
    public static int countComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) uf.union(edge[0], edge[1]);
        return uf.getComponents();
    }

    // ------------------------------------------------------------------
    // Example 2: Redundant Connection — find edge that forms cycle (#684)
    // ------------------------------------------------------------------
    public static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        UnionFind uf = new UnionFind(n + 1);
        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) return edge; // already in same set
        }
        return new int[]{};
    }

    // ------------------------------------------------------------------
    // Example 3: Satisfiability of Equality Equations (#990)
    // ------------------------------------------------------------------
    public static boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind(26);

        // First pass: process all "==" equations
        for (String eq : equations) {
            if (eq.charAt(1) == '=')
                uf.union(eq.charAt(0) - 'a', eq.charAt(3) - 'a');
        }
        // Second pass: check "!=" equations
        for (String eq : equations) {
            if (eq.charAt(1) == '!' && uf.connected(eq.charAt(0) - 'a', eq.charAt(3) - 'a'))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] edges1 = {{0,1},{1,2},{3,4}};
        System.out.println(countComponents(5, edges1)); // 2

        int[][] edges2 = {{1,2},{1,3},{2,3}};
        System.out.println(java.util.Arrays.toString(findRedundantConnection(edges2))); // [2, 3]

        String[] equations = {"a==b","b!=c","c==a"};
        System.out.println(equationsPossible(equations)); // false
    }
}
