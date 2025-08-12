import City.Source;
import City.Destination;
import GenericDataStructures.LinkedList;
import GenericDataStructures.OrderedLinkedList;
import GenericDataStructures.Stack;
import GenericDataStructures.Heap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.FileWriter;
import java.io.IOException;

// Although we hope the professor's input file formats will not be broken by the user, rudimentary
// exception handling is still present to avoid program failure.
public class AdjacencyList {
    private OrderedLinkedList<Source> list;
    private Stack<StackPair> stack;
    private Heap<FlightPath> heap;

    public AdjacencyList() {
        list = new OrderedLinkedList<>(new Source.SourceComparator());
        stack = new Stack<>(new StackPair.StackPairComparator());
        heap = new Heap<>(new FlightPath.FlightPathComparator());
    }

    public AdjacencyList(AdjacencyList adjacencyList) {
        list = new OrderedLinkedList<>(adjacencyList.list);
        stack = new Stack<>(adjacencyList.stack);
        heap = new Heap<>(adjacencyList.heap);
    }

    public void readFlightData(String filepath) {
        // Reset adjacency list for a fresh read-in. Without this line, program would simply add
        // non-duplicate flight data without error on subsequent calls.
        list.reset();

        System.out.println("Reading flight data from " + filepath + " . . .\n");

        try {
            // Seek input file at filepath, and prepare to tokenize and read.
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            // | and \ are special characters in regex and require escape. | provides OR
            // functionality. This statement says to accept |, \n, and \r\n as delimiters.
            scanner.useDelimiter("\\||\n|\r\n");

            // Read number of flight data entries to set the loop counter.
            int count = Integer.parseInt(scanner.next());

            String city1, city2;
            int cost, time;
            Source source1, source2;
            Destination destination1, destination2;

            for (int i = 0; i < count; i++) {
                // For each entry, build two Source/Destination pairs, one for each direction, and
                // insert them into the adjacency list to build the graph.
                city1 = scanner.next();
                city2 = scanner.next();
                cost = Integer.parseInt(scanner.next());
                time = Integer.parseInt(scanner.next());

                source1 = new Source(city1);
                destination1 = new Destination(city2, cost, time);
                source1.addAdjacency(destination1);
                insertFlightData(source1);

                source2 = new Source(city2);
                destination2 = new Destination(city1, cost, time);
                source2.addAdjacency(destination2);
                insertFlightData(source2);
            }

            scanner.close();
        } catch (NullPointerException exception) {
            System.out.println("Invalid filepath.\n");
        } catch (FileNotFoundException exception) {
            System.out.println("File not found.\n");
        } catch (NoSuchElementException exception) {
            System.out.println("Invalid input quantity.\n");
        } catch (NumberFormatException exception) {
            System.out.println("Invalid input type.\n");
        }
    }

    private void insertFlightData(Source source) {
        // Search adjacency list for Source.
        LinkedList<Source>.LinkedListIterator listIterator = list.find(source);

        // Unlike iterator(), find() can return null.
        if (listIterator != null && listIterator.hasNext()) {
            // Source already exists in adjacency list. Just insert Destination.
            LinkedList<Destination>.LinkedListIterator adjacencyIterator = source.iterator();

            if (adjacencyIterator.hasNext()) {
                listIterator.next().addAdjacency(adjacencyIterator.next());
            }
        } else {
            // Source does not yet exist in adjacency list. Insert Source/Destination pair as a new
            // row.
            list.insert(source);
        }
    }

    public void processRequests(String inputFilePath, String outputFilePath) {
        System.out.println("Reading flight requests from " + inputFilePath + " . . .\n");

        try {
            // Seek input file at filepath, and prepare to tokenize and read.
            File inputFile = new File(inputFilePath);
            Scanner scanner = new Scanner(inputFile);
            // | and \ are special characters in regex and require escape. | provides OR
            // functionality. This statement says to accept |, \n, and \r\n as delimiters.
            scanner.useDelimiter("\\||\n|\r\n");

            // Read number of flight request entries to set the loop counter.
            int count = Integer.parseInt(scanner.next());

            String city1, city2, comparisonAttribute;
            Source source;
            Destination destination;
            FlightPath.FlightPathComparator flightPathComparator;

            // Open output file for writing.
            File outputFile = new File(outputFilePath);
            FileWriter fileWriter = new FileWriter(outputFile);

            for (int i = 0; i < count; i++) {
                // For each entry, build a Source/Destination pair and pass it with comparison rules
                // to the iterative backtracking and output method.
                city1 = scanner.next();
                city2 = scanner.next();
                comparisonAttribute = scanner.next();
                source = new Source(city1);
                destination = new Destination(city2);

                if (comparisonAttribute.equals("T")) {
                    flightPathComparator = new FlightPath.FlightPathComparator(
                        FlightPath.FlightPathComparator.ComparisonAttribute.TIME
                    );
                } else {
                    flightPathComparator = new FlightPath.FlightPathComparator(
                        FlightPath.FlightPathComparator.ComparisonAttribute.COST
                    );
                }

                iterativeBacktracking(source, destination, flightPathComparator, fileWriter);
            }

            System.out.println("Writing flight plans to " + outputFilePath + " . . .\n");

            scanner.close();
            // This method is more than a formality. It flushes all buffered writes to the OS. This
            // is the file output.
            fileWriter.close();
        } catch (NullPointerException exception) {
            System.out.println("Invalid filepath.\n");
        } catch (FileNotFoundException exception) {
            System.out.println("File not found.\n");
        } catch (NoSuchElementException exception) {
            System.out.println("Invalid input quantity.\n");
        } catch (NumberFormatException exception) {
            System.out.println("Invalid input type.\n");
        } catch (IOException exception) {
            System.out.println("Unable to write to file.\n");
        }
    }

    private void iterativeBacktracking(
        Source source,
        Destination destination,
        FlightPath.FlightPathComparator flightPathComparator,
        FileWriter fileWriter
    ) throws IOException {
        // Reset the heap and let it know how to compare the upcoming set of flight paths.
        heap.setComparator(flightPathComparator);

        fileWriter.write("Flights from " + source.getName() + " to " + destination.getName());

        if (flightPathComparator.getCompareBy() 
            == FlightPath.FlightPathComparator.ComparisonAttribute.COST) {
            fileWriter.write(" (Cheapest):\n\n");
        } else {
            fileWriter.write(" (Fastest):\n\n");
        }

        // Find the input source in the adjacency list.
        LinkedList<Source>.LinkedListIterator rowIterator = list.find(source);

        if (rowIterator == null) {
            // Input source not present.
            fileWriter.write("No flights available departing from " + source.getName() + ".\n\n");
        } else if (list.find(new Source(destination.getName())) == null) {
            // Input destination not present.
            fileWriter.write("No flights available arriving to " + destination.getName() + ".\n\n");
        } else if (rowIterator.hasNext()) {
            // The input source is available as a starting point. Add it to the stack.
            stack.push(new StackPair(new Destination(source.getName()), null));

            // This iterator will push to and pop from the stack to guide us through a depth-first
            // search of the adjacency list.
            LinkedList<Destination>.LinkedListIterator controller = rowIterator.next().iterator();

            Destination current;
            LinkedList<StackPair>.LinkedListIterator stackIterator;
            FlightPath flightPath;

            while (controller != null) {
                // Proceed until depth-first search terminates after controller pops null iterator
                // from stack.

                current = controller.next();

                if (current.getName().compareTo(destination.getName()) == 0) {
                    // Next jump will take us to our destination. We can build the FlightPath now
                    // without changing rows.
                    stack.push(new StackPair(current, controller));
                    flightPath = new FlightPath();
                    stackIterator = stack.iterator();

                    while (stackIterator.hasNext()) {
                        flightPath.add(stackIterator.next().getDestination());
                    }

                    stack.pop();    // No need to store. We only pushed to ease the above operation.
                                    // Controller did not change.
                    heap.insert(flightPath);
                } else if (stack.find(new StackPair(current)) == null) {
                    // Store current position, and proceed to a new row.
                    stack.push(new StackPair(current, controller));
                    rowIterator = list.find(new Source(current.getName()));
                    controller = rowIterator.next().iterator();
                }

                while (controller != null && !controller.hasNext()) {
                    // End of row. Return to previous row until a new element is available or DFS
                    // complete.
                    controller = stack.pop().getIterator();
                }
            }

            // All paths from source to destination have been located and inserted to the heap.
            LinkedList<Destination>.LinkedListIterator flightPathIterator;
            int weight, total;

            for (int i = 0; i < 3 && heap.peek() != null; i++) {
                // Remove and output the top 3 paths.
                flightPath = heap.remove();
                flightPathIterator = flightPath.iterator();

                while (flightPathIterator.hasNext()) {
                    destination = flightPathIterator.next();

                    fileWriter.write(destination.getName());

                    if (flightPathComparator.getCompareBy()
                        == FlightPath.FlightPathComparator.ComparisonAttribute.COST) {
                        weight = destination.getCost();
                    } else {
                        weight = destination.getTime();
                    }

                    if (weight != 0) {
                        fileWriter.write(" (" + weight + ")");
                    }

                    if (flightPathIterator.hasNext()) {
                        fileWriter.write(" -> ");
                    }
                }

                if (flightPathComparator.getCompareBy()
                    == FlightPath.FlightPathComparator.ComparisonAttribute.COST) {
                    total = flightPath.getCost();
                } else {
                    total = flightPath.getTime();
                }

                fileWriter.write("\nTotal: " + total + "\n\n");
            }
        }
    }

    public void print() {
        LinkedList<Source>.LinkedListIterator listIterator = list.iterator();

        System.out.println("Adjacency List:\n");

        while (listIterator.hasNext()) {
            Source source = listIterator.next();
            System.out.println(source.getName() + " Adjacencies:");

            LinkedList<Destination>.LinkedListIterator adjacencyIterator = source.iterator();

            while (adjacencyIterator.hasNext()) {
                Destination destination = adjacencyIterator.next();
                System.out.println(destination.getName() + " - " + destination.getCost() + " Cost, "
                    + destination.getTime() + " Time");
            }

            System.out.println();
        }
    }
}
