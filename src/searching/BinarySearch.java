package searching;

/**
 * Binary Search and its common variants.
 * Time:  O(log n)
 * Space: O(1) iterative / O(log n) recursive
 *
 * LeetCode problems:
 *   Classic:       Binary Search (#704)
 *   First/Last:    Find First and Last Position (#34)
 *   Rotated array: Search in Rotated Sorted Array (#33)
 *   Answer space:  Koko Eating Bananas (#875), Split Array Largest Sum (#410)
 */
public class BinarySearch {

    // ------------------------------------------------------------------
    // 1. Classic binary search — exact target
    // ------------------------------------------------------------------
    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if      (nums[mid] == target) return mid;
            else if (nums[mid] < target)  lo = mid + 1;
            else                          hi = mid - 1;
        }
        return -1;
    }

    // ------------------------------------------------------------------
    // 2. Lower bound — first index where nums[i] >= target
    // ------------------------------------------------------------------
    public static int lowerBound(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) lo = mid + 1;
            else                    hi = mid;
        }
        return lo;
    }

    // ------------------------------------------------------------------
    // 3. Upper bound — first index where nums[i] > target
    // ------------------------------------------------------------------
    public static int upperBound(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] <= target) lo = mid + 1;
            else                     hi = mid;
        }
        return lo;
    }

    // ------------------------------------------------------------------
    // 4. Search in rotated sorted array (#33)
    // ------------------------------------------------------------------
    public static int searchRotated(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;

            if (nums[lo] <= nums[mid]) {            // left half sorted
                if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
                else                                          lo = mid + 1;
            } else {                                // right half sorted
                if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
                else                                          hi = mid - 1;
            }
        }
        return -1;
    }

    // ------------------------------------------------------------------
    // 5. Binary search on answer space
    //    Template: find minimum value m such that feasible(m) == true
    // ------------------------------------------------------------------
    // Example: Koko Eating Bananas (#875)
    public static int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canFinish(piles, h, mid)) hi = mid;
            else                          lo = mid + 1;
        }
        return lo;
    }

    private static boolean canFinish(int[] piles, int h, int speed) {
        int hours = 0;
        for (int p : piles) hours += (p + speed - 1) / speed; // ceil division
        return hours <= h;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 7, 9, 11};
        System.out.println(search(arr, 7));           // 3
        System.out.println(lowerBound(arr, 5));       // 2
        System.out.println(upperBound(arr, 5));       // 3

        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(searchRotated(rotated, 0)); // 4

        int[] piles = {3, 6, 7, 11};
        System.out.println(minEatingSpeed(piles, 8));  // 4
    }
}
