/**
 * Created by timothysmith on 2016-11-29.
 */
public class Tree<V> {

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
    }
    private Node<V> root;

    public Tree() {
        root = null;
    }

    public Tree(V v) {
        root = new Node(v);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void appendLeft(Tree tree) {
        root.left = tree.root;
    }

    public void appendRight(Tree tree) {
        root.right = tree.root;
    }
}
