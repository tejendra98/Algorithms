package graph;

import java.util.*;

/**
 * Graph BFS and DFS patterns on adjacency-list graphs.
 * Time:  O(V + E)
 * Space: O(V)
 *
 * LeetCode problems:
 *   Number of Islands (#200), Clone Graph (#133),
 *   Course Schedule (#207) — cycle detection,
 *   Topological Sort / Course Schedule II (#210),
 *   Word Ladder (#127) — BFS shortest path,
 *   Surrounded Regions (#130), Pacific Atlantic Water Flow (#417)
 */
public class GraphTraversal {

    // ------------------------------------------------------------------
    // 1. BFS — shortest path (unweighted graph)
    // ------------------------------------------------------------------
    public static int[] bfsShortestPath(List<List<Integer>> graph, int src) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[src] = 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(src);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph.get(node)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[node] + 1;
                    queue.offer(neighbor);
                }
            }
        }
        return dist;
    }

    // ------------------------------------------------------------------
    // 2. DFS — iterative with explicit stack
    // ------------------------------------------------------------------
    public static void dfsIterative(List<List<Integer>> graph, int src) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(src);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (visited[node]) continue;
            visited[node] = true;
            System.out.print(node + " ");
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) stack.push(neighbor);
            }
        }
        System.out.println();
    }

    // ------------------------------------------------------------------
    // 3. Cycle detection in directed graph (DFS with coloring)
    //    0 = unvisited, 1 = in-progress, 2 = done
    //    Used in: Course Schedule (#207)
    // ------------------------------------------------------------------
    public static boolean hasCycle(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n];
        for (int i = 0; i < n; i++) {
            if (color[i] == 0 && dfsCycle(graph, i, color)) return true;
        }
        return false;
    }

    private static boolean dfsCycle(List<List<Integer>> graph, int node, int[] color) {
        color[node] = 1; // mark in-progress
        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == 1) return true;  // back edge = cycle
            if (color[neighbor] == 0 && dfsCycle(graph, neighbor, color)) return true;
        }
        color[node] = 2; // mark done
        return false;
    }

    // ------------------------------------------------------------------
    // 4. Topological Sort — Kahn's Algorithm (BFS)
    //    Used in: Course Schedule II (#210)
    // ------------------------------------------------------------------
    public static int[] topoSort(int n, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
            inDegree[pre[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.offer(i);

        int[] order = new int[n];
        int idx = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            order[idx++] = node;
            for (int neighbor : adj.get(node)) {
                if (--inDegree[neighbor] == 0) queue.offer(neighbor);
            }
        }
        return idx == n ? order : new int[]{}; // empty if cycle
    }

    // ------------------------------------------------------------------
    // 5. Number of Islands — DFS on 2-D grid (#200)
    // ------------------------------------------------------------------
    public static int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfsGrid(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfsGrid(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1')
            return;
        grid[i][j] = '0'; // mark visited
        dfsGrid(grid, i + 1, j);
        dfsGrid(grid, i - 1, j);
        dfsGrid(grid, i, j + 1);
        dfsGrid(grid, i, j - 1);
    }

    // ------------------------------------------------------------------
    // 6. Dijkstra's Algorithm — single-source shortest path (weighted)
    // ------------------------------------------------------------------
    public static int[] dijkstra(List<List<int[]>> graph, int src) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // PriorityQueue: [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, src});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], node = curr[1];
            if (d > dist[node]) continue; // stale entry

            for (int[] edge : graph.get(node)) {
                int neighbor = edge[0], weight = edge[1];
                if (dist[node] + weight < dist[neighbor]) {
                    dist[neighbor] = dist[node] + weight;
                    pq.offer(new int[]{dist[neighbor], neighbor});
                }
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        // Build sample graph: 0-1, 0-2, 1-3, 2-3
        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        graph.get(0).add(1); graph.get(0).add(2);
        graph.get(1).add(3); graph.get(2).add(3);

        System.out.println(Arrays.toString(bfsShortestPath(graph, 0))); // [0, 1, 1, 2]
        dfsIterative(graph, 0);

        // Topo sort: [[1,0],[2,0],[3,1],[3,2]] → 4 courses
        int[][] prereqs = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        System.out.println(Arrays.toString(topoSort(4, prereqs)));       // [0, 1, 2, 3] or similar

        char[][] grid = {{'1','1','0'},{'1','0','0'},{'0','0','1'}};
        System.out.println(numIslands(grid)); // 2
    }
}
