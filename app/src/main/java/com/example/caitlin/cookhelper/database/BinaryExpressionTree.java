package com.example.caitlin.cookhelper.database;

/**
 * Represents a Tree with utilities for representing an algebraic expression.
 * @param <V> Type of element of a node in a tree
 */
public class BinaryExpressionTree<V> {

    class Node<V> {
        private Node right;
        private Node left;
        private Node parent;
        private V element;

        public V getElement() {
            return element;
        }

        public void setElement(V element) {
            this.element = element;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node(V element) {
            this.element = element;
        }

        public boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        public boolean isRightChild() {
            return parent != null && parent.right == this;
        }

        public boolean isLeaf() {
            return (left == null);
        }
    }
    private Node<V> root;

    public BinaryExpressionTree() {
        root = null;
    }

    public BinaryExpressionTree(V v) {
        root = new Node(v);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void appendLeft(BinaryExpressionTree tree) {
        root.left = tree.root;
    }

    public void appendRight(BinaryExpressionTree tree) {
        root.right = tree.root;
    }

}
