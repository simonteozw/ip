package duke.misc;

import static duke.misc.Commands.FIND;

import java.io.IOException;
import java.util.ArrayList;

import duke.exception.InvalidDataException;
import duke.exception.InvalidDescriptionException;
import duke.exception.InvalidIndexException;
import duke.exception.InvalidTypeException;
import duke.task.Task;

/**
 * TaskList class to store all the tasks in Duke.
 */
public class TaskList {
    private static final String SEARCH_FORMAT = String.format("%s [^ ]+.+", FIND);
    private ArrayList<Task> items;
    private Storage storage;
    private boolean isInitialised;

    /**
     * Constructor for TaskList class.
     */
    public TaskList() {
        this.storage = new Storage();
        this.items = new ArrayList<>();
        this.isInitialised = false;
    }

    /**
     * Function to read in data from .txt file.
     *
     * @throws IOException          In case data has errors
     * @throws InvalidTypeException In cases data has an invalid type.
     * @throws InvalidDataException In case data is invalid (.txt file is corrupted).
     */
    public void initialise() throws IOException, InvalidTypeException, InvalidDataException {
        try {
            this.items = this.storage.readData();
            this.isInitialised = true;
        } catch (InvalidTypeException e) {
            throw e;
        } catch (InvalidDataException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Function to tell whether items have been loaded from the .txt file.
     *
     * @return A boolean indicating whether the taskList has been initialised.
     */
    public boolean isInitialised() {
        return this.isInitialised;
    }

    /**
     * Exit function for TaskList class, writes data to .txt file.
     *
     * @throws IOException In case there are errors when writing the data.
     */
    public void saveData() throws IOException {
        try {
            storage.writeData(this.items);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Adds a task to the current TaskList.
     *
     * @param toAdd Task to be added.
     */
    public String add(Task toAdd) {
        this.items.add(toAdd);
        return String.format(Ui.ADD_TASK_MESSAGE, toAdd, this.items.size());
    }

    /**
     * Gets all tasks in TaskList
     *
     * @return A formatted string.
     */
    public String display() {
        String res = Ui.LIST_MESSAGE;
        for (int i = 0; i < items.size(); i++) {
            res += String.format("    %d.%s\n", i + 1, items.get(i));
        }
        if (items.size() == 0) {
            res = Ui.EMPTY_LIST_MESSAGE;
        }
        return res;
    }

    /**
     * Overloads the display method.
     *
     * @param arrayList ArrayList of tasks to be displayed.
     * @return A formatted string.
     */
    public String display(ArrayList<Task> arrayList) {
        String res = Ui.LIST_MESSAGE;
        for (int i = 0; i < arrayList.size(); i++) {
            res += String.format("    %d.%s\n", i + 1, arrayList.get(i));
        }
        if (arrayList.size() == 0) {
            res = Ui.EMPTY_LIST_MESSAGE;
        }
        return res;
    }

    /**
     * Function to find all tasks with specified keyword.
     *
     * @param keyWord Keyword to search for.
     * @return A String containing all relevant tasks.
     * @throws InvalidDescriptionException InvalidDescriptionException In case keyword is empty.
     */
    public String find(String keyWord) throws InvalidDescriptionException {
        if (!keyWord.matches(SEARCH_FORMAT)) {
            throw new InvalidDescriptionException();
        }
        ArrayList<Task> temp = new ArrayList<>();
        for (Task task : items) {
            if (task.toString().contains(keyWord.substring(5))) {
                temp.add(task);
            }
        }
        return this.display(temp);
    }

    /**
     * Completes the specified task.
     *
     * @param idx Index of task to be completed.
     * @return A string to validate completion of task.
     * @throws InvalidIndexException In case idx is out of bounds.
     */
    public String completeTask(int idx) throws InvalidIndexException {
        if (idx < 0 || idx >= this.items.size()) {
            throw new InvalidIndexException();
        }
        Task t = this.items.get(idx);
        t.complete();
        return String.format(Ui.COMPLETE_TASK_MESSAGE, t);
    }

    /**
     * Deletes the specified task.
     *
     * @param idx Index of task to be deleted.
     * @return A string to validate deletion of task.
     * @throws InvalidIndexException In case idx is out of bounds.
     */
    public String deleteTask(int idx) throws InvalidIndexException {
        if (idx < 0 || idx >= this.items.size()) {
            throw new InvalidIndexException();
        }
        Task t = this.items.get(idx);
        this.items.remove(idx);
        return String.format(Ui.DELETE_TASK_MESSAGE, t, this.items.size());
    }
}
