package tests;


import org.junit.Before;
import org.junit.Test;

import main.Deck;
import main.Player;
import main.Card;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * This class tests the Player class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class PlayerTest {

  private Deck testPickupDeck = new Deck(1);
  private Deck testDiscardDeck = new Deck(2);

  private AtomicInteger testWinnerNumber = new AtomicInteger(-1);

  private Player testPlayer;

  /**
   * This method is run before each test to set up the test environment.
   */
  @Before
  public void setUp() {
    // Create some example player decks.

    testPlayer = new Player(101, testPickupDeck, testDiscardDeck, testWinnerNumber);

    // Delete any test files from previous tests
    java.io.File file = new java.io.File("gameData/player101.txt");
    file.delete();

  }

  /**
   * This method tests the getPlayerNumber method.
   */
  @Test
  public void testGetPlayerNumber() {
    // Test the getPlayerNumber method
    // recreate the testPlayer since we are testing something that is changed in the constructor

    testPlayer = new Player(1, testPickupDeck, testDiscardDeck, testWinnerNumber);
    assertEquals(1, testPlayer.getPlayerNumber());

    // test extreme case, large player number
    testPlayer = new Player(1000000, testPickupDeck, testDiscardDeck, testWinnerNumber);
    assertEquals(1000000, testPlayer.getPlayerNumber());

    // test edge case 0
    testPlayer = new Player(0, testPickupDeck, testDiscardDeck, testWinnerNumber);
    assertEquals(0, testPlayer.getPlayerNumber());
  }

  /**
   * This method tests the addCard method.
   */
  @Test
  public void testAddCard() {
    // Test the addCard method
    assertEquals(0, HelperFunctions.getPlayerCards(testPlayer).size());

    testPlayer.addCard(new Card(5));
    assertEquals(1, HelperFunctions.getPlayerCards(testPlayer).size());
    assertEquals(5, HelperFunctions.getPlayerCards(testPlayer).get(0).getDenomination());

    // test adding multiple cards
    testPlayer.addCard(new Card(6));
    assertEquals(2, HelperFunctions.getPlayerCards(testPlayer).size());
    assertEquals(6, HelperFunctions.getPlayerCards(testPlayer).get(1).getDenomination());
  }

  /**
   * This method tests the checkWin method.
   */
  @Test
  public void testCheckWin() {
    // Test the edge case at the start where the player has no cards
    assertEquals(HelperFunctions.getPlayerCards(testPlayer).size(), 0);
    assertFalse(testPlayer.checkWin());

    // Test the case where player has all equal cards, but not 4 of them.
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    assertFalse(testPlayer.checkWin());

    // test if a player has 4 cards but they are not all the same
    HelperFunctions.resetPlayerHand(testPlayer);
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(2));
    testPlayer.addCard(new Card(3));
    testPlayer.addCard(new Card(4));
    assertFalse(testPlayer.checkWin());

    // test if a player has 4 cards and they are all the same
    HelperFunctions.resetPlayerHand(testPlayer);
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    assertTrue(testPlayer.checkWin());

    // test if a player has more than 4 cards and they are all the same
    HelperFunctions.resetPlayerHand(testPlayer);
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    assertFalse(testPlayer.checkWin());
  }

  /**
   * This method tests the writeLog method.
   * The write log method simply writes the 'log' field to a file.
   * This test will check that functionality, not the card logging itself.
   */
  @Test
  public void testWriteLog() {
    // Test writing an empty log
    String log = HelperFunctions.getPlayerLog(testPlayer);
    assertEquals("", log);
    testPlayer.writeLog();
    String actualContents = HelperFunctions.readFileContents("gameData/player101.txt");
    assertEquals("", actualContents);

    // Test writing a non-empty log
    HelperFunctions.setPlayerLog(testPlayer, "Test123");
    assertEquals("Test123", HelperFunctions.getPlayerLog(testPlayer));
    testPlayer.writeLog();
    actualContents = HelperFunctions.readFileContents("gameData/player101.txt");
    assertEquals("Test123", actualContents);

    // Test writing a multi-line log
    String expectedValue = "Test123\nTest456\nTest789";
    HelperFunctions.setPlayerLog(testPlayer, expectedValue);
    assertEquals(expectedValue, HelperFunctions.getPlayerLog(testPlayer));
    testPlayer.writeLog();
    actualContents = HelperFunctions.readFileContents("gameData/player101.txt");
    assertEquals(expectedValue, actualContents);

  }

  /**
   * This method tests the toString method.
   */
  @Test
  public void testToString() {
    // The toString method should contain the player number and the cards in the hand.

    // Test the edge case where the player has no cards
    String expectedString = "Player 101 Hand: ";
    String actualString = testPlayer.toString();
    assertEquals(expectedString, actualString);

    // test normal case where player has cards
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(2));
    testPlayer.addCard(new Card(3));
    testPlayer.addCard(new Card(4));
    expectedString = "Player 101 Hand: 1 2 3 4 "; // note the space at the end
    actualString = testPlayer.toString();
    assertEquals(expectedString, actualString);
  }


  /**
   * This method tests the takeTurn method when it can take a turn.
   * This will give a different card when it has multiple preferred ones.
   * Since it is a private method we will use reflection to test it.
   * This test tests that if a player cannot take a turn it does not
   */
  @Test
  public void testTakeTurnCanGo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // get the private method using reflection.
    Method takeTurn = Player.class.getDeclaredMethod("takeTurn");
    takeTurn.setAccessible(true);
    // run using takeTurn.invoke(testPlayer);

    // set up a player with 4 cards, where 3 are preffered and 1 is different.
    // Should give away the different card.

    // fill pickup deck so testPlayer can take a card
    testPickupDeck.dealCard(new Card(5));
    testPickupDeck.dealCard(new Card(5));
    testPickupDeck.dealCard(new Card(5));
    testPickupDeck.dealCard(new Card(5));


    // fill player deck with 4 cards
    testPlayer.addCard(new Card(testPlayer.getPlayerNumber()));
    testPlayer.addCard(new Card(testPlayer.getPlayerNumber()));
    testPlayer.addCard(new Card(testPlayer.getPlayerNumber()));
    testPlayer.addCard(new Card(999));

    // after taking turn, player should have 4 cards,
    // should have Card(5) in hand, and Card(999) in discard
    takeTurn.invoke(testPlayer);

    assertEquals(4, HelperFunctions.getPlayerCards(testPlayer).size()); // check player has 4 cards
    assertEquals(3, HelperFunctions.getDeckCards(testPickupDeck).size());
    assertEquals(1, HelperFunctions.getDeckCards(testDiscardDeck).size());
    assertEquals(999, HelperFunctions.getDeckCards(testDiscardDeck).get(0).getDenomination());
  }

  /**
   * This test checks the correct output after running the takeTurn method as done
   * in the testTakeTurnCanGo test.
   *
   * @throws InvocationTargetException if the method cannot be invoked.
   * @throws NoSuchMethodException     if the method does not exist.
   * @throws IllegalAccessException    if the method cannot be accessed.
   */
  @Test
  public void testTakeTurnLog() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    // run the testTakeTurnCanGo test to set up the player with 4 cards
    testTakeTurnCanGo();
    // according to the test, the player should draw a 5 and discard a 999 and have 3 other 101 cards
    String expectedLog = "Player 101 draws a 5 from deck 1\n" +
        "Player 101 discards Card of Value: 999 to deck 2\n" +
        "Player 101's current hand is: 101 101 101 5 \n";
    String actualLog = HelperFunctions.getPlayerLog(testPlayer);
    assertEquals(expectedLog, actualLog);

  }

  /**
   * This method tests the takeTurn method when it can take a turn.
   * This will give a random card when it has 4 the same.
   * Since it is a private method we will use reflection to test it.
   * This test tests that if a player cannot take a turn it does not
   */
  @Test
  public void testTakeTurnCanGoGiveRandom() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // get the private method using reflection.
    Method takeTurn = Player.class.getDeclaredMethod("takeTurn");
    takeTurn.setAccessible(true);
    // run using takeTurn.invoke(testPlayer);

    // set up a player with 4 cards, where 4 are same so it should give random.

    // fill pickup deck so testPlayer can take a card
    testPickupDeck.dealCard(new Card(5));
    testPickupDeck.dealCard(new Card(5));
    testPickupDeck.dealCard(new Card(5));
    testPickupDeck.dealCard(new Card(5));

    // fill player deck with 4 cards
    int cardVal = testPlayer.getPlayerNumber();
    testPlayer.addCard(new Card(cardVal));
    testPlayer.addCard(new Card(cardVal));
    testPlayer.addCard(new Card(cardVal));
    testPlayer.addCard(new Card(cardVal));

    // after taking turn, player should have 4 cards,
    // should have Card(5) in hand, and Card(cardVal) in discard
    takeTurn.invoke(testPlayer);

    assertEquals(4, HelperFunctions.getPlayerCards(testPlayer).size()); // check player has 4 cards
    assertEquals(3, HelperFunctions.getDeckCards(testPickupDeck).size());
    assertEquals(1, HelperFunctions.getDeckCards(testDiscardDeck).size());
    assertEquals(cardVal, HelperFunctions.getDeckCards(testDiscardDeck).get(0).getDenomination());

  }

  /**
   * This method tests the takeTurn method when it can't take a turn.
   * Since it is a private method we will use reflection to test it.
   * This test tests that if a player cannot take a turn it does not
   */
  @Test
  public void testTakeTurnCantGo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // get the private method using reflection.
    Method takeTurn = Player.class.getDeclaredMethod("takeTurn");
    takeTurn.setAccessible(true);
    // run using takeTurn.invoke(testPlayer);

    // Set up a valid player hand and 2 empty decks either side
    ArrayList<Card> pickupCards = HelperFunctions.getDeckCards(testPickupDeck);
    ArrayList<Card> discardCards = HelperFunctions.getDeckCards(testDiscardDeck);
    pickupCards.clear();
    discardCards.clear();
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(2));
    testPlayer.addCard(new Card(3));
    testPlayer.addCard(new Card(4));
    assertEquals(4, HelperFunctions.getPlayerCards(testPlayer).size());
    assertEquals(0, pickupCards.size());
    assertEquals(0, discardCards.size());

    // run the takeTurn method
    takeTurn.invoke(testPlayer);
    // These tests should be the same as before
    assertEquals(4, HelperFunctions.getPlayerCards(testPlayer).size());
    assertEquals(0, pickupCards.size());
    assertEquals(0, discardCards.size());
    // check player hand is still the same
    ArrayList<Card> playerCards = HelperFunctions.getPlayerCards(testPlayer);
    assertEquals(1, playerCards.get(0).getDenomination());
    assertEquals(2, playerCards.get(1).getDenomination());
    assertEquals(3, playerCards.get(2).getDenomination());
    assertEquals(4, playerCards.get(3).getDenomination());

    // Test edge case where player has no cards
    HelperFunctions.resetPlayerHand(testPlayer);
    assertEquals(0, HelperFunctions.getPlayerCards(testPlayer).size());
    takeTurn.invoke(testPlayer);
    assertEquals(0, HelperFunctions.getPlayerCards(testPlayer).size());
    assertEquals(0, pickupCards.size());
    assertEquals(0, discardCards.size());


  }

  /**
   * This method tests the run method, when it instantly wins.
   */
  @Test
  public void testRunInstantWin() throws IOException{

    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.addCard(new Card(1));
    testPlayer.run();

    // Test log file contents
    String logContents = HelperFunctions.getPlayerLog(testPlayer);
    String expectedLog = "Player 101's initial hand is: 1 1 1 1 \n" +
        "Player 101 wins\n" +
        "Player 101 exits\n" +
        "Player 101's final hand is: 1 1 1 1 ";
    assertEquals(expectedLog, logContents);
  }

}