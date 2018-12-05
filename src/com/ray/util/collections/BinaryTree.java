package com.ray.util.collections;

import static com.ray.io.Out.p;

import com.ray.io.Out;

public class BinaryTree<K extends Comparable<K>,V > {

    private class Node {
        Node left;
        Node right;
        int size;
        K key;
        V val;
        public Node(K k, V v) {
            key = k;
            val = v;
            size = 1;
        }
    }
    
    Node root;
    
    /**
     * 插入新元素
     * @param k
     * @param v
     */
    public void put(K k, V v) {
        root = put(root, k, v);
    }

    private BinaryTree<K, V>.Node put(Node node, K k, V v) {
        if (node == null) return new Node(k, v);
        int c = node.key.compareTo(k);
        if (c == 0) {
            node.val = v;
        } else if (c > 0) {
            // 放入左子树
            node.left = put(node.left, k, v);
        } else {
            // 放入右子树
            node.right = put(node.right, k, v);            
        }
        node.size = size(node.left) + size(node.right);
        return node;
    }
    
    /**
     * 获取元素
     * @param k
     * @return
     */
    public V get(K k) {
        return get(root, k);
    }
    
    private V get(Node n, K k) {
        if (n == null) return null;
        int c = n.key.compareTo(k);
        if (c == 0)
            return n.val;
        else if (c > 0)
            return get(n.left, k);
        else
            return get(n.right, k);
    }
    
    /**
     * 删除最小元素
     */
    public void removeMin() {
        root = removeMin(root);
    }
    
    private Node removeMin(Node n) {
        if (n == null) return n;
        if (n.left == null) return n.right;
        n.left = removeMin(n.left);
        n.size = size(n.left) + size(n.right);
       return n;
    }
    
    /**
     * 删除最大元素
     */
    public void removeMax() {
        root = removeMax(root);
    }
    
    private Node removeMax(Node n) {
        if (n == null) return n;
        if (n.right == null) return n.left;
        n.right = removeMax(n.right);
        n.size = size(n.left) + size(n.right);
        return n;
    }

    private int size(Node n) {
        return (n==null) ? 0 : n.size;
    }
    
    public void show() {
        p("== tree ===========================");
        if (root != null) tree(root, 1);
        p("===================================");
    }

    private void tree(Node node, int deepth) {
        if(node == null) return;
        tree(node.right, deepth+1);
        Out.pf("%"+(deepth*10)+"s", " ");
        Out.pf("--[%2s,s:%2s]\n", node.key, node.size);
        tree(node.left, deepth+1);
    }
    
    public static void main(String[] args) {
        
        
        Integer[] arr = {2,1,8,4,3,9,5,7};
        BinaryTree<Integer, Integer> tree = new BinaryTree<>();
        for (Integer num : arr) {
            tree.put(num, num);
        }
        
        tree.show();
        tree.removeMin();
        tree.show();
        tree.removeMax();
        tree.show();
        
    }
    
}
