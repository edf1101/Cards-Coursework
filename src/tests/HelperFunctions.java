package tests;

import main.Card;
import main.Deck;
import main.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class contains helper functions for the tests.
 */
public class HelperFunctions {
  /**
   * This helpful function gets the private hand field from the player class.
   *
   * @param player The player object to get the hand field from.
   * @return The ArrayList of Card hand field from the deck object. Null if error (shouldn't happen).
   */
  public static ArrayList<Card> getPlayerCards(Player player) {
    // Create Field object

    try {
      Field privateField = Player.class.getDeclaredField("hand");
      privateField.setAccessible(true);
      CopyOnWriteArrayList<Card> playerCards =
          (CopyOnWriteArrayList<Card>) privateField.get(player);
      // convert to ArrayList<Card>
      ArrayList<Card> playerCardsList = new ArrayList<>();
      playerCardsList.addAll(playerCards);
      return playerCardsList;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * This helpful function gets the private log field from the player class.
   *
   * @param player The player object to get the log field from.
   * @return The Log string, or null if can't find (should never happen).
   */
  public static String getPlayerLog(Player player) {
    // Create Field object
    try {
      Field privateField = Player.class.getDeclaredField("log");
      privateField.setAccessible(true);
      String log =
          (String) privateField.get(player);

      return log;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * This helpful function gets the private log field from the player class.
   *
   * @param player The player object to get the log field from.
   * @param newVal The new value to set the log field to.
   * @return The Log string, or null if can't find (should never happen).
   */
  public static void setPlayerLog(Player player, String newVal) {
    // Create Field object
    try {
      Field privateField = Player.class.getDeclaredField("log");
      privateField.setAccessible(true);
      privateField.set(player, newVal);


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This helper function resets the player's hand to an empty hand.
   *
   * @param player The player object to reset the hand of.
   */
  public static void resetPlayerHand(Player player) {
    try {
      Field privateField = Player.class.getDeclaredField("hand");
      privateField.setAccessible(true);
      CopyOnWriteArrayList<Card> playerCards =
          (CopyOnWriteArrayList<Card>) privateField.get(player);
      playerCards.clear();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This helper function reads the contents of a file and returns it as a string.
   *
   * @param path The path to the file to read.
   * @return The contents of the file as a string.
   */
  public static String readFileContents(String path) {
    String expectedPath = "gameData/player101.txt";
    // read in the file and check the contents
    String actualContents = "";
    try {
      java.io.File file = new java.io.File("gameData/player101.txt");
      assert (file.exists());
      java.util.Scanner scanner = new java.util.Scanner(file);
      while (scanner.hasNextLine()) {
        actualContents += scanner.nextLine() + "\n";
      }
      // remove the last newline character
      if (actualContents.length() > 0) {
        actualContents = actualContents.substring(0, actualContents.length() - 1);
      }
      scanner.close();
    } catch (java.io.FileNotFoundException e) {
      e.printStackTrace();
    }
    return actualContents;
  }

  /**
   * This helpful function gets the private deck field from the Deck class.
   *
   * @param deck The deck object to get the deck field from.
   * @return The ArrayList<Card> deck field from the deck object. Null if error (shouldn't happen).
   */
  public static ArrayList<Card> getDeckCards(Deck deck) {
    // Create Field object

    try {
      Field privateField = Deck.class.getDeclaredField("deck");
      privateField.setAccessible(true);
      CopyOnWriteArrayList<Card> deckCards = (CopyOnWriteArrayList<Card>) privateField.get(deck);
      // convert to ArrayList<Card>
      ArrayList<Card> deckCardsList = new ArrayList<>();
      deckCardsList.addAll(deckCards);
      return deckCardsList;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
