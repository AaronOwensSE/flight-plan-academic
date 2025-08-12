import City.Destination;
import GenericDataStructures.LinkedList;
import GenericDataStructures.Stack;

import java.util.Comparator;

public class FlightPath implements Iterable<Destination> {
    private Stack<Destination> stack;
    private int cost;
    private int time;

    // Constructors - Do not allow a FlightPath to be implemented without the cost and time by which
    // it is compared.
    public FlightPath() {
        stack = new Stack<>(new Destination.DestinationComparator());
    }

    public FlightPath(FlightPath flightPath) {
        stack = new Stack<>(flightPath.stack);
        cost = flightPath.cost;
        time = flightPath.time;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public void add(Destination destination) {
        stack.push(destination);
        cost += destination.getCost();
        time += destination.getTime();
    }

    public void reset() {
        stack.reset();
        cost = time = 0;
    }

    public LinkedList<Destination>.LinkedListIterator iterator() {
        return stack.iterator();
    }

    public static class FlightPathComparator implements Comparator<FlightPath> {
        public enum ComparisonAttribute { COST, TIME }

        private ComparisonAttribute compareBy;

        // Constructors - Do not allow a FlightPathComparator to be implemented with its compareBy
        // set to null.
        public FlightPathComparator() {
            compareBy = ComparisonAttribute.COST;
        }

        public FlightPathComparator(ComparisonAttribute comparisonAttribute) {
            compareBy = comparisonAttribute;
        }

        public FlightPathComparator(FlightPathComparator flightPathComparator) {
            compareBy = flightPathComparator.compareBy;
        }

        // We do not implement a setCompareBy because we do not want to change the way a data
        // structure using this comparator compares without resetting that data structure.

        public ComparisonAttribute getCompareBy() {
            return compareBy;
        }

        public int compare(FlightPath flightPath1, FlightPath flightPath2) {
            int cmp = 0;

            switch (compareBy) {
                case COST:
                    cmp = flightPath1.cost - flightPath2.cost;
                    break;
                case TIME:
                    cmp = flightPath1.time - flightPath2.time;
                    break;
            }

            return cmp;
        }
    }
}
