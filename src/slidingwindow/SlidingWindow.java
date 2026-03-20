package slidingwindow;

import java.util.*;

/**
 * Sliding Window patterns — fixed and variable size.
 * Time:  O(n)
 * Space: O(k) for window state
 *
 * LeetCode problems:
 *   Max Sum Subarray of Size K (fixed)
 *   Longest Substring Without Repeating Characters (#3)
 *   Longest Substring with At Most K Distinct (#340)
 *   Minimum Window Substring (#76)
 *   Permutation in String (#567)
 *   Sliding Window Maximum (#239)
 */
public class SlidingWindow {

    // ------------------------------------------------------------------
    // 1. Fixed window — max sum of subarray of size k
    // ------------------------------------------------------------------
    public static int maxSumSubarray(int[] nums, int k) {
        int windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += nums[i];

        int maxSum = windowSum;
        for (int i = k; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }
        return maxSum;
    }

    // ------------------------------------------------------------------
    // 2. Longest substring without repeating characters (#3)
    // ------------------------------------------------------------------
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int max = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }
            lastSeen.put(c, right);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    // ------------------------------------------------------------------
    // 3. Longest substring with at most k distinct characters (#340)
    // ------------------------------------------------------------------
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> freq = new HashMap<>();
        int max = 0, left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            freq.merge(c, 1, Integer::sum);

            while (freq.size() > k) {
                char lc = s.charAt(left++);
                freq.merge(lc, -1, Integer::sum);
                if (freq.get(lc) == 0) freq.remove(lc);
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    // ------------------------------------------------------------------
    // 4. Minimum window substring (#76)
    // ------------------------------------------------------------------
    public static String minWindow(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) return "";

        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;

        int have = 0, required = t.length();
        int left = 0, minLen = Integer.MAX_VALUE, minLeft = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) have++;
            need[c]--;

            while (have == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                char lc = s.charAt(left++);
                need[lc]++;
                if (need[lc] > 0) have--;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }

    // ------------------------------------------------------------------
    // 5. Permutation in string (#567)
    // ------------------------------------------------------------------
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;

        int[] count = new int[26];
        for (char c : s1.toCharArray()) count[c - 'a']++;

        int left = 0, matches = 0;
        // count letters in s1 that are fully matched
        for (int i = 0; i < 26; i++) if (count[i] == 0) matches++;

        for (int right = 0; right < s2.length(); right++) {
            int c = s2.charAt(right) - 'a';
            count[c]--;
            if (count[c] == 0) matches++;

            if (right >= s1.length()) {
                int lc = s2.charAt(left++) - 'a';
                if (count[lc] == 0) matches--;
                count[lc]++;
            }
            if (matches == 26) return true;
        }
        return false;
    }

    // ------------------------------------------------------------------
    // 6. Sliding window maximum — monotonic deque (#239)
    // ------------------------------------------------------------------
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // stores indices, front = max

        for (int i = 0; i < n; i++) {
            // remove elements outside window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1)
                deque.pollFirst();
            // maintain decreasing order
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i])
                deque.pollLast();
            deque.offerLast(i);
            if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3));            // 9
        System.out.println(lengthOfLongestSubstring("abcabcbb"));                       // 3
        System.out.println(lengthOfLongestSubstringKDistinct("eceba", 2));              // 3
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));                          // "BANC"
        System.out.println(checkInclusion("ab", "eidbaooo"));                           // true
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3))); // [3,3,5,5,6,7]
    }
}
