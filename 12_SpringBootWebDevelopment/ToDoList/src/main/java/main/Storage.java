package main;

import main.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {
    public static int currentId = 1;
    private static HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();

    public static List<Task> getAllTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        tasksList.addAll(tasks.values());
        return tasksList;
    }

    public static Integer addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public static Task getTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }


    public static int changeSeveralTasks(List<Task> newTasks) {

        for (Task newTask : newTasks) {
            if (!tasks.containsKey(newTask.getId())) {
                return 0;
            }
        }
        for (Task newTask : newTasks) {
            tasks.put(newTask.getId(), newTask);
        }
        return 1;

    }

    public static int changeTask(int id, Task changedTask) {

        if (tasks.containsKey(id)) {
            tasks.put(id, changedTask);
        } else {
            return 0;
        }
        return 1;
    }

    public static void deleteAll() {
        tasks.clear();
        currentId = 1;
    }

    public static int deleteTask(int id) {

        if (tasks.containsKey(id)) {
            tasks.remove(id);
            return id;
        }
        return 0;
    }
}
