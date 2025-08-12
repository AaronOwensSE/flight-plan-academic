package GenericDataStructures;

import java.util.Comparator;

public class Stack<E> implements Iterable<E> {
    private UnorderedLinkedList<E> linkedList;

    // Constructors - Do not allow a Stack to initialize without a Comparator to pass to its
    // UnorderedLinkedList.
    public Stack(UnorderedLinkedList<E> unorderedLinkedList) {
        linkedList = new UnorderedLinkedList<>(unorderedLinkedList);
    }

    public Stack(Comparator<E> comparator) {
        linkedList = new UnorderedLinkedList<>(comparator);
    }

    public Stack(Stack<E> stack) {
        linkedList = new UnorderedLinkedList<>(stack.linkedList);
    }

    public void setComparator(Comparator<E> comparator) {
        linkedList.setComparator(comparator);
    }

    public int getSize() {
        return linkedList.getLength();
    }

    public Comparator<E> getComparator() {
        return linkedList.getComparator();
    }

    public void push(E element) {
        linkedList.insertAtHead(element);
    }

    public E pop() {
        return linkedList.removeFromHead();
    }

    public E peek() {
        return linkedList.peekHead();
    }

    public LinkedList<E>.LinkedListIterator find(E element) {
        return linkedList.find(element);
    }

    public void reset() {
        linkedList.reset();
    }

    public LinkedList<E>.LinkedListIterator iterator() {
        return linkedList.iterator();
    }
}
