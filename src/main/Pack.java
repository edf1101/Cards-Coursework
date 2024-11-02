package main;

import java.util.ArrayList;
import java.io.*;

/**
 * This class represents a pack of cards in the card game. All file reading / error checking is
 * done here to abstract complexity from the main game class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class Pack {

  private final Card[] cards;

  /**
   * Constructor for the Pack class.
   *
   * @param n    The number of players. There should be 8*n total cards.
   * @param path The path to the file containing the cards.
   * @throws IOException If the file is not found or cannot be read.
   */
  public Pack(int n, String path)
      throws IOException, NumberFormatException {

    // Try and open / read the file
    File file = new File(path);
    BufferedReader br
        = new BufferedReader(new FileReader(file));

    String line;
    ArrayList<Integer> readValues = new ArrayList<>();
    while ((line = br.readLine()) != null) {
      // Print the string
      try {
        int value = Integer.parseInt(line);
        readValues.add(value);
      } catch (NumberFormatException e) {
        throw new NumberFormatException("Invalid card value (should be Int): " + line);
      }
    }

    // Check if the number of cards is correct
    if (readValues.size() != 8 * n) {
      throw new IOException("Invalid number of cards in file. Expected " + 8 * n + " got " + readValues.size());
    }

    cards = new Card[8 * n];
    // Now all the cards are valid, add them to the pack
    for (int i = 0; i < readValues.size(); i++) {
      cards[i] = new Card(readValues.get(i));
    }
  }

  /**
   * Get the cards in the pack.
   *
   * @return The cards in the pack.
   */
  public Card[] getCards() {
    return cards;
  }

  /**
   * A string representation of the pack.
   *
   * @return A string representation of the pack.
   */
  @Override
  public String toString() {
    String returnVal = "This is a valid pack with the following " + cards.length + " cards:\n";

    for (Card card : cards) {
      returnVal += card.getDenomination() + ", ";
    }
    returnVal = returnVal.substring(0, returnVal.length() - 2); // Remove the last comma and space

    return returnVal;
  }
}
