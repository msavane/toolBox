package laboratory;

import data_factory.DataCreatorHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class DeckOfCards {

    static DataCreatorHelper dch = new DataCreatorHelper();
    static Scanner scanner = new Scanner(System.in);
    static int replay = 0;

    //build deck
    static ArrayList rankList;
    static ArrayList suitList;
    static ArrayList suitSymbolsList;
    static ArrayList newDeckOfCards;
    static ArrayList newDeck;


    public static void main(String[] args) {

        //Init known
        String rank = "Ace,2,3,4,5,6,7,8,9,10,Jack,Queen,King";
        String suit = "Spades,Hearts,Diamonds,Clubs";
        String suitSymbols = "♤,♡,⃟,♧";

        //build deck
        rankList = new ArrayList(Arrays.asList(rank.split(",", -1)));
        suitList = new ArrayList(Arrays.asList(suit.split(",", -1)));
        suitSymbolsList = new ArrayList(Arrays.asList(suitSymbols.split(",", -1)));
        newDeckOfCards = new ArrayList(Arrays.asList(",", -1));

        System.out.println("New Set of cards! enjoy!");
        selectCard();


    }

    private static ArrayList getNewDeck() {

        String s = null;


        for (int i = 0; i < rankList.size(); i++)
            for (int y = 0; y < suitList.size(); y++) {

                s = suitSymbolsList.get(y) + " " + rankList.get(i) + " of " + suitList.get(y);
                newDeckOfCards.add(suitSymbolsList.get(y) + " " + rankList.get(i) + " of " + suitList.get(y));

            }
        System.out.println("Matching suits, here is the choice of the computer !");

        return newDeckOfCards;
    }

    private static String printDeck(ArrayList arr) {

        for (int i = 0; i < arr.size(); i++) {

            System.out.println(arr.get(i));
        }

        return null;
    }

    private static void compareCards(String pc, String player) {
        String searchMe = player;
        String findMe = pc;
        int findMeLength = 1;
        boolean foundIt = false;

        for (int i = 0;
             i == 0;
             i++) {
            if (searchMe.regionMatches(i, findMe, 0, findMeLength)) {
                foundIt = true;
                System.out.println(searchMe.substring(0, i + findMeLength));
                System.out.println("you WIN !! Flabola© EIGHT");
                System.exit(0);
                break;
            } else {
                System.out.println("No match found.");

            }
        }

    }

    private static String selectCard() {

        if (replay == 0) {
            newDeck = getNewDeck();
            Collections.shuffle(newDeck);
        }


        int min = 1;
        int max = 8;


        for (int m = 0; m < newDeck.size(); m++) {

            if (newDeck.get(m).equals(",")) {
                newDeck.remove(newDeck.get(m));
            }
            if (newDeck.get(m).equals(-1)) {
                newDeck.remove(newDeck.get(m));
            }

        }

        int rand = dch.getRandomNumber(min, max - min) + min;

        System.out.println("random selection: " + rand);

        System.out.println("computer played " + newDeck.get(rand));
        String computersChoice = (String) newDeck.get(rand);
        newDeck.remove(newDeck.get(rand));

        System.out.println("Please enter a number between 1 - 8:");
        int playersSelection = scanner.nextInt();

        System.out.println("you've played " + newDeck.get(playersSelection += 1));
        String playersChoice = (String) newDeck.get(playersSelection);
        newDeck.remove(newDeck.get(playersSelection));

        int c = 8;
        if (newDeck.size() <= c) {
            printDeck(newDeck);
        }
        compareCards(computersChoice, playersChoice);

        System.out.println("Would you like to play again, enter 1 - for yes or 2 for No:");
        int playAgain = scanner.nextInt();
        if (playAgain == 1) {
            replay = 1;
            selectCard();
        } else {
            replay = 0;
            printDeck(newDeck);
        }

        return null;
    }
}