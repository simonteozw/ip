package duke.misc;

import java.io.IOException;
import java.util.Arrays;

import duke.exception.InvalidDescriptionException;
import duke.exception.InvalidIndexException;
import duke.exception.InvalidTypeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

/**
 * Parser class to handle user inputs.
 */
public class Parser {

    /**
     * Categorise user input into different types of tasks to add.
     *
     * @param input User input.
     * @return A task to be added by the TaskList.
     * @throws InvalidDescriptionException In case the task description is empty.
     * @throws InvalidTypeException        In case the task type is not one of Event, Deadline, Todo.
     */
    public static Task handleInput(String input) throws InvalidDescriptionException, InvalidTypeException {
        String[] tags = extractTags(input);
        String cleanInput = removeHashTags(input);
        String type = cleanInput.split("\\s+")[0];
        switch (type) {
        case "todo":
            if (!cleanInput.matches(Todo.FORMAT)) {
                throw new InvalidDescriptionException();
            }
            return new Todo(cleanInput.substring(5), tags);
        case "deadline":
            if (!cleanInput.matches(Deadline.FORMAT)) {
                throw new InvalidDescriptionException();
            }
            String[] dl = cleanInput.split(" /by ");
            return new Deadline(dl[0].substring(9), dl[1], tags);
        case "event":
            if (!cleanInput.matches(Event.FORMAT)) {
                throw new InvalidDescriptionException();
            }
            String[] e = cleanInput.split(" /at ");
            return new Event(e[0].substring(6), e[1], tags);
        default:
            throw new InvalidTypeException();
        }
    }

    private static String[] extractTags(String input) {
        return Arrays.stream(input.split("\\s+")).filter(str -> str.matches("#.+")).toArray(String[]::new);
    }

    private static String removeHashTags(String input) {
        return input.replace("#", "");
    }


    /**
     * Maps user input to actions the TaskList must carry out.
     *
     * @param input User input.
     * @param tl    TaskList to be used.
     */
    public static String allocate(String input, TaskList tl) {
        String[] arr = input.split(" ");
        int idx;

        switch (arr[0]) {
        case "bye":
            try {
                tl.saveData();
                return Ui.GOODBYE;
            } catch (IOException e) {
                return e.toString();
            }
        case "list":
            return tl.display();
        case "find":
            try {
                return tl.find(input);
            } catch (InvalidDescriptionException e) {
                return e.toString();
            }
        case "done":
            idx = Integer.parseInt(arr[1]) - 1;
            try {
                return tl.completeTask(idx);
            } catch (InvalidIndexException e) {
                return e.toString();
            }
        case "delete":
            idx = Integer.parseInt(arr[1]) - 1;
            try {
                return tl.deleteTask(idx);
            } catch (InvalidIndexException e) {
                return e.toString();
            }
        default:
            try {
                Task toAdd = handleInput(input);
                return tl.add(toAdd);
            } catch (InvalidDescriptionException e) {
                return e.toString();
            } catch (InvalidTypeException e) {
                return e.toString();
            }
        }
    }
}
