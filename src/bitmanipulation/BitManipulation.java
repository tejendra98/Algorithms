package bitmanipulation;

/**
 * Bit Manipulation tricks and patterns.
 *
 * Key operations:
 *   x & (x-1)   → clear lowest set bit
 *   x & (-x)    → isolate lowest set bit
 *   x ^ x = 0   → XOR self cancels
 *   x ^ 0 = x   → XOR with zero identity
 *
 * LeetCode problems:
 *   Single Number (#136, #137, #260), Number of 1 Bits (#191),
 *   Counting Bits (#338), Reverse Bits (#190),
 *   Missing Number (#268), Power of Two (#231),
 *   Sum of Two Integers (#371), Subsets via bitmask (#78)
 */
public class BitManipulation {

    // ------------------------------------------------------------------
    // 1. Single Number — every element appears twice except one (#136)
    //    XOR all: duplicates cancel out
    // ------------------------------------------------------------------
    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int n : nums) result ^= n;
        return result;
    }

    // ------------------------------------------------------------------
    // 2. Number of 1 Bits (popcount / Hamming weight) (#191)
    // ------------------------------------------------------------------
    public static int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1); // clears lowest set bit
            count++;
        }
        return count;
    }

    // ------------------------------------------------------------------
    // 3. Counting Bits — for 0..n, count 1-bits in each (#338)
    //    dp[i] = dp[i >> 1] + (i & 1)
    // ------------------------------------------------------------------
    public static int[] countBits(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) dp[i] = dp[i >> 1] + (i & 1);
        return dp;
    }

    // ------------------------------------------------------------------
    // 4. Reverse Bits (#190)
    // ------------------------------------------------------------------
    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1);
            n >>>= 1; // unsigned right shift
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 5. Missing Number in [0..n] (#268)
    //    XOR indices 0..n with all nums — unpaired index is missing
    // ------------------------------------------------------------------
    public static int missingNumber(int[] nums) {
        int missing = nums.length;
        for (int i = 0; i < nums.length; i++) missing ^= i ^ nums[i];
        return missing;
    }

    // ------------------------------------------------------------------
    // 6. Power of Two (#231)
    //    A power of two has exactly one set bit: n & (n-1) == 0
    // ------------------------------------------------------------------
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    // ------------------------------------------------------------------
    // 7. Sum of Two Integers without + operator (#371)
    // ------------------------------------------------------------------
    public static int getSum(int a, int b) {
        while (b != 0) {
            int carry = (a & b) << 1; // carry bits
            a = a ^ b;                // sum without carry
            b = carry;
        }
        return a;
    }

    // ------------------------------------------------------------------
    // 8. Find Two Non-Repeating Numbers (#260) — Single Number III
    //    XOR all → xor of two unique numbers → split by any differing bit
    // ------------------------------------------------------------------
    public static int[] singleNumberIII(int[] nums) {
        int xor = 0;
        for (int n : nums) xor ^= n;

        int diffBit = xor & (-xor); // isolate lowest set bit where the two numbers differ
        int a = 0, b = 0;
        for (int n : nums) {
            if ((n & diffBit) != 0) a ^= n;
            else                    b ^= n;
        }
        return new int[]{a, b};
    }

    // ------------------------------------------------------------------
    // 9. Enumerate all subsets via bitmask
    // ------------------------------------------------------------------
    public static void enumerateSubsets(int[] nums) {
        int n = nums.length;
        for (int mask = 0; mask < (1 << n); mask++) {
            System.out.print("{ ");
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) System.out.print(nums[i] + " ");
            }
            System.out.print("}");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{4, 1, 2, 1, 2}));          // 4
        System.out.println(hammingWeight(11));                                 // 3  (1011)
        System.out.println(java.util.Arrays.toString(countBits(5)));          // [0,1,1,2,1,2]
        System.out.println(missingNumber(new int[]{3, 0, 1}));                // 2
        System.out.println(isPowerOfTwo(16));                                  // true
        System.out.println(getSum(3, 5));                                      // 8
        System.out.println(java.util.Arrays.toString(singleNumberIII(new int[]{1,2,1,3,2,5}))); // [3, 5]
        enumerateSubsets(new int[]{1, 2, 3});
    }
}
