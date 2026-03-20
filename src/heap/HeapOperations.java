package heap;

import java.util.*;

/**
 * Heap / Priority Queue patterns.
 * Java's PriorityQueue is a min-heap by default.
 * Time: O(log n) insert/remove, O(1) peek
 *
 * LeetCode problems:
 *   Kth Largest Element (#215), Top K Frequent (#347),
 *   Merge K Sorted Lists (#23), Find Median from Data Stream (#295),
 *   Task Scheduler (#621), K Closest Points to Origin (#973),
 *   Ugly Number II (#264)
 */
public class HeapOperations {

    // ------------------------------------------------------------------
    // 1. Kth Largest Element in Array (#215)
    //    Min-heap of size k — root is always the kth largest
    // ------------------------------------------------------------------
    public static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) minHeap.poll(); // evict smallest
        }
        return minHeap.peek();
    }

    // ------------------------------------------------------------------
    // 2. Top K Frequent Elements (#347)
    // ------------------------------------------------------------------
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);

        // Min-heap ordered by frequency
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        for (var entry : freq.entrySet()) {
            minHeap.offer(new int[]{entry.getKey(), entry.getValue()});
            if (minHeap.size() > k) minHeap.poll();
        }

        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) result[i] = minHeap.poll()[0];
        return result;
    }

    // ------------------------------------------------------------------
    // 3. Merge K Sorted Lists (#23)
    // ------------------------------------------------------------------
    static class ListNode {
        int val; ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator.comparingInt(n -> n.val));
        for (ListNode head : lists) if (head != null) minHeap.offer(head);

        ListNode dummy = new ListNode(0), curr = dummy;
        while (!minHeap.isEmpty()) {
            curr.next = minHeap.poll();
            curr = curr.next;
            if (curr.next != null) minHeap.offer(curr.next);
        }
        return dummy.next;
    }

    // ------------------------------------------------------------------
    // 4. Find Median from Data Stream (#295)
    //    Two heaps: maxHeap for lower half, minHeap for upper half
    // ------------------------------------------------------------------
    static class MedianFinder {
        private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        private final PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll()); // balance: push max of lower half to upper

            if (maxHeap.size() < minHeap.size())
                maxHeap.offer(minHeap.poll()); // keep maxHeap size >= minHeap size
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) return maxHeap.peek();
            return (maxHeap.peek() + (double) minHeap.peek()) / 2.0;
        }
    }

    // ------------------------------------------------------------------
    // 5. K Closest Points to Origin (#973)
    // ------------------------------------------------------------------
    public static int[][] kClosest(int[][] points, int k) {
        // Max-heap of size k — farthest at top gets evicted
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
        );
        for (int[] p : points) {
            maxHeap.offer(p);
            if (maxHeap.size() > k) maxHeap.poll();
        }
        return maxHeap.toArray(new int[0][]);
    }

    // ------------------------------------------------------------------
    // 6. Task Scheduler (#621) — greedy with max-heap
    // ------------------------------------------------------------------
    public static int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char t : tasks) freq[t - 'A']++;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int f : freq) if (f > 0) maxHeap.offer(f);

        int time = 0;
        Queue<int[]> cooldown = new LinkedList<>(); // [remaining_freq, available_at]

        while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
            time++;
            if (!maxHeap.isEmpty()) {
                int remaining = maxHeap.poll() - 1;
                if (remaining > 0) cooldown.offer(new int[]{remaining, time + n});
            }
            if (!cooldown.isEmpty() && cooldown.peek()[1] == time)
                maxHeap.offer(cooldown.poll()[0]);
        }
        return time;
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3,2,1,5,6,4}, 2));          // 5
        System.out.println(Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2))); // [1, 2]

        int[][] points = {{1,3},{-2,2},{5,8},{0,1}};
        System.out.println(Arrays.deepToString(kClosest(points, 2)));            // [[-2,2],[0,1]]

        MedianFinder mf = new MedianFinder();
        mf.addNum(1); mf.addNum(2); mf.addNum(3);
        System.out.println(mf.findMedian()); // 2.0

        System.out.println(leastInterval(new char[]{'A','A','A','B','B','B'}, 2)); // 8
    }
}
