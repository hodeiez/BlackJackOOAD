package BlackJack;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Lukas Aronsson
 * Date: 01/12/2020
 * Time: 16:23
 * Project: BlackJackOOAD
 * Copyright: MIT
 **/
public class ComputerPlayer extends Player {

    private boolean broke = false; //if the ComputerPlayer is all out of balance (gets removed form the game)

    // TODO: 12/1/2020 : Lukas :  add bot stopValue for easier properties later?

    /**
     * Returns a random name for the ComputerPlayer
     * @return a random generated name from a text file
     */
    private String generateRandomName(){
        ArrayList<String> botNames = new ArrayList<>();
        //BotNames.txt contains the names of the easy bots from cs (testing)
        try(Scanner FileScan = new Scanner(new FileReader("BotNames.txt"))){
            while (FileScan.hasNext()) //for each line in the txt file
                botNames.add(FileScan.next()); //adds to botNames
        } catch (Exception e ){
            e.printStackTrace();
        }
        // TODO: 12/2/2020 : Lukas : add a check for already taken playerNames (need BlackJackLogic)
        Random rand = new Random(botNames.size());
        return botNames.get(rand.nextInt());
    }

    /**
     * a check if the ComputerPlayer Should pickup cards or not
     * @return the ComputerPlayer should pickup a card or not
     */
    private boolean shouldHit(){


        return true;
    }

    /**
     * Getter For broke
     * @return if the ComputerPlayer is all out of balance (gets removed form the game)
     */
    public boolean isBroke() {
        return broke;
    }

    /**
     * Setter for broke
     * @param broke if the ComputerPlayer is all out of balance (gets removed form the game)
     */
    public void setBroke(boolean broke) {
        this.broke = broke;
    }
}
