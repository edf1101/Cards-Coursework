package main;

import java.io.IOException;

/**
 * This is an interface for the pack class.
 * This is so that we can create mock classes.
 */
public interface IPack {

  /**
   * Get the cards in the pack.
   *
   * @return The cards in the pack.
   */
  public Card[] getCards();

  /**
   * Get how many players in the pack.
   *
   * @return Player count.
   */
  public int getPlayerCount();

  /**
   * A string representation of the pack.
   *
   * @return A string representation of the pack.
   */
  public String toString();
}
