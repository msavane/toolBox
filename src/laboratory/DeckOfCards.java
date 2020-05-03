package laboratory;

import java.util.ArrayList;
import java.util.Arrays;

public class DeckOfCards {

    public static void main(String[] args){


        System.out.println("Here is a new set of cards for you !");
        System.out.println(getNewDeck());

    }
    private static String getNewDeck(){

        //Init known
        String rank = "Ace,2,3,4,5,6,7,8,9,10,Jack,Queen,King";
        String suit = "Spades,Hearts,Diamonds,Clubs";
        String suitSymbols = "♤,♡,⃟,♧";
        //build deck
        ArrayList rankList= new ArrayList(Arrays.asList(rank.split(",",-1)));
        ArrayList suitList= new ArrayList(Arrays.asList(suit.split(",",-1)));
        ArrayList suitSymbolsList= new ArrayList(Arrays.asList(suitSymbols.split(",",-1)));
        String newDeck = "52 Cards! enjoy!";
        for(int i=0;i<rankList.size();i++)
            for(int y=0;y<suitList.size();y++)
            {

                System.out.println(suitSymbolsList.get(y) +" "+ rankList.get(i) + " of "+suitList.get(y));

            }

        return newDeck;
    }
}
