package GenericDataStructures;

import java.util.Comparator;

public class UnorderedLinkedList<E> extends LinkedList<E> {
    // Constructors - Do not allow an UnorderedLinkedList to initialize without a Comparator.
    public UnorderedLinkedList(Comparator<E> comparator) {
        super(comparator);
    }

    public UnorderedLinkedList(UnorderedLinkedList<E> unorderedLinkedList) {
        super(unorderedLinkedList);
    }

    public void insertAtHead(E element) {
        Node node = new Node(element);

        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head = node;
        }

        length++;
    }

    public void insertAtTail(E element) {
        Node node = new Node(element);

        if (head == null) {
            head = tail = node;
        } else {
            tail = tail.next = node;
        }

        length++;
    }
}
