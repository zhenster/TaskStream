package se.edu.inclass;

import se.edu.inclass.data.DataManager;
import se.edu.inclass.task.Deadline;
import se.edu.inclass.task.Task;
import se.edu.inclass.task.TaskNameComparator;

import java.util.ArrayList;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

public class Main {

    private TaskNameComparator taskNameComparator;

    public static void main(String[] args) {
        DataManager dm = new DataManager("./data/data.txt"); // Class is being run from this particular file location
        ArrayList<Task> tasksData = dm.loadData();

        printData(tasksData);
        System.out.println("\nPrinting deadlines");
        printDeadlines(tasksData);

        System.out.println("\nTotal number of deadlines: " + countDeadlines(tasksData));
        printDeadlinesWithStream(tasksData);

        ArrayList<Task> filteredList = filterTasksByString(tasksData, "11");


    }

    public static ArrayList<Task> filterTasksByString(ArrayList<Task> tasksData, String s) {
        ArrayList<Task> filteredList = (ArrayList<Task>) tasksData.stream();
        tasksData.stream()
                .filter((t) -> t.getDescription().contains(s))
                .collect(toList());
        return filteredList;
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

    public static void printData(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            System.out.println(t);
        }
    }

    public static void printDeadlines(ArrayList<Task> tasksData) {
        for (Task t : tasksData) {
            if (t instanceof Deadline) {
                System.out.println(t);
            }
        }
    }

    public static void printDeadlinesWithStream(ArrayList<Task> tasks) {
        System.out.println("\nPrinting deadline with stream (sorted)");
        tasks.stream()
                .filter((t) -> t instanceof Deadline)
                .sorted((a, b) -> a.getDescription().toLowerCase().compareTo(b.getDescription().toLowerCase()))
                .forEach(System.out::println);
    }
}
