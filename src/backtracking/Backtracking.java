package backtracking;

import java.util.*;

/**
 * Backtracking — exhaustive search with pruning.
 * Template:
 *   void backtrack(state, choices) {
 *     if (base case) { add to result; return; }
 *     for each choice:
 *       make choice → recurse → undo choice
 *   }
 *
 * LeetCode problems:
 *   Permutations (#46), Subsets (#78), Combinations (#77),
 *   Combination Sum (#39), Palindrome Partitioning (#131),
 *   N-Queens (#51), Sudoku Solver (#37), Letter Combinations (#17)
 */
public class Backtracking {

    // ------------------------------------------------------------------
    // 1. Permutations — all orderings of unique elements (#46)
    // ------------------------------------------------------------------
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private static void permuteHelper(int[] nums, boolean[] used,
                                      List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            current.add(nums[i]);
            permuteHelper(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }

    // ------------------------------------------------------------------
    // 2. Subsets — power set (#78)
    // ------------------------------------------------------------------
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        subsetsHelper(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void subsetsHelper(int[] nums, int start,
                                      List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current)); // add snapshot at every node
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            subsetsHelper(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    // ------------------------------------------------------------------
    // 3. Combination Sum — elements may repeat, sum must equal target (#39)
    // ------------------------------------------------------------------
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        combinationHelper(candidates, 0, target, new ArrayList<>(), result);
        return result;
    }

    private static void combinationHelper(int[] candidates, int start, int remaining,
                                           List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) { result.add(new ArrayList<>(current)); return; }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remaining) break; // pruning
            current.add(candidates[i]);
            combinationHelper(candidates, i, remaining - candidates[i], current, result); // i, not i+1 (reuse)
            current.remove(current.size() - 1);
        }
    }

    // ------------------------------------------------------------------
    // 4. Palindrome Partitioning (#131)
    // ------------------------------------------------------------------
    public static List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        partitionHelper(s, 0, new ArrayList<>(), result);
        return result;
    }

    private static void partitionHelper(String s, int start,
                                         List<String> current, List<List<String>> result) {
        if (start == s.length()) { result.add(new ArrayList<>(current)); return; }
        for (int end = start + 1; end <= s.length(); end++) {
            String sub = s.substring(start, end);
            if (isPalindrome(sub)) {
                current.add(sub);
                partitionHelper(s, end, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    // ------------------------------------------------------------------
    // 5. N-Queens (#51)
    // ------------------------------------------------------------------
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        int[] queens = new int[n]; // queens[row] = col
        Arrays.fill(queens, -1);
        Set<Integer> cols = new HashSet<>(), diag1 = new HashSet<>(), diag2 = new HashSet<>();
        nQueensHelper(n, 0, queens, cols, diag1, diag2, result);
        return result;
    }

    private static void nQueensHelper(int n, int row, int[] queens,
                                       Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2,
                                       List<List<String>> result) {
        if (row == n) {
            result.add(buildBoard(queens, n));
            return;
        }
        for (int col = 0; col < n; col++) {
            if (cols.contains(col) || diag1.contains(row - col) || diag2.contains(row + col))
                continue;
            queens[row] = col;
            cols.add(col); diag1.add(row - col); diag2.add(row + col);
            nQueensHelper(n, row + 1, queens, cols, diag1, diag2, result);
            queens[row] = -1;
            cols.remove(col); diag1.remove(row - col); diag2.remove(row + col);
        }
    }

    private static List<String> buildBoard(int[] queens, int n) {
        List<String> board = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            char[] line = new char[n];
            Arrays.fill(line, '.');
            line[queens[row]] = 'Q';
            board.add(new String(line));
        }
        return board;
    }

    private static boolean isPalindrome(String s) {
        int lo = 0, hi = s.length() - 1;
        while (lo < hi) if (s.charAt(lo++) != s.charAt(hi--)) return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));
        System.out.println(subsets(new int[]{1, 2, 3}));
        System.out.println(combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println(partition("aab"));
        System.out.println("4-Queens solutions: " + solveNQueens(4).size()); // 2
    }
}
