import City.Destination;
import GenericDataStructures.LinkedList;

import java.util.Comparator;

public class StackPair {
    private Destination destination;
    private LinkedList<Destination>.LinkedListIterator iterator;

    // Constructors - Do not allow a StackPair to be implemented without the Destination by which it
    // is compared.
    public StackPair(Destination destination) {
        this.destination = new Destination(destination);
    }

    public StackPair(Destination destination, LinkedList<Destination>.LinkedListIterator iterator) {
        this.destination = new Destination(destination);
        this.iterator = iterator;
    }

    public StackPair(StackPair stackPair) {
        destination = new Destination(stackPair.destination);
        iterator = stackPair.iterator;
    }

    public void setDestination(Destination destination) {
        this.destination = new Destination(destination);
    }

    public void setIterator(LinkedList<Destination>.LinkedListIterator iterator) {
        this.iterator = iterator;
    }

    public Destination getDestination() {
        return new Destination(destination);
    }

    public LinkedList<Destination>.LinkedListIterator getIterator() {
        return iterator;
    }

    public static class StackPairComparator implements Comparator<StackPair> {
        public int compare(StackPair stackPair1, StackPair stackPair2) {
            return stackPair1.destination.getName().compareTo(stackPair2.destination.getName());
        }
    }
}
