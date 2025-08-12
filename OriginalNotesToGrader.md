# Original Notes to Grader

My project consists of 11 class files, some of which are enclosed in one of 2 packages, and 1 test file. I have also included 6 example data files which can be used to run the program, although any files specified in the command line by the user should work, provided the formats established in the project assignment sheet are followed.

## City Package

Contains classes which represent graph vertices and edges in a weighted graph of cities to be considered in flight planning.

**City (abstract)** - Has a name and establishes basic class functionality to be inherited.

**Destination** - Inherits City and carries the cost and time of traveling to that Destination (to be attached to a Source). Implements a Comparator with rules for comparing Destinations.

**Source** - Inherits City and contains an OrderedLinkedList of Destinations representing the Source's adjacencies, each with their associated cost and time weights. Iterable. Implements a Comparator with rules for comparing Sources.

## GenericDataStructures Package

Contains generic implementations of data structures needed by the project. For educational benefit, I chose to write these myself.

**LinkedList (abstract)** - Singly-linked with head and tail pointers, length, and generic Comparator. Iterable. Establishes basic linked list functionality to be inherited, including peek and removal at head or tail, copy, reset, find, and Iterator.

**UnorderedLinkedList** - Inherits LinkedList. Adds insertion and removal at head or tail.

**OrderedLinkedList** - Inherits LinkedList. Adds an ordered insert method. For the purposes of this project, the insert was designed to not accept duplicates.

**Stack** - Wrapper class for an UnorderedLinkedList. Iterable. Encloses UnorderedLinkedList functionality to behave as a stack. Push, pop, and peek are implemented using head operations.

**Heap** - A tree-based min heap with size and generic Comparator. Implements insert, peek, remove, and reset functionality.

## Additional Classes

**FlightPath** - Contains a Stack of Destinations with aggregate cost and time. Iterable. Used to assemble complete flight paths from input data. Implements a Comparator with rules for comparing FlightPaths.

**StackPair** - Contains a Destination and Iterator for a LinkedList of Destinations. These objects are pushed to the Stack contained in the AdjacencyList to aid in iterative backtracking. Implements a Comparator with rules for comparing StackPairs.

**AdjacencyList** - Contains an OrderedLinkedList of Sources to represent the adjacency list assembled from input data, a Stack of StackPairs to aid in iterative backtracking, and a Heap of FlightPaths to aid in obtaining the most efficient flight paths calculated by the algorithm. Implements I/O functionality related to the adjacency list and iterative backtracking algorithm.

**FlightPlanTest** - Test file containing main method.

## Comparator Approach

Implementing the Comparable interface through generic inheritance structures proved difficult, so I chose to instead rely upon Comparator objects. These are defined in classes which find themselves being inserted as elements in larger structures and establish the rules for how these classes should be compared to each other. They are required to be supplied to the larger structures upon initialization.

## Iterator Approach

Implementing the Iterable interface on data structures proved to be a convenient way to traverse them from outside without breaking data encapsulation. Iterators themselves ended up being the ideal objects to store on the iterative backtracking stack and to guide the iterative backtracking algorithm.

## Command Line

Command line functionality per the project assignment sheet is present, but if no command line arguments are entered, the program will default to one set of my included test files.

---

[Back to README.md](./README.md)
