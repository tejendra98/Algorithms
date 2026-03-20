package twopointers;

import java.util.*;

/**
 * Two Pointers patterns.
 * Time: O(n) or O(n log n) with sort
 * Space: O(1) extra
 *
 * LeetCode problems:
 *   Two Sum II (#167), 3Sum (#15), Container With Most Water (#11),
 *   Trapping Rain Water (#42), Remove Duplicates (#26), Valid Palindrome (#125)
 */
public class TwoPointers {

    // ------------------------------------------------------------------
    // 1. Two Sum II — sorted array, find pair summing to target (#167)
    // ------------------------------------------------------------------
    public static int[] twoSum(int[] numbers, int target) {
        int lo = 0, hi = numbers.length - 1;
        while (lo < hi) {
            int sum = numbers[lo] + numbers[hi];
            if      (sum == target) return new int[]{lo + 1, hi + 1}; // 1-indexed
            else if (sum < target)  lo++;
            else                    hi--;
        }
        return new int[]{-1, -1};
    }

    // ------------------------------------------------------------------
    // 2. 3Sum — find all unique triplets summing to zero (#15)
    // ------------------------------------------------------------------
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // skip duplicates

            int lo = i + 1, hi = nums.length - 1;
            while (lo < hi) {
                int sum = nums[i] + nums[lo] + nums[hi];
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));
                    while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                    while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                    lo++; hi--;
                } else if (sum < 0) lo++;
                else                hi--;
            }
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 3. Container With Most Water (#11)
    // ------------------------------------------------------------------
    public static int maxArea(int[] height) {
        int lo = 0, hi = height.length - 1, max = 0;
        while (lo < hi) {
            max = Math.max(max, Math.min(height[lo], height[hi]) * (hi - lo));
            if (height[lo] < height[hi]) lo++;
            else                         hi--;
        }
        return max;
    }

    // ------------------------------------------------------------------
    // 4. Trapping Rain Water (#42) — two-pointer O(n) space O(1)
    // ------------------------------------------------------------------
    public static int trap(int[] height) {
        int lo = 0, hi = height.length - 1;
        int leftMax = 0, rightMax = 0, water = 0;

        while (lo < hi) {
            if (height[lo] < height[hi]) {
                if (height[lo] >= leftMax) leftMax = height[lo];
                else                       water += leftMax - height[lo];
                lo++;
            } else {
                if (height[hi] >= rightMax) rightMax = height[hi];
                else                        water += rightMax - height[hi];
                hi--;
            }
        }
        return water;
    }

    // ------------------------------------------------------------------
    // 5. Remove Duplicates from Sorted Array (#26)
    // ------------------------------------------------------------------
    public static int removeDuplicates(int[] nums) {
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[fast] != nums[slow]) nums[++slow] = nums[fast];
        }
        return slow + 1;
    }

    // ------------------------------------------------------------------
    // 6. Valid Palindrome (#125)
    // ------------------------------------------------------------------
    public static boolean isPalindrome(String s) {
        int lo = 0, hi = s.length() - 1;
        while (lo < hi) {
            while (lo < hi && !Character.isLetterOrDigit(s.charAt(lo))) lo++;
            while (lo < hi && !Character.isLetterOrDigit(s.charAt(hi))) hi--;
            if (Character.toLowerCase(s.charAt(lo)) != Character.toLowerCase(s.charAt(hi)))
                return false;
            lo++; hi--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));  // [1, 2]
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));             // [[-1,-1,2],[-1,0,1]]
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));        // 49
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));        // true
    }
}
