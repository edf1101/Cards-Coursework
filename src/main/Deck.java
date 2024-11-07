package main;

import java.util.ArrayList;

/**
 * This class represents the 4 card deck surrounding each player.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class Deck {
  private final int deckId;
  private final ArrayList<Card> deck = new ArrayList<>();

  /**
   * Constructor for the Deck class.
   *
   * @param deckId The deck number, This should correspond to the player number.
   */
  public Deck(int deckId) {
    this.deckId = deckId;
  }

  /**
   * Add a card to the top of the deck.
   *
   * @param card The card to add to the deck.
   */
  public void addCard(Card card) {
    deck.add(card);
  }

  /**
   * Remove a card from the bottom of the deck.
   *
   * @return The card removed from the deck.
   */
  public Card removeCard() {
    return deck.removeFirst();
  }

  /**
   * Getter for the deckId of the deck.
   *
   * @return The deckId of the deck.
   */
  public int getDeckId() {
    return deckId;
  }

  @Override
  public String toString() {

    String desc = "Hand of deck " + deckId + ":\n";
    for (Card card : deck) {
      desc += card.denomination + ",";
    }

    // get rid of comma at end
    desc = desc.substring(0, desc.length() - 1);

    return desc;
  }
}
