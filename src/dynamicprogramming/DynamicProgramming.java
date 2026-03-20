package dynamicprogramming;

import java.util.*;

/**
 * Dynamic Programming — classic 1-D and 2-D patterns.
 * Core idea: optimal substructure + overlapping subproblems.
 *
 * LeetCode problems covered:
 *   Climbing Stairs (#70), House Robber (#198), Coin Change (#322),
 *   Longest Increasing Subsequence (#300), Longest Common Subsequence (#1143),
 *   0/1 Knapsack, Edit Distance (#72), Word Break (#139),
 *   Maximum Subarray — Kadane's (#53)
 */
public class DynamicProgramming {

    // ------------------------------------------------------------------
    // 1. Climbing Stairs (#70) — Fibonacci variant
    //    dp[i] = dp[i-1] + dp[i-2]
    // ------------------------------------------------------------------
    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    // ------------------------------------------------------------------
    // 2. House Robber (#198)
    //    dp[i] = max(dp[i-1], dp[i-2] + nums[i])
    // ------------------------------------------------------------------
    public static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int num : nums) {
            int curr = Math.max(prev1, prev2 + num);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    // ------------------------------------------------------------------
    // 3. Coin Change (#322) — unbounded knapsack
    //    dp[i] = min coins to make amount i
    // ------------------------------------------------------------------
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    // ------------------------------------------------------------------
    // 4. Longest Increasing Subsequence (#300) — O(n log n) with patience sort
    // ------------------------------------------------------------------
    public static int lengthOfLIS(int[] nums) {
        List<Integer> tails = new ArrayList<>();
        for (int num : nums) {
            int lo = 0, hi = tails.size();
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails.get(mid) < num) lo = mid + 1;
                else                      hi = mid;
            }
            if (lo == tails.size()) tails.add(num);
            else                    tails.set(lo, num);
        }
        return tails.size();
    }

    // ------------------------------------------------------------------
    // 5. Longest Common Subsequence (#1143) — O(m*n)
    // ------------------------------------------------------------------
    public static int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[m][n];
    }

    // ------------------------------------------------------------------
    // 6. 0/1 Knapsack — pick items to maximize value within weight W
    // ------------------------------------------------------------------
    public static int knapsack(int[] weights, int[] values, int W) {
        int n = weights.length;
        int[] dp = new int[W + 1]; // space-optimized 1-D

        for (int i = 0; i < n; i++) {
            for (int w = W; w >= weights[i]; w--) { // traverse right-to-left to avoid reuse
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
        return dp[W];
    }

    // ------------------------------------------------------------------
    // 7. Edit Distance (#72) — Levenshtein distance
    // ------------------------------------------------------------------
    public static int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[] dp = new int[n + 1];
        for (int j = 0; j <= n; j++) dp[j] = j;

        for (int i = 1; i <= m; i++) {
            int prev = dp[0];
            dp[0] = i;
            for (int j = 1; j <= n; j++) {
                int temp = dp[j];
                if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dp[j] = prev;
                else
                    dp[j] = 1 + Math.min(prev, Math.min(dp[j], dp[j - 1]));
                prev = temp;
            }
        }
        return dp[n];
    }

    // ------------------------------------------------------------------
    // 8. Word Break (#139)
    // ------------------------------------------------------------------
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    // ------------------------------------------------------------------
    // 9. Maximum Subarray — Kadane's Algorithm (#53)
    // ------------------------------------------------------------------
    public static int maxSubArray(int[] nums) {
        int maxSum = nums[0], curr = nums[0];
        for (int i = 1; i < nums.length; i++) {
            curr = Math.max(nums[i], curr + nums[i]);
            maxSum = Math.max(maxSum, curr);
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(5));                                       // 8
        System.out.println(rob(new int[]{2, 7, 9, 3, 1}));                        // 12
        System.out.println(coinChange(new int[]{1, 5, 11}, 15));                  // 3 (5+5+5)
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18})); // 4
        System.out.println(longestCommonSubsequence("abcde", "ace"));             // 3
        System.out.println(knapsack(new int[]{2,3,4,5}, new int[]{3,4,5,6}, 8)); // 10
        System.out.println(minDistance("horse", "ros"));                           // 3
        System.out.println(wordBreak("leetcode", Arrays.asList("leet", "code"))); // true
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
    }
}
