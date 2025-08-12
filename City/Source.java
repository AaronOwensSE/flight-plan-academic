package City;

import GenericDataStructures.LinkedList;
import GenericDataStructures.OrderedLinkedList;

import java.util.Comparator;

public class Source extends City implements Iterable<Destination> {
    private OrderedLinkedList<Destination> adjacencies;

    // Constructors - Do not allow a Source to be implemented without the name by which it is
    // compared.
    public Source(String name) {
        super(name);
        adjacencies = new OrderedLinkedList<>(new Destination.DestinationComparator());
    }

    public Source(String name, OrderedLinkedList<Destination> adjacencies) {
        super(name);
        this.adjacencies = new OrderedLinkedList<>(adjacencies);
    }

    public Source(Source source) {
        super(source.name);
        adjacencies = new OrderedLinkedList<>(source.adjacencies);
    }

    public void addAdjacency(Destination destination) {
        adjacencies.insert(destination);
    }

    public LinkedList<Destination>.LinkedListIterator iterator() {
        return adjacencies.iterator();
    }

    public static class SourceComparator implements Comparator<Source> {
        public int compare(Source source1, Source source2) {
            return source1.name.compareTo(source2.name);
        }
    }
}
