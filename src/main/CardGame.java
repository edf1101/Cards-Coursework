package main;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This is the card game class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class CardGame {

  private final Deck[] gameDecks;
  private final Player[] gamePlayers;

  private final Card[] gameCards;

  private AtomicInteger winnerNumber = new AtomicInteger(-1);

  /**
   * Constructor for the CardGame class.
   *
   * @param n    number of players.
   * @param path Path to deck file.
   * @throws IOException           If the file is not found or cannot be read.
   * @throws NumberFormatException If the file contains invalid card values.
   */
  private CardGame(int n, String path) throws IOException {
    // Read the pack and get the cards from it.
    Pack gamePack = new Pack(n, path);
    gameCards = gamePack.getCards();

    // Create the decks & player objects.
    gameDecks = new Deck[n];
    gamePlayers = new Player[n];

    // Need to create the decks first as the player constructors reference them.
    for (int i = 0; i < n; i++) {
      gameDecks[i] = new Deck(i);
    }

    for (int i = 0; i < n; i++) {
      // instantiate the player and start the thread
      gamePlayers[i] = new Player(i + 1, gameDecks[i], gameDecks[(i + 1) % n], winnerNumber);
    }

    dealCards();
  }

  /**
   * Deal the cards to the players and decks.
   */
  private void dealCards() {

    // deal cards to the players.
    int n = gamePlayers.length;
    for (int i = 0; i < (n * 4); i++) {
      gamePlayers[i % n].addCard(gameCards[i]);
    }

    // deal cards to the decks
    for (int i = 0; i < (n * 4); i++) {
      gameDecks[(i % n)].addCard(gameCards[(n * 4) + i]);
    }
  }

  /**
   * This method plays the game.
   *
   * @return The player number of the winner.
   */
  private int playGame() {
    // Check if any player has won instantly
    for (Player player : gamePlayers) {
      if (player.checkWin()) {
        return player.getPlayerNumber();
      }
    }

    // start all player threads
    for (Player player : gamePlayers) {
      Thread playerThread = new Thread(player);
      playerThread.start();
    }

    while (winnerNumber.get() == -1) {
      // wait for the game to finish
    }

    return winnerNumber.get();
  }


  /**
   * This method runs the threaded game.
   *
   * @param args command-line arguments (unused)
   * @throws IOException If the file is not found or cannot be read.
   */
  public static void main(String[] args) throws IOException {
    int n = 22;
    String filename = "testPack.txt";

    int tot = 0;
    int c = 10;
    for (int i = 0; i < c; i++) {

      CardGame game = new CardGame(n, filename);
      game.playGame();

      int res = game.winnerNumber.get();
      System.out.println("Winner: " + res);
      tot += res;
    }
    float avg = (float) tot / (float) c;
    System.out.println("Avg " + avg);

  }

}

