package trie;

import java.util.*;

/**
 * Trie (Prefix Tree) — efficient string insert, search, prefix lookup.
 * Time:  O(m) per operation where m = word length
 * Space: O(ALPHABET_SIZE * m * n) for n words
 *
 * LeetCode problems:
 *   Implement Trie (#208), Add and Search Word (#211),
 *   Word Search II (#212), Replace Words (#648),
 *   Longest Word in Dictionary (#720), Auto-Complete
 */
public class Trie {

    private static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd;
    }

    private final TrieNode root = new TrieNode();

    // ------------------------------------------------------------------
    // Insert a word into the trie
    // ------------------------------------------------------------------
    public void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) curr.children[idx] = new TrieNode();
            curr = curr.children[idx];
        }
        curr.isEnd = true;
    }

    // ------------------------------------------------------------------
    // Search for exact word
    // ------------------------------------------------------------------
    public boolean search(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return false;
            curr = curr.children[idx];
        }
        return curr.isEnd;
    }

    // ------------------------------------------------------------------
    // Check if any word starts with given prefix
    // ------------------------------------------------------------------
    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return false;
            curr = curr.children[idx];
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Add and Search Word with '.' wildcard (#211)
    // ------------------------------------------------------------------
    public boolean searchWithWildcard(String word) {
        return dfsSearch(root, word, 0);
    }

    private boolean dfsSearch(TrieNode node, String word, int idx) {
        if (idx == word.length()) return node.isEnd;
        char c = word.charAt(idx);
        if (c == '.') {
            for (TrieNode child : node.children)
                if (child != null && dfsSearch(child, word, idx + 1)) return true;
            return false;
        }
        TrieNode next = node.children[c - 'a'];
        return next != null && dfsSearch(next, word, idx + 1);
    }

    // ------------------------------------------------------------------
    // Replace Words — replace derivative with shortest root in dict (#648)
    // ------------------------------------------------------------------
    public static String replaceWords(List<String> dictionary, String sentence) {
        Trie trie = new Trie();
        for (String root : dictionary) trie.insert(root);

        String[] words = sentence.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(trie.findShortestRoot(words[i]));
        }
        return sb.toString();
    }

    private String findShortestRoot(String word) {
        TrieNode curr = root;
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) break;
            sb.append(c);
            curr = curr.children[idx];
            if (curr.isEnd) return sb.toString(); // found shortest prefix root
        }
        return word; // no root found, return original word
    }

    // ------------------------------------------------------------------
    // Auto-complete — return all words with given prefix
    // ------------------------------------------------------------------
    public List<String> autocomplete(String prefix) {
        List<String> result = new ArrayList<>();
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (curr.children[idx] == null) return result;
            curr = curr.children[idx];
        }
        collectWords(curr, new StringBuilder(prefix), result);
        return result;
    }

    private void collectWords(TrieNode node, StringBuilder current, List<String> result) {
        if (node.isEnd) result.add(current.toString());
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                current.append((char)('a' + i));
                collectWords(node.children[i], current, result);
                current.deleteCharAt(current.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");

        System.out.println(trie.search("app"));           // true
        System.out.println(trie.search("ap"));            // false
        System.out.println(trie.startsWith("app"));       // true
        System.out.println(trie.searchWithWildcard("a.p")); // true
        System.out.println(trie.autocomplete("app"));     // [app, apple, application]

        List<String> dict = Arrays.asList("cat", "bat", "rat");
        System.out.println(replaceWords(dict, "the cattle was rattled by the battery")); // the cat was rat by the bat
    }
}
