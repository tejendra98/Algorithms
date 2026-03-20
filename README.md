# LeetCode Algorithms in Java

A curated reference of the most frequently tested algorithmic patterns on LeetCode, implemented cleanly in Java with comments, complexity analysis, and direct problem mappings.

---

## Repository Structure

```
src/
├── sorting/
│   ├── MergeSort.java
│   └── QuickSort.java
├── searching/
│   └── BinarySearch.java
├── twopointers/
│   └── TwoPointers.java
├── slidingwindow/
│   └── SlidingWindow.java
├── dynamicprogramming/
│   └── DynamicProgramming.java
├── graph/
│   └── GraphTraversal.java
├── tree/
│   └── TreeTraversals.java
├── backtracking/
│   └── Backtracking.java
├── unionfind/
│   └── UnionFind.java
├── trie/
│   └── Trie.java
├── heap/
│   └── HeapOperations.java
└── bitmanipulation/
    └── BitManipulation.java
```

---

## Algorithm Overview

### 1. Sorting — `src/sorting/`

| Algorithm | Time | Space | Stable |
|-----------|------|-------|--------|
| Merge Sort | O(n log n) | O(n) | Yes |
| Quick Sort | O(n log n) avg / O(n²) worst | O(log n) | No |

**MergeSort.java**
- Classic divide-and-conquer merge sort
- **Variant:** Count Inversions while sorting → used in [Count of Smaller Numbers After Self #315](https://leetcode.com/problems/count-of-smaller-numbers-after-self/)

**QuickSort.java**
- Lomuto partition with random pivot to avoid worst case
- **Variant:** Quick Select → find k-th smallest/largest in O(n) average → [Kth Largest Element #215](https://leetcode.com/problems/kth-largest-element-in-an-array/)

---

### 2. Binary Search — `src/searching/BinarySearch.java`

**Time:** O(log n) &nbsp; **Space:** O(1)

| Variant | Description | Key LeetCode |
|---------|-------------|-------------|
| Classic | Exact target search | [#704](https://leetcode.com/problems/binary-search/) |
| Lower Bound | First index `≥ target` | [#34](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) |
| Upper Bound | First index `> target` | [#34](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) |
| Rotated Array | Search in rotated sorted array | [#33](https://leetcode.com/problems/search-in-rotated-sorted-array/) |
| Answer Space | Binary search on the answer value | [#875](https://leetcode.com/problems/koko-eating-bananas/), [#410](https://leetcode.com/problems/split-array-largest-sum/) |

**Answer-space template:**
```java
int lo = minPossible, hi = maxPossible;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (feasible(mid)) hi = mid;
    else               lo = mid + 1;
}
return lo;
```

---

### 3. Two Pointers — `src/twopointers/TwoPointers.java`

**Time:** O(n) after optional O(n log n) sort &nbsp; **Space:** O(1)

| Pattern | Description | Key LeetCode |
|---------|-------------|-------------|
| Opposite ends | Converging pointers on sorted array | [Two Sum II #167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) |
| 3Sum | Sort + two-pointer inner loop | [#15](https://leetcode.com/problems/3sum/) |
| Container With Most Water | Greedy width shrink | [#11](https://leetcode.com/problems/container-with-most-water/) |
| Trapping Rain Water | Track left/right max | [#42](https://leetcode.com/problems/trapping-rain-water/) |
| Slow/Fast pointer | In-place deduplication | [Remove Duplicates #26](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) |

---

### 4. Sliding Window — `src/slidingwindow/SlidingWindow.java`

**Time:** O(n) &nbsp; **Space:** O(k)

| Pattern | Description | Key LeetCode |
|---------|-------------|-------------|
| Fixed window | Max sum of subarray size k | Classic |
| Variable expand/shrink | Longest substring without repeating chars | [#3](https://leetcode.com/problems/longest-substring-without-repeating-characters/) |
| At-most-k distinct | Longest with ≤ k distinct chars | [#340](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/) |
| Minimum window | Minimum covering substring | [#76](https://leetcode.com/problems/minimum-window-substring/) |
| Fixed + frequency | Permutation in string | [#567](https://leetcode.com/problems/permutation-in-string/) |
| Monotonic deque | Sliding window maximum | [#239](https://leetcode.com/problems/sliding-window-maximum/) |

---

### 5. Dynamic Programming — `src/dynamicprogramming/DynamicProgramming.java`

**Core idea:** Optimal substructure + overlapping subproblems → memoize or tabulate.

| Problem | Recurrence | Key LeetCode |
|---------|-----------|-------------|
| Climbing Stairs | `dp[i] = dp[i-1] + dp[i-2]` | [#70](https://leetcode.com/problems/climbing-stairs/) |
| House Robber | `dp[i] = max(dp[i-1], dp[i-2]+nums[i])` | [#198](https://leetcode.com/problems/house-robber/) |
| Coin Change | `dp[i] = min(dp[i-c]+1)` for each coin | [#322](https://leetcode.com/problems/coin-change/) |
| Longest Increasing Subsequence | Patience sort O(n log n) | [#300](https://leetcode.com/problems/longest-increasing-subsequence/) |
| Longest Common Subsequence | 2-D DP table | [#1143](https://leetcode.com/problems/longest-common-subsequence/) |
| 0/1 Knapsack | 1-D DP, right-to-left | Classic |
| Edit Distance | 2-D DP (space-optimized) | [#72](https://leetcode.com/problems/edit-distance/) |
| Word Break | 1-D boolean DP | [#139](https://leetcode.com/problems/word-break/) |
| Maximum Subarray | Kadane's: `curr = max(num, curr+num)` | [#53](https://leetcode.com/problems/maximum-subarray/) |

---

### 6. Graph — `src/graph/GraphTraversal.java`

**Time:** O(V + E) &nbsp; **Space:** O(V)

| Algorithm | Use Case | Key LeetCode |
|-----------|---------|-------------|
| BFS | Shortest path (unweighted) | [Word Ladder #127](https://leetcode.com/problems/word-ladder/) |
| DFS (iterative) | Connected components, cycle detection | [#200](https://leetcode.com/problems/number-of-islands/) |
| DFS Coloring | Cycle detection in directed graph | [Course Schedule #207](https://leetcode.com/problems/course-schedule/) |
| Kahn's Algorithm | Topological sort (BFS) | [Course Schedule II #210](https://leetcode.com/problems/course-schedule-ii/) |
| Number of Islands | DFS flood fill on 2-D grid | [#200](https://leetcode.com/problems/number-of-islands/) |
| Dijkstra's | Shortest path (weighted, non-negative) | [Network Delay #743](https://leetcode.com/problems/network-delay-time/) |

---

### 7. Tree — `src/tree/TreeTraversals.java`

**Time:** O(n) &nbsp; **Space:** O(h) where h = tree height

| Operation | Approach | Key LeetCode |
|-----------|---------|-------------|
| Inorder | Iterative (stack) | [#94](https://leetcode.com/problems/binary-tree-inorder-traversal/) |
| Preorder | Iterative (stack) | [#144](https://leetcode.com/problems/binary-tree-preorder-traversal/) |
| Level Order | BFS queue | [#102](https://leetcode.com/problems/binary-tree-level-order-traversal/) |
| Max Depth | Recursive | [#104](https://leetcode.com/problems/maximum-depth-of-binary-tree/) |
| Diameter | DFS height post-order | [#543](https://leetcode.com/problems/diameter-of-binary-tree/) |
| Lowest Common Ancestor | Recursive | [#236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) |
| Validate BST | DFS with min/max bounds | [#98](https://leetcode.com/problems/validate-binary-search-tree/) |
| Serialize/Deserialize | Preorder + null markers | [#297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) |

---

### 8. Backtracking — `src/backtracking/Backtracking.java`

**Time:** O(n! or 2ⁿ depending on problem) &nbsp; **Space:** O(n) recursion stack

**Universal template:**
```java
void backtrack(state, start/index, choices) {
    if (base case) { result.add(snapshot); return; }
    for (each valid choice) {
        make choice;
        backtrack(next state);
        undo choice;      // ← this is what makes it "backtracking"
    }
}
```

| Problem | Key Idea | Key LeetCode |
|---------|---------|-------------|
| Permutations | `used[]` boolean array | [#46](https://leetcode.com/problems/permutations/) |
| Subsets | Add snapshot at every recursion node | [#78](https://leetcode.com/problems/subsets/) |
| Combination Sum | Allow reuse, prune if candidate > remaining | [#39](https://leetcode.com/problems/combination-sum/) |
| Palindrome Partitioning | Branch only on palindrome prefixes | [#131](https://leetcode.com/problems/palindrome-partitioning/) |
| N-Queens | Track cols, diagonals with sets | [#51](https://leetcode.com/problems/n-queens/) |

---

### 9. Union-Find (DSU) — `src/unionfind/UnionFind.java`

**Time:** O(α(n)) ≈ O(1) amortized &nbsp; **Space:** O(n)

Optimizations: **path compression** + **union by rank**

| Problem | Key LeetCode |
|---------|-------------|
| Number of Connected Components | [#323](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/) |
| Redundant Connection | [#684](https://leetcode.com/problems/redundant-connection/) |
| Accounts Merge | [#721](https://leetcode.com/problems/accounts-merge/) |
| Satisfiability of Equality Equations | [#990](https://leetcode.com/problems/satisfiability-of-equality-equations/) |

---

### 10. Trie — `src/trie/Trie.java`

**Time:** O(m) per operation (m = word length) &nbsp; **Space:** O(ALPHABET × m × n)

| Operation | Description | Key LeetCode |
|-----------|-------------|-------------|
| Insert / Search / StartsWith | Core trie operations | [#208](https://leetcode.com/problems/implement-trie-prefix-tree/) |
| Wildcard search with `.` | DFS through trie nodes | [#211](https://leetcode.com/problems/design-add-and-search-words-data-structure/) |
| Replace Words | Find shortest prefix root | [#648](https://leetcode.com/problems/replace-words/) |
| Autocomplete | DFS collect all words under prefix | Custom |

---

### 11. Heap / Priority Queue — `src/heap/HeapOperations.java`

**Time:** O(log n) insert/remove, O(1) peek &nbsp; Java's `PriorityQueue` is a **min-heap** by default.

| Pattern | Trick | Key LeetCode |
|---------|-------|-------------|
| Kth Largest | Min-heap of size k | [#215](https://leetcode.com/problems/kth-largest-element-in-an-array/) |
| Top K Frequent | Min-heap keyed by frequency | [#347](https://leetcode.com/problems/top-k-frequent-elements/) |
| Merge K Sorted Lists | Min-heap of list heads | [#23](https://leetcode.com/problems/merge-k-sorted-lists/) |
| Median from Data Stream | Two heaps (max + min) | [#295](https://leetcode.com/problems/find-median-from-data-stream/) |
| K Closest Points | Max-heap of size k | [#973](https://leetcode.com/problems/k-closest-points-to-origin/) |
| Task Scheduler | Max-heap + cooldown queue | [#621](https://leetcode.com/problems/task-scheduler/) |

---

### 12. Bit Manipulation — `src/bitmanipulation/BitManipulation.java`

| Trick | Expression | Key LeetCode |
|-------|-----------|-------------|
| Single Number (XOR cancel) | `result ^= n` | [#136](https://leetcode.com/problems/single-number/) |
| Hamming Weight | `n &= (n-1)` loop | [#191](https://leetcode.com/problems/number-of-1-bits/) |
| Count Bits DP | `dp[i] = dp[i>>1] + (i&1)` | [#338](https://leetcode.com/problems/counting-bits/) |
| Reverse Bits | Shift + OR 32 times | [#190](https://leetcode.com/problems/reverse-bits/) |
| Missing Number | XOR indices with values | [#268](https://leetcode.com/problems/missing-number/) |
| Power of Two | `n > 0 && (n & (n-1)) == 0` | [#231](https://leetcode.com/problems/power-of-two/) |
| Add without `+` | Carry = `(a&b)<<1`, sum = `a^b` | [#371](https://leetcode.com/problems/sum-of-two-integers/) |
| Two Unique Numbers | XOR all, split by differing bit | [#260](https://leetcode.com/problems/single-number-iii/) |
| Enumerate subsets | `for mask in 0..(1<<n)` | [#78](https://leetcode.com/problems/subsets/) |

---

## Quick Complexity Reference

| Category | Time | Space |
|----------|------|-------|
| Merge Sort | O(n log n) | O(n) |
| Quick Sort | O(n log n) avg | O(log n) |
| Binary Search | O(log n) | O(1) |
| Two Pointers | O(n) | O(1) |
| Sliding Window | O(n) | O(k) |
| DP (1-D) | O(n) | O(n) → O(1) optimized |
| DP (2-D) | O(m×n) | O(m×n) → O(n) optimized |
| BFS / DFS | O(V+E) | O(V) |
| Tree traversal | O(n) | O(h) |
| Backtracking | O(2ⁿ or n!) | O(n) |
| Union-Find | O(α(n)) | O(n) |
| Trie | O(m) per op | O(m×n×Σ) |
| Heap (k-th) | O(n log k) | O(k) |
| Bit tricks | O(1) to O(n) | O(1) |

---

## How to Run

Each file has a `main` method with sample test cases.

```bash
# Compile from project root
javac -d out src/**/*.java

# Run a specific class (e.g., BinarySearch)
java -cp out searching.BinarySearch
```
