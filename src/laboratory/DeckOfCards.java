package laboratory;

import data_factory.DataCreatorHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DeckOfCards {

    static DataCreatorHelper dch = new DataCreatorHelper();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        System.out.println("Here is a new set of cards for you !");
        //System.out.println(getNewDeck());
        selectCard();

    }

    private static ArrayList getNewDeck() {

        //Init known
        String rank = "Ace,2,3,4,5,6,7,8,9,10,Jack,Queen,King";
        String suit = "Spades,Hearts,Diamonds,Clubs";
        String suitSymbols = "♤,♡,⃟,♧";
        //build deck
        ArrayList rankList = new ArrayList(Arrays.asList(rank.split(",", -1)));
        ArrayList suitList = new ArrayList(Arrays.asList(suit.split(",", -1)));
        ArrayList suitSymbolsList = new ArrayList(Arrays.asList(suitSymbols.split(",", -1)));

        String s = null;
        ArrayList newDeckOfCards = new ArrayList(Arrays.asList(",", -1));

        for (int i = 0; i < rankList.size(); i++)
            for (int y = 0; y < suitList.size(); y++) {

                s = suitSymbolsList.get(y) + " " + rankList.get(i) + " of " + suitList.get(y);
                newDeckOfCards.add(suitSymbolsList.get(y) + " " + rankList.get(i) + " of " + suitList.get(y));

            }
        System.out.println("New Set of cards! enjoy!");
        return newDeckOfCards;
    }

    private static ArrayList printDeck(ArrayList arr) {


        //ArrayList newDeck = getNewDeck();

        for (int i = 2; i < arr.size(); i++) {

            System.out.println(arr.get(i));
        }

        return null;
    }


    private static String selectCard() {

        ArrayList newDeck = getNewDeck();
        int min = 1;
        int max = newDeck.size();
       // for (int i = 2; i < newDeck.size(); i++) {
        int ran = dch.getRandomNumber(min, max - 5);
            //System.out.println(newDeck.get(i));
       // }
        System.out.println("computer placed: "+ran);

        System.out.println(newDeck.get(ran)  + " has been randomly selected");
        newDeck.remove(newDeck.get(ran));

        System.out.println("Please enter a number:");
        int playersSelection = scanner.nextInt();

        System.out.println( "you've played "+newDeck.get(playersSelection));
        newDeck.remove(newDeck.get(playersSelection));

        //printDeck(newDeck);

        return null;
    }
}
