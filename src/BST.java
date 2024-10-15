import java.util.LinkedList;
import java.util.Random;

public class BST<T extends Comparable<? super T>> {
    protected BSTNode<T> root = null;

    public BST() {
    }
    public static class BSTNode<T extends Comparable<? super T>> {
        protected T el;
        protected BSTNode<T> left, right;
        public BSTNode() {
            left = right = null;
        }
        public BSTNode(T el) {
            this(el,null,null);
        }
        public BSTNode(T el, BSTNode<T> lt, BSTNode<T> rt) {
            this.el = el; left = lt; right = rt;
        }
    }

    public static void main(String[] args) {
        BST<Integer> tree = new BST<>();
        BST<Integer> tree1 = new BST<>();
        Random random = new Random();


        for (int i = 0; i < 6; i++) {
            int key = random.nextInt(15);
            tree1.insert(key);
        }

        tree.insert(8);
        tree.insert(4);
        tree.insert(9);
        tree.insert(2);
        tree.insert(7);


        System.out.print("Breadth First Traversal: ");
        tree.breadthFirst();
        System.out.println();

        System.out.print("Preorder Traversal: ");
        tree.preorder();
        System.out.println();

        System.out.print("Inorder Traversal: ");
        tree.inorder();
        System.out.println();

        System.out.print("Postorder Traversal: ");
        tree.postorder();
        System.out.println();

        int leaves = tree.countLeaves();
        System.out.println("Number of leaves? " + leaves);

        int count = tree.count();
        System.out.println("Number of nodes? " + count);

        System.out.println("Is '4' a leaf? " + tree.isLeaf(4));
        System.out.println("Is '7' a leaf? " + tree.isLeaf(7));
        System.out.println("What is the height? " + tree.getHeight());


        System.out.print("Breadth First Traversal: ");
        tree1.breadthFirst();
        System.out.println();

        System.out.print("Preorder Traversal: ");
        tree1.preorder();
        System.out.println();

        System.out.print("Inorder Traversal: ");
        tree1.inorder();
        System.out.println();

        System.out.print("Postorder Traversal: ");
        tree1.postorder();
        System.out.println();


    }

    public void clear() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(T el) {
        BSTNode<T> p = root, prev = null;
        while (p != null) {  // find a place for inserting new node;
            prev = p;
            if (el.compareTo(p.el) < 0)
                p = p.left;
            else p = p.right;
        }
        if (root == null)    // tree is empty;
            root = new BSTNode<T>(el);
        else if (el.compareTo(prev.el) < 0)
            prev.left = new BSTNode<T>(el);
        else prev.right = new BSTNode<T>(el);
    }

    public void recInsert(T el) {
        root = recInsert(root, el);
    }

    protected BSTNode<T> recInsert(BSTNode<T> p, T el) {
        if (p == null)
            p = new BSTNode<T>(el);
        else if (el.compareTo(p.el) < 0)
            p.left = recInsert(p.left, el);
        else p.right = recInsert(p.right, el);
        return p;
    }

    public boolean isInTree(T el) {
        return search(el) != null;
    }

    protected T search(T el) {
        BSTNode<T> p = root;
        while (p != null)
            if (el.equals(p.el))
                return p.el;
            else if (el.compareTo(p.el) < 0)
                p = p.left;
            else p = p.right;
        return null;
    }

    public void preorder() {
        preorder(root);
    }

    public void inorder() {
        inorder(root);
    }

    public void postorder() {
        postorder(root);
    }

    protected void visit(BSTNode<T> p) {
        System.out.print(p.el + " ");
    }

    protected void inorder(BSTNode<T> p) {
        if (p != null) {
            inorder(p.left);
            visit(p);
            inorder(p.right);
        }
    }

    protected void preorder(BSTNode<T> p) {
        if (p != null) {
            visit(p);
            preorder(p.left);
            preorder(p.right);
        }
    }

    protected void postorder(BSTNode<T> p) {
        if (p != null) {
            postorder(p.left);
            postorder(p.right);
            visit(p);
        }
    }

    public void deleteByCopying(T el) {
        BSTNode<T> node, p = root, prev = null;
        while (p != null && !p.el.equals(el)) {  // find the node p
            prev = p;                           // with element el;
            if (el.compareTo(p.el) < 0)
                p = p.left;
            else p = p.right;
        }
        node = p;
        if (p != null && p.el.equals(el)) {
            if (node.right == null)             // node has no right child;
                node = node.left;
            else if (node.left == null)         // no left child for node;
                node = node.right;
            else {
                BSTNode<T> tmp = node.left;    // node has both children;
                BSTNode<T> previous = node;    // 1.
                while (tmp.right != null) {    // 2. find the rightmost
                    previous = tmp;            //    position in the
                    tmp = tmp.right;           //    left subtree of node;
                }
                node.el = tmp.el;              // 3. overwrite the reference
                //    to the element being deleted;
                if (previous == node)          // if node's left child's
                    previous.left = tmp.left; // right subtree is null;
                else previous.right = tmp.left; // 4.
            }
            if (p == root)
                root = node;
            else if (prev.left == p)
                prev.left = node;
            else prev.right = node;
        } else if (root != null)
            System.out.println("el " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }

    public void deleteByMerging(T el) {
        BSTNode<T> tmp, node, p = root, prev = null;
        while (p != null && !p.el.equals(el)) {  // find the node p
            prev = p;                           // with element el;
            if (el.compareTo(p.el) < 0)
                p = p.right;
            else p = p.left;
        }
        node = p;
        if (p != null && p.el.equals(el)) {
            if (node.right == null) // node has no right child: its left
                node = node.left;  // child (if any) is attached to its parent;
            else if (node.left == null) // node has no left child: its right
                node = node.right; // child is attached to its parent;
            else {                  // be ready for merging subtrees;
                tmp = node.left;   // 1. move left
                while (tmp.right != null) // 2. and then right as far as
                    tmp = tmp.right;      //    possible;
                tmp.right =        // 3. establish the link between
                        node.right;    //    the rightmost node of the left
                //    subtree and the right subtree;
                node = node.left;  // 4.
            }
            if (p == root)
                root = node;
            else if (prev.left == p)
                prev.left = node;
            else prev.right = node; // 5.
        } else if (root != null)
            System.out.println("el " + el + " is not in the tree");
        else System.out.println("the tree is empty");
    }

    public void iterativePreorder() {
        BSTNode<T> p = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>();
        if (p != null) {
            travStack.push(p);
            while (!travStack.isEmpty()) {
                p = travStack.pop();
                visit(p);
                if (p.right != null)
                    travStack.push(p.right);
                if (p.left != null)        // left child pushed after right
                    travStack.push(p.left);// to be on the top of the stack;
            }
        }
    }

    public void iterativeInorder() {
        BSTNode<T> p = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>();
        while (p != null) {
            while (p != null) {               // stack the right child (if any)
                if (p.right != null)        // and the node itself when going
                    travStack.push(p.right); // to the left;
                travStack.push(p);
                p = p.left;
            }
            p = travStack.pop();             // pop a node with no left child
            while (!travStack.isEmpty() && p.right == null) { // visit it and all
                visit(p);                   // nodes with no right child;
                p = travStack.pop();
            }
            visit(p);                        // visit also the first node with
            if (!travStack.isEmpty())        // a right child (if any);
                p = travStack.pop();
            else p = null;
        }
    }

    public void iterativePostorder2() {
        BSTNode<T> p = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>(),
                output = new Stack<BSTNode<T>>();
        if (p != null) {        // left-to-right postorder = right-to-left preorder;
            travStack.push(p);
            while (!travStack.isEmpty()) {
                p = travStack.pop();
                output.push(p);
                if (p.left != null)
                    travStack.push(p.left);
                if (p.right != null)
                    travStack.push(p.right);
            }
            while (!output.isEmpty()) {
                p = output.pop();
                visit(p);
            }
        }
    }

    public void iterativePostorder() {
        BSTNode<T> p = root, q = root;
        Stack<BSTNode<T>> travStack = new Stack<BSTNode<T>>();
        while (p != null) {
            for (; p.left != null; p = p.left)
                travStack.push(p);
            while (p != null && (p.right == null || p.right == q)) {
                visit(p);
                q = p;
                if (travStack.isEmpty())
                    return;
                p = travStack.pop();
            }
            travStack.push(p);
            p = p.right;
        }
    }

    public void breadthFirst() {
        BSTNode<T> p = root;
        Queue<BSTNode<T>> queue = new Queue<BSTNode<T>>();
        if (p != null) {
            queue.enqueue(p);
            while (!queue.isEmpty()) {
                p = queue.dequeue();
                visit(p);
                if (p.left != null)
                    queue.enqueue(p.left);
                if (p.right != null)
                    queue.enqueue(p.right);
            }
        }
    }

    public void MorrisInorder() {
        BSTNode<T> p = root, tmp;
        while (p != null)
            if (p.left == null) {
                visit(p);
                p = p.right;
            } else {
                tmp = p.left;
                while (tmp.right != null && // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                    tmp = tmp.right;   // to the temporary parent of p;
                if (tmp.right == null) {// if 'true' rightmost node was
                    tmp.right = p;     // reached, make it a temporary
                    p = p.left;        // parent of the current root,
                } else {                  // else a temporary parent has been
                    visit(p);          // found; visit node p and then cut
                    tmp.right = null;  // the right pointer of the current
                    p = p.right;       // parent, whereby it ceases to be
                }                       // a parent;
            }
    }

    public void MorrisPreorder() {
        BSTNode<T> p = root, tmp;
        while (p != null) {
            if (p.left == null) {
                visit(p);
                p = p.right;
            } else {
                tmp = p.left;
                while (tmp.right != null && // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                    tmp = tmp.right;   // to the temporary parent of p;
                if (tmp.right == null) {// if 'true' rightmost node was
                    visit(p);          // reached, visit the root and
                    tmp.right = p;     // make the rightmost node a temporary
                    p = p.left;        // parent of the current root,
                } else {                  // else a temporary parent has been
                    tmp.right = null;  // found; cut the right pointer of
                    p = p.right;       // the current parent, whereby it ceases
                }                       // to be a parent;
            }
        }
    }

    public void MorrisPostorder() {
        BSTNode<T> p = new BSTNode<T>(), tmp, q, r, s;
        p.left = root;
        while (p != null)
            if (p.left == null)
                p = p.right;
            else {
                tmp = p.left;
                while (tmp.right != null &&  // go to the rightmost node of
                        tmp.right != p)  // the left subtree or
                    tmp = tmp.right;   // to the temporary parent of p;
                if (tmp.right == null) {// if 'true' rightmost node was
                    tmp.right = p;     // reached, make it a temporary
                    p = p.left;        // parent of the current root,
                } else {           // else a temporary parent has been found;
                    // process nodes between p.left (included) and p (excluded)
                    // extended to the right in modified tree in reverse order;
                    // the first loop descends this chain of nodes and reverses
                    // right pointers; the second loop goes back, visits nodes,
                    // and reverses right pointers again to restore the pointers
                    // to their original setting;
                    for (q = p.left, r = q.right, s = r.right;
                         r != p; q = r, r = s, s = s.right)
                        r.right = q;
                    for (s = q.right; q != p.left;
                         q.right = r, r = q, q = s, s = s.right)
                        visit(q);
                    visit(p.left);     // visit node p.left and then cut
                    tmp.right = null;  // the right pointer of the current
                    p = p.right;       // parent, whereby it ceases to be
                }                       // a parent;
            }
    }

    public void balance(T data[], int first, int last) {
        if (first <= last) {
            int middle = (first + last) / 2;
            insert(data[middle]);
            balance(data, first, middle - 1);
            balance(data, middle + 1, last);
        }
    }

    public void balance(T data[]) {
        balance(data, 0, data.length - 1);
    }

    public int count() {
        int count = 0;
        if (root == null)
            return count;
        else
            return 1 + count(root.left) + count(root.left);
    }

    public int count(BSTNode<T> Node) {
        if (Node != null) {
            if (Node.right == null && Node.left == null)
                return 1;
            if (Node.right != null)
                return 1 + count(Node.right);
            else
                return 1 + count(Node.left);
        }
        return 0;
    }

    public boolean isLeaf(T value) {
        BSTNode<T> p = root;
        while (p != null)
            if (value.equals(p.el))
                return p.left == null && p.right == null;
            else if (value.compareTo(p.el) < 0)
                p = p.left;
            else p = p.right;
        return false;
    }

    public int countLeaves() {
        return countLeaves(root);
    }

    private int countLeaves(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }

    public int getHeight() {
            return getHeight(root);
    }

    public int getHeight(BSTNode<T> node) {
        if (node == null) {
            return 0;
        }

        int left = getHeight(node.left);
        int right = getHeight(node.right);

        int height;
        if (left > right) {
            height = left;
        } else {
            height = right;
        }

        return 1 + height;
    }




    public static class Queue<T> {
        private java.util.LinkedList<T> list = new java.util.LinkedList<T>();
        public Queue() {
        }
        public void clear() {
            list.clear();
        }
        public boolean isEmpty() {
            return list.isEmpty();
        }
        public T firstEl() {
            return list.getFirst();
        }
        public T dequeue() {
            return list.removeFirst();
        }
        public void enqueue(T el) {
            list.addLast(el);
        }
        public String toString() {
            return list.toString();
        }
    }
    public static class Stack<T> {
        private java.util.ArrayList<T> pool = new java.util.ArrayList<T>();
        public Stack() {
        }
        public Stack(int n) {
            pool.ensureCapacity(n);
        }
        public void clear() {
            pool.clear();
        }
        public boolean isEmpty() {
            return pool.isEmpty();
        }
        public T topEl() {
            if (isEmpty())
                throw new java.util.EmptyStackException();
            return pool.get(pool.size()-1);
        }
        public T pop() {
            if (isEmpty())
                throw new java.util.EmptyStackException();
            return pool.remove(pool.size()-1);
        }
        public void push(T el) {
            pool.add(el);
        }
        public String toString() {
            return pool.toString();
        }
    }
}
