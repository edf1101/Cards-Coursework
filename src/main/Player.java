package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This is the thread safe player class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class Player implements Runnable {

  private final int playerNumber;
  private final CopyOnWriteArrayList<Card> hand = new CopyOnWriteArrayList<>();
  private final Deck pickupDeck;
  private final Deck discardDeck;
  private final AtomicInteger winnerNumber;

  private String log = "";

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
    hand.add(card);
  }

  /**
   * Check if this player has won or not. The edge case of an empty hand is handled as False.
   *
   * @return True if the player has won, false otherwise.
   */
  public synchronized boolean checkWin() {

    if (hand.size() != 4) { // Check that hand must be full to win
      return false;
    }

    // Checks if there are any cards that are different. If so, the player has not won.
    for (Card card : hand) {
      if (card.getDenomination() != hand.get(0).getDenomination()) {
        return false;
      }
    }
    // If the player has got here, then all cards are the same, so they have won.
    System.out.println("Player " + playerNumber + " has won!");
    winnerNumber.set(playerNumber);
    return true;
  }

  /**
   * This function is called when the thread starts, it runs the game loop and exits
   * when a player has won.
   */
  public void run() {
    // Write initial hand to log
    log += "Player " + playerNumber + "'s initial hand is: ";
    for (Card card : hand) {
      log += card.getDenomination() + " ";
    }
    log += "\n";

    while (winnerNumber.get() == -1) {
      if (checkWin()) {
        break;
      }
      takeTurn();
    }

    // Write the winner to the log
    if (winnerNumber.get() == playerNumber) {
      log += "Player " + playerNumber + " wins\n";
    } else {
      log += "Player " + winnerNumber.get() + " has informed player "
          + playerNumber + " that player " + winnerNumber.get() + " has won\n";
    }
    log += "Player " + playerNumber + " exits\n";
    log += "Player " + playerNumber + "'s final hand is: ";
    for (Card card : hand) {
      log += card.getDenomination() + " ";
    }
    discardDeck.logDeck(); // each player logging their discard deck will mean all decks are logged

    writeLog();
  }

  /**
   * This function writes the player's log to a file.
   * This will all be done at once to avoid the log being written to multiple times which is slow.
   */
  public void writeLog() {
    String path = "gameData/player" + playerNumber + ".txt";
    File f = new File(path);
    f.getParentFile().mkdirs(); // make sure the directory exists
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(path));
      writer.write(log);
      writer.close();
    } catch (IOException e) {
      // This should never happen since we created the file just before
      System.out.println("Error creating log file for player " + playerNumber);
    }
  }

  /**
   * This function contains the code run each turn a player takes.
   */
  private void takeTurn() {
    Card drawnCard = null;
    Card discardCard = null;
    synchronized (Player.class) { // To keep in sync with all other Player class instances.
      if (!pickupDeck.canDrawCard() || !discardDeck.canGiveCard() || hand.size() != 4) {
        return;
      }

      // select card to discard
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

      drawnCard = pickupDeck.removeCard();
      hand.add(drawnCard); // pickup card
    }
    // Write to log
    log += "Player " + playerNumber + " draws a " + drawnCard.getDenomination()
        + " from deck " + pickupDeck.getDeckId() + "\n";
    log += "Player " + playerNumber + " discards " + discardCard + " to deck "
        + discardDeck.getDeckId() + "\n";
    log += "Player " + playerNumber + "'s current hand is: ";
    for (Card card : hand) {
      log += card.getDenomination() + " ";
    }
    log += "\n";

  }

  /**
   * Gets a breif description of the player.
   *
   * @return A string containing the player's ID and hand.
   */
  @Override
  public String toString() {
    String returnString = "Player " + playerNumber + " Hand: ";
    for (Card card : hand) {
      returnString += card.getDenomination() + " ";
    }
    return returnString;
  }

}
