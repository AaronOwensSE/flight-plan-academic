package GenericDataStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public abstract class LinkedList<E> implements Iterable<E> {
    protected Node head;
    protected Node tail;
    protected int length;
    protected Comparator<E> comparator;

    // Constructors - Do not allow a LinkedList to initialize without a Comparator.
    public LinkedList(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public LinkedList(LinkedList<E> linkedList) {
        copy(linkedList);
        comparator = linkedList.comparator;
    }

    public void setComparator(Comparator<E> comparator) {
        reset();
        this.comparator = comparator;
    }

    public int getLength() {
        return length;
    }

    public Comparator<E> getComparator() {
        return comparator;
    }

    public E peekHead() {
        if (head != null) {
            return head.element;
        } else {
            return null;
        }
    }

    public E peekTail() {
        if (tail != null) {
            return tail.element;
        } else {
            return null;
        }
    }

    public E removeFromHead() {
        if (head != null) {
            E element = head.element;

            if (head == tail) {
                head = tail = null;
            } else {
                head = head.next;
            }

            length--;

            return element;
        } else {
            return null;
        }
    }

    public E removeFromTail() {
        if (tail != null) {
            E element = tail.element;

            if (head == tail) {
                head = tail = null;
            } else {
                Node current = head;

                while (current.next != tail) {
                    current = current.next;
                }

                current.next = null;
                tail = current;
            }

            length--;

            return element;
        } else {
            return null;
        }
    }

    public LinkedListIterator find(E element) {
        Node current = head;

        while (current != null) {
            if (comparator.compare(current.element, element) == 0) {
                return new LinkedListIterator(current);
            } else {
                current = current.next;
            }
        }

        return null;
    }

    public void copy(LinkedList<E> linkedList) {
        Node paramCurrent = linkedList.head;

        if (paramCurrent != null) {
            head = new Node(paramCurrent.element);

            Node thisCurrent = head;

            while (paramCurrent.next != null) {
                thisCurrent.next = new Node(paramCurrent.next.element);

                paramCurrent = paramCurrent.next;
                thisCurrent = thisCurrent.next;
            }

            tail = thisCurrent;
        } else {
            head = tail = null;
        }

        length = linkedList.length;
    }

    public void reset() {
        head = tail = null;
        length = 0;
    }

    public LinkedListIterator iterator() {
        return new LinkedListIterator(head);
    }

    protected class Node {
        protected E element;
        protected Node next;

        public Node(E element) {
            this.element = element;
        }

        public Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }

        public Node(Node node) {
            element = node.element;
            next = node.next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public Node getNext() {
            return next;
        }
    }

    public class LinkedListIterator implements Iterator<E> {
        private Node current;

        public LinkedListIterator() {
            current = head;
        }

        public LinkedListIterator(Node node) {
            current = node;
        }

        public LinkedListIterator(LinkedListIterator linkedListIterator) {
            current = linkedListIterator.current;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() throws NoSuchElementException {
            if (current != null) {
                E element = current.element;
                current = current.next;

                return element;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
