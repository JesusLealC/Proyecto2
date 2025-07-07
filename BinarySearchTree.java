import java.util.*;

public class BinarySearchTree {
    private Node root;

    private class Node {
        String pattern;
        int count;
        Node left, right;

        public Node(String pattern, int count) {
            this.pattern = pattern;
            this.count = count;
        }
    }

    public void insertOrUpdate(String pattern, int count) {
        root = insertOrUpdate(root, pattern, count);
    }

    private Node insertOrUpdate(Node node, String pattern, int count) {
        if (node == null) {
            return new Node(pattern, count);
        }

        if (pattern.equals(node.pattern)) {
            node.count = count;
        } else if (count < node.count || (count == node.count && pattern.compareTo(node.pattern) < 0)) {
            node.left = insertOrUpdate(node.left, pattern, count);
        } else {
            node.right = insertOrUpdate(node.right, pattern, count);
        }

        return node;
    }

    public List<String> getPatternsInOrder() {
        List<String> patterns = new ArrayList<>();
        inOrderTraversal(root, patterns);
        return patterns;
    }

    private void inOrderTraversal(Node node, List<String> patterns) {
        if (node != null) {
            inOrderTraversal(node.right, patterns); // Primero el derecho para orden descendente
            patterns.add(node.pattern);
            inOrderTraversal(node.left, patterns);
        }
    }

    public String getMaxPattern() {
        if (root == null) return null;
        return getMaxNode(root).pattern;
    }

    private Node getMaxNode(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public String getMinPattern() {
        if (root == null) return null;
        return getMinNode(root).pattern;
    }

    private Node getMinNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
