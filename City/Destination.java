package City;

import java.util.Comparator;

public class Destination extends City {
    private int cost;
    private int time;

    // Constructors - Do not allow a Destination to be implemented without the name by which it is
    // compared.
    public Destination(String name) {
        super(name);
    }

    public Destination(String name, int cost, int time) {
        super(name);
        this.cost = cost;
        this.time = time;
    }

    public Destination(Destination destination) {
        super(destination.name);
        cost = destination.cost;
        time = destination.time;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public int getTime() {
        return time;
    }

    public static class DestinationComparator implements Comparator<Destination> {
        public int compare(Destination destination1, Destination destination2) {
            return destination1.name.compareTo(destination2.name);
        }
    }
}
