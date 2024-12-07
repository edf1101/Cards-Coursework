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
   * Add a card to the bottom of the deck (The first of the list).
   *
   * @param card The card to add to the deck.
   */
  public void addCard(Card card) {

    // add to the front of the list.
    ArrayList<Card> newDeckCards = new ArrayList<>();
    newDeckCards.add(card);
    newDeckCards.addAll(deck);
    deck.clear();
    deck.addAll(newDeckCards);
  }

  /**
   * Remove a card from the top of the deck (last index).
   *
   * @return The card removed from the deck. Or null if the deck is empty.
   */
  public Card removeCard() {
    if (deck.isEmpty()) {
      return null;
    }

    Card lastCard = deck.get(deck.size() - 1);
    deck.remove(deck.size() - 1);
    return lastCard;
  }

  /**
   * This method gets a string representation of the deck.
   *
   * @return The string representation of the deck.
   */
  @Override
  public String toString() {

    String desc = "Hand of deck " + deckId + ":\n";
    for (Card card : deck) {
      desc += card.getDenomination() + ",";
    }

    // to get rid of comma at end
    desc = desc.substring(0, desc.length() - 1);

    return desc;
  }

  /**
   * Returns whether a player can add a card. Check it isn't full.
   *
   * @return True if deck size <= 4.
   */
  public boolean canAddCard() {
    return deck.size() <= 4;
  }

  /**
   * Returns whether a player can take a card. Check it is full.
   *
   * @return True if deck size >= 4.
   */
  public boolean canTakeCard() {
    return deck.size() >= 4;
  }
}
