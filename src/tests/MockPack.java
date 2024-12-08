package tests;

import main.Card;
import main.IPack;

/**
 * This is a mockPack class where we can set the cards ourselves
 * without reading from a file.
 */
public class MockPack implements IPack {

  private final Card[] cards;
  private final int playerCount;

  /**
   * Constructor for the MockPack class to just read in a set of cards.
   *
   * @param cards
   */
  public MockPack(int n, Card[] cards) {
    this.cards = cards;
    this.playerCount = n;
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
   * Get how many players in the pack.
   *
   * @return Player count.
   */
  public int getPlayerCount() {
    return playerCount;
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
