package main;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    CardGame game = new CardGame(2,"demoPacks/demoPackValid.txt");
    Player[] players = game.getPlayers();

    System.out.println("Player 1: " + players[0].toString());
    System.out.println("Player 2: " + players[1].toString());

    System.out.println("Deck 1: " + game.getDecks()[0].toString());
    System.out.println("Deck 2: " + game.getDecks()[1].toString());

  }
}
