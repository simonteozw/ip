package duke;

import java.util.ArrayList;
import java.io.IOException;

/**
 * TaskList class to store all the tasks in Duke.
 */
public class TaskList {
    private ArrayList<Task> items;
    private int total;
    Storage storage;

    /**
     * Constructor for TaskList class.
     */
    public TaskList() {
        this.storage = new Storage();
        this.items = storage.readData();
        this.total = items.size();
    }

    /**
     * Exit function for TaskList class, writes data to .txt file.
     */
    public void bye() {
        try {
            storage.writeData(this.items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a task to the current TaskList.
     *
     * @param toAdd Task to be added.
     */
    public void add(Task toAdd) {
        this.items.add(toAdd);
        this.total++;
        Ui.addLine(String.format("    Got it. I've added this task:\n    %s\n    Now you have %d tasks in the list.", toAdd, this.total));
    }

    /**
     * Prints out all tasks in current TaskList.
     */
    public void display() {
        String res = "Here are your tasks:\n";
        for (int i = 0; i < this.total; i++) {
            res += String.format("    %d.%s\n", i + 1, this.items.get(i));
        }
        Ui.addLine(res);
    }

    /**
     * Completes the specified task.
     *
     * @param idx Index of task to be completed.
     * @throws InvalidIndexException In case idx is out of bounds.
     */
    public void completeTask(int idx) throws InvalidIndexException {
        if (idx < 0 || idx >= this.total) {
            throw new InvalidIndexException();
        }
        Task t = this.items.get(idx);
        t.complete();
        Ui.addLine(String.format("    Nice! I've marked this task as done:\n    %s", t));
    }

    /**
     * Deletes the specified task.
     *
     * @param idx Index of task to be deleted.
     * @throws InvalidIndexException In case idx is out of bounds.
     */
    public void deleteTask(int idx) throws InvalidIndexException {
        if (idx < 0 || idx >= this.total) {
            throw new InvalidIndexException();
        }
        Task t = this.items.get(idx);
        this.items.remove(idx);
        this.total--;
        Ui.addLine(String.format("    Nice! I've removed this task:\n    %s\n    Now you have %d tasks in the list.", t, this.total));
    }
}