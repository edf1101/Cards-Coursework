package main;

/**
 * This class represents a card in the game It only needs to hold the denomination
 * and some quality of life functions.
 *
 * @author 730003140 & 730049916
 * @version 1.0
 */
public class Card {

  private final int denomination;

  /**
   * Constructor for the Card class.
   *
   * @param denomination The positive integer value of the card.
   * @throws IllegalArgumentException If the denomination is negative.
   */
  public Card(int denomination)
      throws IllegalArgumentException {

    if (denomination < 0) {
      throw new IllegalArgumentException("Denomination must be a positive integer");
    }

    this.denomination = denomination;
  }

  /**
   * Getter for the denomination of the card.
   *
   * @return The denomination of the card.
   */
  public int getDenomination() {
    return denomination;
  }

  /**
   * ToString method for the Card class.
   *
   * @return String representation of the card.
   */
  @Override
  public String toString() {
    return "Card of Value: " + denomination;
  }
}
