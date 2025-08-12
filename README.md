# flight-plan-academic

An academic project that accepts a set of flight data and a set of flight requests, then determines the fastest or cheapest flight plans available using an iterative backtracking algorithm.

This project was part of a data structures and algorithms class completed in the fall of 2022. Written in Java.

## Input File Structure

### Flight Data File

- The first line of a flight data file contains an integer indicating how many rows of data are in the file.
- Subsequent lines each represent a possible flight leg, containing a pipe-separated list of two city names, the cost of a flight between them, and the duration of a flight between them.

```
4
Winterfell|Oz|96|49
Oz|Rohan|93|42
Winterfell|Rohan|105|56
Oz|Chicago|149|198
```

### Flight Request File

- The first line of a flight request file contains an integer indicating how many rows of data are in the file.
- Subsequent lines each represent a requested flight, containing a pipe-separated list of the origin city, the destination city, and either a 'T' or 'C' to indicate whether to calculate by time or cost, respectively.

```
2
Winterfell|Rohan|T
Chicago|Winterfell|C
```

## Output File

Flight plans are written to the output file specified by the third command-line argument to the program.

## Compile

From the project root directory:

```
javac FlightPlanTest.java
```

## Run

From the project root directory:

```
java FlightPlanTest ./sample_files/FlightData.txt ./sample_files/FlightRequests.txt ./sample_files/FlightPlans.txt
```

Or substitute your own files using the formats detailed above.

## [Original Notes to Grader](./OriginalNotesToGrader.md)

## Disclaimer

As an academic exercise, this project's scope is limited to a highly specific set of requirements elicited from assignment documentation and classroom discussions. It is presented as a successful fulfillment of learning objectives and a snapshot of my development over time as a programmer.
