package GenericDataStructures;

import java.util.Comparator;

// An array-based heap would be ideal, but I wanted the experience of writing the tree version. For
// its role in this project, the difference is negligible.
public class Heap<E> {
    private Node root;
    private Node parentOfNext;
    private int size;
    private Comparator<E> comparator;

    // Constructors - Do not allow a Heap to initialize without a Comparator.
    public Heap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    // Package-friendly constructor. Not used in this project.
    public Heap(LinkedList<E> linkedList) {
        comparator = linkedList.getComparator();
        LinkedList<E>.LinkedListIterator iterator = linkedList.iterator();

        while (iterator.hasNext()) {
            insert(iterator.next());
        }
    }

    // Would be faster as a parallel traversal algorithm. Rewrite if time.
    public Heap(Heap<E> heap) {
        comparator = heap.comparator;

        while (heap.root != null) {
            insert(heap.remove());
        }
    }

    public void setComparator(Comparator<E> comparator) {
        reset();
        this.comparator = comparator;
    }

    public int getSize() {
        return size;
    }

    public Comparator<E> getComparator() {
        return comparator;
    }

    // Would a recursive algorithm be simpler?
    public void insert(E element) {
        if (root == null) {
            // Tree is empty. Simply add new element. No need to sift with one element.
            root = new Node(element);
            parentOfNext = root;
        } else if (parentOfNext.left == null) {
            // Left element is null. Pointer need not move. Simply add to left and sift up.
            parentOfNext.left = new Node(element);
            parentOfNext.left.parent = parentOfNext;

            siftUp(parentOfNext.left);
        } else {
            // Left element is filled. Therefore, add to right and sift up, then move pointer.
            parentOfNext.right = new Node(element);
            parentOfNext.right.parent = parentOfNext;

            siftUp(parentOfNext.right);

            while (parentOfNext.parent != null) {
                // Go toward root.
                if (parentOfNext == parentOfNext.parent.left) {
                    // Until right movement is possible.
                    parentOfNext = parentOfNext.parent.right;

                    while (parentOfNext.left != null) {
                        // Then go full left.
                        parentOfNext = parentOfNext.left;
                    }

                    break;  // And break.
                } else {
                    parentOfNext = parentOfNext.parent;
                }
            }

            if (parentOfNext == root) {
                // Reached root without ever being able to move right.
                while (parentOfNext.left != null) {
                    // Go full left.
                    parentOfNext = parentOfNext.left;
                }
            }
        }

        size++;
    }

    public E peek() {
        if (root != null) {
            return root.element;
        } else {
            return null;
        }
    }

    public E remove() {
        if (root == null) {
            // Tree is empty. Nothing to remove.
            return null;
        } else if (parentOfNext == root) {
            // Tree consists of no more than two elements.
            E element;

            if (parentOfNext.left == null) {
                // Tree consists of root only. Remove root and null all pointers. No sifting
                // required on zero elements.
                element = parentOfNext.element;
                root = parentOfNext = null;
            } else {
                // Tree consists of root and left. Remove root and move left to root. No sifting
                // required on one element.
                element = root.element;
                root.element = root.left.element;
                root.left = null;
            }

            size--;

            return element;
        } else if (parentOfNext.left != null) {
            // Tree consists of three or more elements.
            // Left element is present, so pointer need not move. Remove root, swap left to root,
            // and sift down.
            E element = root.element;
            root.element = parentOfNext.left.element;
            parentOfNext.left = null;
            size--;
            siftDown(root);

            return element;
        } else {
            // Left element is not present, so pointer must return to parent of last inserted
            // element.
            while (parentOfNext.parent != null) {
                // Go toward root.
                if (parentOfNext == parentOfNext.parent.right) {
                    // Until left movement is possible.
                    parentOfNext = parentOfNext.parent.left;

                    while (parentOfNext.right.right != null) {
                        // Then go full right.
                        parentOfNext = parentOfNext.right;
                    }

                    break; // And break.
                } else {
                    parentOfNext = parentOfNext.parent;
                }
            }

            if (parentOfNext == root) {
                // Reached root without ever being able to move left.
                while (parentOfNext.right.right != null) {
                    // Go full right.
                    parentOfNext = parentOfNext.right;
                }
            }

            // Remove root, swap right to root, and sift down.
            E element = root.element;
            root.element = parentOfNext.right.element;
            parentOfNext.right = null;
            size--;
            siftDown(root);

            return element;
        }
    }

    private void siftUp(Node node) {
        if (node.parent != null) {
            if (comparator.compare(node.element, node.parent.element) < 0) {
                E swap = node.element;
                node.element = node.parent.element;
                node.parent.element = swap;

                siftUp(node.parent);
            }
        }
    }

    private void siftDown(Node node) {
        if (node.left != null) {
            if (node.right != null) {
                Node cmp;

                if (comparator.compare(node.left.element, node.right.element) < 0) {
                    cmp = node.left;
                } else {
                    cmp = node.right;
                }

                if (comparator.compare(node.element, cmp.element) > 0) {
                    E swap = node.element;
                    node.element = cmp.element;
                    cmp.element = swap;

                    siftDown(cmp);
                }
            } else {
                if (comparator.compare(node.element, node.left.element) > 0) {
                    E swap = node.element;
                    node.element = node.left.element;
                    node.left.element = swap;

                    siftDown(node.left);
                }
            }
        }
    }

    public void reset() {
        root = parentOfNext = null;
        size = 0;
    }

    private class Node {
        private E element;
        private Node parent;
        private Node left;
        private Node right;

        public Node(E element) {
            this.element = element;
        }

        public Node(E element, Node parent, Node left, Node right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public Node(Node node) {
            element = node.element;
            parent = node.parent;
            left = node.left;
            right = node.right;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public E getElement() {
            return element;
        }

        public Node getParent() {
            return parent;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
    }
}
