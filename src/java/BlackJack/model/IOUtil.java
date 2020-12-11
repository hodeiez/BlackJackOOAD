package BlackJack.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOUtil {
    public static List<String> readFromFileToList(String file) {
        List<String> list = new ArrayList<>();
        try (Scanner in = new Scanner(new File(file))) {
            while (in.hasNextLine()) {
                list.add(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean writeListToFile(List<String> list, String filePath) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            for (String s : list) {
                out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeHighScoreToFile(HighScore highScore) {
        return true;
    }
}
