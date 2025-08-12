package GenericDataStructures;

import java.util.Comparator;

public class OrderedLinkedList<E> extends LinkedList<E> {
    // Constructors - Do not allow an OrderedLinkedList to initialize without a Comparator.
    public OrderedLinkedList(Comparator<E> comparator) {
        super(comparator);
    }

    public OrderedLinkedList(OrderedLinkedList<E> orderedLinkedList) {
        super(orderedLinkedList);
    }

    // This insert is designed to discard duplicates.
    public void insert(E element) {
        if (head == null) {
            // Empty list. Insert as head and tail.
            head = tail = new Node(element);
            length++;
        } else if (comparator.compare(head.element, element) > 0) {
            // Head is greater than element. Insert at head.
            Node node = new Node(element);
            node.next = head;
            head = node;
            length++;
        } else if (comparator.compare(head.element, element) < 0) {
            // Head is less than element.
            // We have deliberately not written a control path for when head is equal to element.
            // The only duplicates possible now are ahead in the list.
            Node current = head;

            while (current.next != null && comparator.compare(current.next.element, element) < 0) {
                // Traverse to insertion point or end of list.
                current = current.next;
            }

            if (current.next == null) {
                // End of list. Insert at tail.
                tail = current.next = new Node(element);
                length++;
            } else if (comparator.compare(current.next.element, element) > 0) {
                // Mid-list insertion point (or next is equal to element).
                // Insertion only occurs when next is greater than element, eliminating the last
                // possibility of a duplicate.
                Node node = new Node(element);
                node.next = current.next;
                current.next = node;
                length++;
            }
        }
    }
}
