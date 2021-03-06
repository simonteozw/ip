package duke.task;

/**
 * Task class to represent a task to be manipulated by Duke.
 */
public abstract class Task {
    protected String title;
    protected boolean isComplete;
    protected String[] tags;

    /**
     * Constructor of Task class.
     *
     * @param title Title of task.
     */
    public Task(String title, String[] tags) {
        this.title = title;
        this.isComplete = false;
        this.tags = tags;
    }

    /**
     * Overloads constructor of Task class. Only used to read data from .txt file.
     *
     * @param title      Title of task.
     * @param isComplete Boolean to represent completion status of task.
     */
    public Task(String title, boolean isComplete, String[] tags) {
        this.title = title;
        this.isComplete = isComplete;
        this.tags = tags;
    }

    /**
     * Completes this task.
     */
    public void complete() {
        this.isComplete = true;
    }

    protected String convertTagsToString(String separator) {
        String res = "";
        for (int i = 0; i < tags.length; i++) {
            if (i == tags.length - 1) {
                res += String.format("%s", tags[i]);
            } else {
                res += String.format("%s%s", tags[i], separator);
            }
        }
        return res;
    }

    /**
     * Converts the task to a suitable string that can be saved to the .txt file.
     *
     * @return String describing the task.
     */
    public abstract String getSaveString();
}
