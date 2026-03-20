package sorting;

/**
 * Quick Sort — Divide & Conquer, in-place.
 * Time:  O(n log n) average, O(n²) worst (mitigated by random pivot)
 * Space: O(log n) stack
 *
 * LeetCode problems: Sort an Array (#912), Kth Largest Element (#215)
 */
public class QuickSort {

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIdx = partition(arr, low, high);
            quickSort(arr, low, pivotIdx - 1);
            quickSort(arr, pivotIdx + 1, high);
        }
    }

    // Lomuto partition — pivot ends at its final sorted position
    private static int partition(int[] arr, int low, int high) {
        // Random pivot to avoid worst-case on sorted input
        int randIdx = low + (int)(Math.random() * (high - low + 1));
        swap(arr, randIdx, high);

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) swap(arr, ++i, j);
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // ---------------------------------------------------------------
    // Variant: Quick Select — find k-th smallest in O(n) average
    // Used in: Kth Largest Element in Array (#215)
    // ---------------------------------------------------------------
    public static int quickSelect(int[] arr, int low, int high, int k) {
        if (low == high) return arr[low];

        int pivotIdx = partition(arr, low, high);

        if (pivotIdx == k)      return arr[pivotIdx];
        else if (pivotIdx < k)  return quickSelect(arr, pivotIdx + 1, high, k);
        else                    return quickSelect(arr, low, pivotIdx - 1, k);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {3, 6, 8, 10, 1, 2, 1};
        quickSort(arr, 0, arr.length - 1);
        for (int x : arr) System.out.print(x + " "); // 1 1 2 3 6 8 10
        System.out.println();

        int[] arr2 = {3, 2, 1, 5, 6, 4};
        // kth largest = (n-k)-th smallest (0-indexed)
        int k = 2; // 2nd largest → index arr.length - k = 4
        System.out.println("2nd largest: " + quickSelect(arr2, 0, arr2.length - 1, arr2.length - k)); // 5
    }
}
