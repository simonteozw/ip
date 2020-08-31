package duke;

/**
 * UI class to handle aesthetics of Duke.
 */
public class Ui {
    private static final String OPENING = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    static final String WELCOME = OPENING + "\nHello I am Duke!\nWhat can I help you with?";
    static final String GOODBYE = "Goodbye. See you soon!";
    private static final String LINE = "--------------------------------------------------";

    /**
     * Adds line under given input for aesthetics.
     *
     * @param input Input for which line shall be printed under.
     */
    public static void addLine(String input) {
        System.out.println(input);
        System.out.println(LINE);
    }
}
