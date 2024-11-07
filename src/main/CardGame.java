package main;

import java.io.IOException;

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

  /**
   * Constructor for the CardGame class.
   *
   * @param n    number of players.
   * @param path Path to deck file.
   * @throws IOException           If the file is not found or cannot be read.
   * @throws NumberFormatException If the file contains invalid card values.
   */
  public CardGame(int n, String path) throws IOException {
    // Read the pack and get the cards from it.
    Pack gamePack = new Pack(n, path);
    gameCards = gamePack.getCards();

    // Create the decks & player objects.
    gameDecks = new Deck[n];
    gamePlayers = new Player[n];
    for (int i = 0; i < n; i++) {
      gameDecks[i] = new Deck(i);
    }

    for (int i = 0; i < n; i++) {
      gamePlayers[i] = new Player(i, gameDecks[i], gameDecks[(i + 1) % n]);
    }

    // deal the cards
    dealCards();
  }

  /**
   * Deal the cards to the players and decks.
   */
  private void dealCards() {

    // deal cards to the players.
    int n = gamePlayers.length;
    for (int i = 0; i < (n*4); i++) {
      gamePlayers[i % n].addCard(gameCards[i]);
    }

    // deal cards to the decks
    for (int i = 0; i < (n*4); i++) {
      gameDecks[(i % n)].addCard(gameCards[(n * 4) + i]);
    }
  }

  public Player[] getPlayers(){
    return gamePlayers;
  }

  public Deck[] getDecks(){
    return gameDecks;
  }
}
