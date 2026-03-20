package tree;

import java.util.*;

/**
 * Binary Tree traversals and common patterns.
 * Time:  O(n)  Space: O(h) where h = tree height
 *
 * LeetCode problems:
 *   Inorder/Preorder/Postorder (#94, #144, #145),
 *   Level Order Traversal (#102), Zigzag Level Order (#103),
 *   Max Depth (#104), Diameter (#543), Path Sum (#112),
 *   Lowest Common Ancestor (#236), Validate BST (#98),
 *   Serialize/Deserialize Binary Tree (#297)
 */
public class TreeTraversals {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // ------------------------------------------------------------------
    // 1. Inorder (Left → Root → Right) — iterative
    // ------------------------------------------------------------------
    public static List<Integer> inorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) { stack.push(curr); curr = curr.left; }
            curr = stack.pop();
            result.add(curr.val);
            curr = curr.right;
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 2. Preorder (Root → Left → Right) — iterative
    // ------------------------------------------------------------------
    public static List<Integer> preorder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) stack.push(node.right);
            if (node.left  != null) stack.push(node.left);
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 3. Level Order BFS (#102)
    // ------------------------------------------------------------------
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left  != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }

    // ------------------------------------------------------------------
    // 4. Maximum Depth (#104)
    // ------------------------------------------------------------------
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // ------------------------------------------------------------------
    // 5. Diameter of Binary Tree (#543)
    //    Diameter = max path through any node's left + right heights
    // ------------------------------------------------------------------
    private static int diameter = 0;

    public static int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        height(root);
        return diameter;
    }

    private static int height(TreeNode node) {
        if (node == null) return 0;
        int left = height(node.left), right = height(node.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    }

    // ------------------------------------------------------------------
    // 6. Lowest Common Ancestor (#236)
    // ------------------------------------------------------------------
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left  = lowestCommonAncestor(root.left,  p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root; // p and q on opposite sides
        return left != null ? left : right;
    }

    // ------------------------------------------------------------------
    // 7. Validate BST (#98)
    // ------------------------------------------------------------------
    public static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    // ------------------------------------------------------------------
    // 8. Serialize and Deserialize Binary Tree (#297)
    // ------------------------------------------------------------------
    public static String serialize(TreeNode root) {
        if (root == null) return "null";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }

    public static TreeNode deserialize(String data) {
        Queue<String> tokens = new LinkedList<>(Arrays.asList(data.split(",")));
        return buildTree(tokens);
    }

    private static TreeNode buildTree(Queue<String> tokens) {
        String val = tokens.poll();
        if ("null".equals(val)) return null;
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left  = buildTree(tokens);
        node.right = buildTree(tokens);
        return node;
    }

    public static void main(String[] args) {
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left  = new TreeNode(2); root.right = new TreeNode(3);
        root.left.left  = new TreeNode(4); root.left.right = new TreeNode(5);

        System.out.println("Inorder:  " + inorder(root));     // [4, 2, 5, 1, 3]
        System.out.println("Preorder: " + preorder(root));    // [1, 2, 4, 5, 3]
        System.out.println("Levels:   " + levelOrder(root));  // [[1], [2, 3], [4, 5]]
        System.out.println("Depth:    " + maxDepth(root));    // 3
        System.out.println("Diameter: " + diameterOfBinaryTree(root)); // 3

        String ser = serialize(root);
        System.out.println("Serialized: " + ser);
        System.out.println("Deserialized inorder: " + inorder(deserialize(ser)));
    }
}
