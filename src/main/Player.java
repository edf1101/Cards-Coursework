package main;

import java.util.ArrayList;

/**
 * This class represents a player in the game. It holds a hand of cards, and the player logic.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class Player {

  private final ArrayList<Card> hand = new ArrayList<>();
  private final int playerNumber;

  private final Deck prevDeck;
  private final Deck nextDeck;

  /**
   * Constructor for the player class.
   *
   * @param playerNumber The player number.
   * @param prevDeck     The previous (same number) deck.
   * @param nextDeck    The next deck found at (i+1)%n.
   */
  public Player(int playerNumber, Deck prevDeck, Deck nextDeck) {
    this.playerNumber = playerNumber;
    this.prevDeck = prevDeck;
    this.nextDeck = nextDeck;
  }

  /**
   * Getter for the player number.
   *
   * @return The player number.
   */
  public int getPlayerNumber() {
    return playerNumber;
  }

  /**
   * Add the card to the player's hand.
   *
   * @param card The card to add to the player's hand.
   */
  public void addCard(Card card) {
    hand.add(card);
  }

  @Override
  public String toString() {

    String desc = "Hand of player " + playerNumber + ":\n";
    for (Card card : hand) {
      desc += card.denomination + ",";
    }
    desc = desc.substring(0, desc.length() - 1);
    return desc;
  }
}
