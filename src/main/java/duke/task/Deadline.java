package duke.task;

/**
 * Deadline class for tasks that have a set deadline.
 */
public class Deadline extends Task {
    static final String SYMBOL = "D";
    public static final String FORMAT = "deadline [^ ]+.+[^ ]+ /by [^ ]+.+";
    private DateTime deadline;

    /**
     * Constructor for Deadline class.
     *
     * @param title    Title of task.
     * @param deadline Deadline of task.
     */
    public Deadline(String title, String deadline) {
        super(title);
        this.deadline = new DateTime(deadline);
    }

    /**
     * Overloads constructor for Deadline class.
     *
     * @param title      Title of task.
     * @param isComplete Boolean to represent completion status of task.
     * @param deadline   Deadline of task.
     */
    public Deadline(String title, boolean isComplete, String deadline) {
        super(title, isComplete);
        this.deadline = new DateTime(deadline);
    }

    /**
     * Overrides method of saveString method in Task.
     *
     * @return A string to that can be saved into the .txt file
     */
    @Override
    public String getSaveString() {
        int completeSymbol = this.complete ? 1 : 0;
        return String.format("%s|%d|%s|%s", SYMBOL, completeSymbol, this.title, this.deadline.getSaveString());
    }

    /**
     * Overrides the standard toString method.
     *
     * @return A String describing the task with deadline.
     */
    @Override
    public String toString() {
        String completeSymbol = this.complete ? "[/]" : "[X]";
        return String.format("[%s]%s %s (by: %s)", SYMBOL, completeSymbol, this.title, this.deadline);
    }
}
