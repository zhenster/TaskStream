package se.edu.inclass.data;

import se.edu.inclass.task.Deadline;
import se.edu.inclass.task.Event;
import se.edu.inclass.task.Task;
import se.edu.inclass.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private File dataFile;

    public File getDataFile() {
        return dataFile;
    }

    public DataManager(String fileName) {
        dataFile = new File(fileName);
    }

    public void createFile() {
        try {
            if (dataFile.exists()) {
                System.out.println("file exists");
                return;
            }
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }
            dataFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Cannot create file; reason: " + e.getMessage());
        }
    }

    private ArrayList readFile() throws IOException {
        if (!dataFile.exists()) {
            throw new FileNotFoundException();
        }
        if (dataFile.length() == 0) {
            System.out.println("empty file");
            throw new IOException();
        }
        ArrayList<String> dataItems = (ArrayList) Files.readAllLines(dataFile.toPath(), Charset.defaultCharset());

        return dataItems;
    }

    public ArrayList<Task> loadData() {
        ArrayList<Task> taskList = null;
        try {
            ArrayList<String> dataItems = readFile();
            taskList = parse(dataItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    private ArrayList<Task> parse(ArrayList<String> dataItems) {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (String line : dataItems) {
            String taskType = getTaskType(line);
            String taskDescription = getTaskDescription(line);
            switch (taskType) {
            case "T":
                ToDo todo = new ToDo(taskDescription);
                allTasks.add(todo);
                break;
            case "D":
                Deadline deadline = new Deadline(taskDescription);
                allTasks.add(deadline);
                break;
            case "E":
                Event event = new Event(taskDescription);
                allTasks.add(event);
                break;
            default:
                System.out.println("Unknown task encountered. Skipping");
                break;
            }
        }
        return allTasks;
    }

    private String getTaskDescription(String line) {
        String taskDescription = line.substring(4);
        return taskDescription;
    }

    private String getTaskType(String line) {
        String taskType = line.substring(0, 2);
        taskType = taskType.replace("[", "");
        taskType = taskType.replace("]", "");
        return taskType;
    }
}
