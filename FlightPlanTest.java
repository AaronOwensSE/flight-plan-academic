public class FlightPlanTest {
    public static void main(String[] args) {
        System.out.println("FLIGHT PLAN TEST PROGRAM");

        AdjacencyList adjacencyList = new AdjacencyList();

        String flightDataFile, flightRequestsFile, flightPlansFile;

        if (args.length >= 3) {
            flightDataFile = args[0];
            flightRequestsFile = args[1];
            flightPlansFile = args[2];
        } else {
            flightDataFile = "./sample_files/FlightData.txt";
            flightRequestsFile = "./sample_files/FlightRequests.txt";
            flightPlansFile = "./sample_files/FlightPlans.txt";
        }

        adjacencyList.readFlightData(flightDataFile);
        adjacencyList.print();
        adjacencyList.processRequests(flightRequestsFile, flightPlansFile);

        System.out.println("Exiting program . . .");
    }
}
