package casinogame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileHandling {

    public static String folderDirectory = System.getProperty("user.dir") + "\\playerList.txt";

    public static void writePlayerFile(ArrayList<Player> playerList) {

        try {
            FileWriter writeToFile = new FileWriter(folderDirectory, false);
            PrintWriter printToFile = new PrintWriter(writeToFile);
            for (int i = 0; i < playerList.size(); i++) {
                printToFile.println(playerList.get(i).toString());
            }
            printToFile.close();
            writeToFile.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    public static ArrayList<Player> readPlayerFile() {
        ArrayList<Player> playerList = new ArrayList<>();
        String lineFromFile;
        try {
            BufferedReader read = new BufferedReader(new FileReader(folderDirectory));
            while ((lineFromFile = read.readLine()) != null) {
                String[] playerDetails = lineFromFile.split(", ");

                Player newPlayer = new Player(playerDetails[0], playerDetails[1], playerDetails[2], playerDetails[3], Double.parseDouble(playerDetails[4]));
                playerList.add(newPlayer);
            }
            read.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return playerList;
    }


}
