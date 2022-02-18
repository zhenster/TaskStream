package se.edu.inclass;

import se.edu.inclass.data.DataManager;
import se.edu.inclass.task.Deadline;
import se.edu.inclass.task.Task;
import se.edu.inclass.task.TaskNameComparator;

import java.util.ArrayList;

public class Main {

    private TaskNameComparator taskNameComparator;

    public static void main(String[] args) {
        DataManager dm = new DataManager("./data/data.txt"); // Class is being run from this particular file location
        ArrayList<Task> tasksData = dm.loadData();

//        printData(tasksData);
//        System.out.println("\nPrinting deadlines");
//        printDeadlines(tasksData);
        printDataWithStreams(tasksData); // Does the same as above, but with a Stream representation
        printDeadlinesWithStream(tasksData);
        System.out.println("\nTotal number of deadlines: " + countDeadlines(tasksData));
        System.out.println("Total number of deadlines (using stream) : " +
                countDeadlinesWithStream(tasksData));

    }

    private static int countDeadlines(ArrayList<Task> tasksData) {
        int count = 0;
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                count++;
            }
        }
        return count;
    }

    private static int countDeadlinesWithStream(ArrayList<Task> tasks) {
        int count = 0;
        // Must cast to int, or else it will complain that the result is a long
        count = (int) tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .count();
        return count;
    }

    public static void printData(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            System.out.println(t);
            // System.object.method call
        }
    }

    public static void printDataWithStreams(ArrayList<Task> tasks) {
        System.out.println("\nPrint tasks using streams");
        tasks.stream() // Converts task data to a stream - parallelStream() allows for parallel computations - parallel iterations (that's why streams are useful for large amounts of data)
                .forEach(System.out::println); // terminal operation - System.object., but not a method name
                // :: vs : 
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        ArrayList<Task> deadlineList = new ArrayList<>();
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                deadlineList.add(t);
                System.out.println(t);
            }
        }
        // Loop to print
        // Compare with printDeadlinesWithStreams - in here, you need two iterations, but printDeadlinesWithStreams utilizes lazy evaluation and performs evaluation at the very end
    }

    public static void printDeadlinesWithStream(ArrayList<Task> tasks) {
        System.out.println("\nPrint deadlines using streams");
        tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .forEach(System.out::println);
    }
}
