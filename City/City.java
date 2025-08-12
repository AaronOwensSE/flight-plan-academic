package City;

public abstract class City {
    protected String name;

    public City(String name) {
        this.name = name;
    }

    public City(City city) {
        name = city.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
