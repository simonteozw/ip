import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

public class Storage {
    private final String HOME = System.getProperty("user.home");
    private final java.nio.file.Path PATH = java.nio.file.Paths.get(HOME, "iPStore.txt");

    public Storage() {}

    private String allTasksCombined(ArrayList<Task> items) {
        String res = "";
        for (Task item : items) {
            res += String.format("%s", item.saveString());
            res += "\n";
        }
        return res;
    }

    private Task stringToTask(String str){
        String[] info = str.split("\\|");
        Boolean isComplete = info[1].equals("1");
        switch (info[0]) {
            case "T":
                return new Todo(info[2], isComplete);
            case "D":
            case "E":
                return new Event(info[2], isComplete, info[3]);
            default:
                return new Task(info[2], isComplete);
        }
    }

    public void writeData(ArrayList<Task> items) throws IOException {
        try {
            FileWriter fw = new FileWriter(PATH.toFile());
            PrintWriter pw = new PrintWriter(fw);

            pw.print(allTasksCombined(items));

            pw.close();
        } catch (IOException e){
            throw e;
        }
    }

    public ArrayList<Task> readData() {
        Boolean fileExists = java.nio.file.Files.exists(PATH);

        ArrayList<Task> res = new ArrayList<>();

        if (fileExists) {
            try {
                FileReader fr = new FileReader(PATH.toFile());
                BufferedReader br = new BufferedReader(fr);
                String str;
                while ((str = br.readLine()) != null) {
                    res.add(stringToTask(str));
                }
            } catch (IOException e) {}
        }

        return res;
    }
}