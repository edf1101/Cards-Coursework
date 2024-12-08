package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
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

  private final AtomicInteger winnerNumber;

  /**
   * Constructor for the CardGame class.
   *
   * @param inputPack The pack to play with.
   */
  private CardGame(IPack inputPack) {

    winnerNumber = new AtomicInteger(-1);

    // Read the pack and get the cards from it.
    gameCards = inputPack.getCards();
    int n = inputPack.getPlayerCount();

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
      gameDecks[(i % n)].dealCard(gameCards[(n * 4) + i]);
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
   * This checks if a String represents a valid integer for the player count of the game.
   *
   * @param n The string to check.
   * @return The integer if valid, else -1
   */
  private static int validateInputSize(String n) {
    try {
      int num = Integer.parseInt(n);
      if (num < 1) {
        return -1;
      }
      return num;
    } catch (NumberFormatException e) {
      return -1;
    }
  }


  /**
   * This method runs the threaded game.
   *
   * @param args command-line arguments (unused)
   * @throws IOException If the file is not found or cannot be read.
   */
  public static void main(String[] args) throws IOException {

    // Get the number of players from input.
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of players: ");
    int n = validateInputSize(scanner.nextLine());
    while (n == -1) {
      System.out.println("Invalid input. Please enter a positive integer: ");
      n = validateInputSize(scanner.nextLine());
    }

    // get the file path from input.
    System.out.println("Enter the file path for the pack: ");
    String path = scanner.nextLine();

    // Create the game and play it.
    try {
      CardGame game = new CardGame(new Pack(n, path));
      game.playGame();
    } catch (FileNotFoundException e) {
      System.out.println("File not found. Exiting...");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}

