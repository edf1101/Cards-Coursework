package main;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This is the thread safe player class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class Player implements Runnable {

  private final int playerNumber;
  private final ArrayList<Card> hand = new ArrayList<>();
  private final Deck pickupDeck;
  private final Deck discardDeck;
  private final AtomicInteger winnerNumber;

  /**
   * Constructor for the player class.
   *
   * @param playerNum    The player number.
   * @param pickupDeck   The deck the player picks up cards from.
   * @param discardDeck  The deck the player discards cards to.
   * @param winnerNumber The game-wide winner number, this is a reference that all players have to
   *                     check/write to if the game has been won.
   */
  public Player(int playerNum, Deck pickupDeck, Deck discardDeck, AtomicInteger winnerNumber) {
    this.playerNumber = playerNum;
    this.pickupDeck = pickupDeck;
    this.discardDeck = discardDeck;
    this.winnerNumber = winnerNumber;
  }

  /**
   * Gets the player's ID.
   *
   * @return an integer representing the player's ID.
   */
  public int getPlayerNumber() {
    return playerNumber;
  }

  /**
   * Adds a card to the player's hand.
   * Only should be used to deal cards to the player.
   *
   * @param card The card to be added to the player's hand.
   */
  public void addCard(Card card) {

    // TODO have some error throwing here if the hand is full, or dodgy card
    hand.add(card);
  }

  /**
   * Check if this player has won or not.
   *
   * @return True if the player has won, false otherwise.
   */
  public boolean checkWin() {
    // TODO error checking if the hand is empty (not set up yet)

    // Checks if there are any cards that are different. If so, the player has not won.
    for (Card card : hand) {
      if (card.getDenomination() != hand.get(0).getDenomination()) {
        return false;
      }
    }
    // If the player has got here, then all cards are the same, so they have won.
    winnerNumber.set(playerNumber);
    return true;
  }

  /**
   * This function is called when the thread starts, it runs the game loop and exits
   * when a player has won.
   */
  public void run() {
    while (winnerNumber.get() == -1) {
      if (checkWin()) {
        break;
      }
      takeTurn();
    }
  }

  /**
   * This function contains the code run each turn a player takes.
   */
  private void takeTurn() {
    synchronized (Player.class) { // To keep in sync with all other Player class instances.
      if (!pickupDeck.canTakeCard()) {
        return;
      }

      // select card to discard
      Card discardCard = null;

      for (Card card : hand) {
        if (card.getDenomination() != playerNumber) {
          discardCard = card;
          break;
        }
      }
      if (discardCard == null) {
        discardCard = hand.get(new Random().nextInt(4));
      }
      hand.remove(discardCard);
      discardDeck.addCard(discardCard);

      hand.add(pickupDeck.removeCard()); // pickup card
    }
  }

  /**
   * Gets a breif description of the player.
   *
   * @return A string containing the player's ID and hand.
   */
  @Override
  public String toString() {
    String returnString = "Player " + playerNumber + "Hand: ";
    for (Card card : hand) {
      returnString += " " + card;
    }
    return returnString;
  }

}
