package sorting;

/**
 * Merge Sort — Divide & Conquer, stable sort.
 * Time:  O(n log n) in all cases
 * Space: O(n) auxiliary
 *
 * LeetCode problems: Sort an Array (#912), Merge Sorted Array (#88),
 *                    Count of Smaller Numbers After Self (#315)
 */
public class MergeSort {

    public static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) temp[k++] = arr[i++];
            else                  temp[k++] = arr[j++];
        }
        while (i <= mid)    temp[k++] = arr[i++];
        while (j <= right)  temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // ---------------------------------------------------------------
    // Variant: count inversions while sorting (used in #315, #493)
    // ---------------------------------------------------------------
    public static long countInversions(int[] arr, int left, int right) {
        if (left >= right) return 0;

        int mid = left + (right - left) / 2;
        long count = countInversions(arr, left, mid)
                   + countInversions(arr, mid + 1, right);

        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                count += (mid - i + 1); // all remaining left elements > arr[j]
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid)   temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);
        return count;
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9, 3};
        mergeSort(arr, 0, arr.length - 1);
        for (int x : arr) System.out.print(x + " "); // 1 2 3 5 8 9
        System.out.println();

        int[] arr2 = {5, 2, 8, 1};
        System.out.println("Inversions: " + countInversions(arr2, 0, arr2.length - 1)); // 4
    }
}
