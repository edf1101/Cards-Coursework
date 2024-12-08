package tests;

import main.Card;
import main.Deck;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

/**
 * This class tests the Deck class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class DeckTest {


  @Before
  public void setUp() {
    // Delete any test files from previous tests
    java.io.File file = new java.io.File("gameData/deck101.txt");
    file.delete();
  }

  /**
   * Test adding a card to the deck and checking if it was added.
   */
  @Test
  public void addCard() {
    Deck deck = new Deck(1);
    Card card = new Card(1);
    deck.addCard(card);

    ArrayList<Card> deckCards = HelperFunctions.getDeckCards(deck);
    assert deckCards != null;
    assertEquals(1, deckCards.size());
    assertEquals(card, deckCards.get(0));
    assertEquals(1, card.getDenomination());
  }

  /**
   * Test removing cards to the deck and checking if they were removed.
   */
  @Test
  public void removeCard() {
    Deck deck = new Deck(1);
    Card card1 = new Card(1);
    Card card2 = new Card(2);

    // as you add cards to the bottom the card array now is [card2, card1]
    deck.addCard(card1);
    deck.addCard(card2);

    // we remove cards from the top of the deck so the card returned should be card1
    Card removedCard = deck.removeCard();
    assertEquals(card1, removedCard);
    assertEquals(1, removedCard.getDenomination());

    ArrayList<Card> deckCards = HelperFunctions.getDeckCards(deck);
    assert deckCards != null;
    assertEquals(1, deckCards.size());
    assertEquals(card2, deckCards.get(0));
    assertEquals(2, card2.getDenomination());

    // Test removing from an empty deck. Should return null.
    deck = new Deck(2);
    assertNull(deck.removeCard());


  }

  /**
   * Test getting the deckId of the deck.
   */
  @Test
  public void getDeckId() {
    Deck deck = new Deck(1);
    assertEquals(1, deck.getDeckId());
  }

  /**
   * Test the toString method of the deck.
   */
  @Test
  public void testToString() {
    // Test toString with normal deck
    Deck testDeck = new Deck(1);
    Card card1 = new Card(1);
    Card card2 = new Card(2);
    Card card3 = new Card(3);
    Card card4 = new Card(4);

    testDeck = new Deck(1);
    testDeck.addCard(card1);
    testDeck.addCard(card2);
    testDeck.addCard(card3);
    testDeck.addCard(card4);

    String expected = "Hand of deck 1:\n4,3,2,1";
    assertEquals(expected, testDeck.toString());

    // Test ToString with empty deck
    testDeck = new Deck(2);
    expected = "Hand of deck 2:";
    assertEquals(expected, testDeck.toString());
  }

  /**
   * Test the canTakeCard method of the deck.
   * This method is used to check if the deck has >= 4 cards ie can draw a card from it.
   */
  @Test
  public void testCanDrawCard() {

    // create a deck and add 4 cards
    Deck deck = new Deck(1);
    deck.addCard(new Card(1));
    deck.addCard(new Card(2));
    deck.addCard(new Card(3));
    deck.addCard(new Card(4));

    assertTrue(deck.canDrawCard());
    deck.removeCard();
    assertFalse(deck.canDrawCard());

    // Test edge case with 0 cards
    deck = new Deck(2);
    assertFalse(deck.canDrawCard());
  }

  /**
   * Test the canTakeCard method of the deck.
   * This method is used to check if the deck has >= 4 cards ie can draw a card from it.
   */
  @Test
  public void testCanGiveCard() {

    // create a deck and add 4 cards
    Deck deck = new Deck(1);
    deck.addCard(new Card(1));
    deck.addCard(new Card(2));
    deck.addCard(new Card(3));
    deck.addCard(new Card(4));
    deck.addCard(new Card(5));

    assertFalse(deck.canGiveCard()); // now length is 5 so can't give a card
    deck.removeCard();

    assertTrue(deck.canGiveCard()); // now length is 4 so can give a card (EDGE CASE)

    deck.removeCard();
    assertTrue(deck.canGiveCard()); // now length is 3 so can give a card

    // Test extreme case with 0 cards
    deck = new Deck(2);
    assertTrue(deck.canGiveCard());

    // test edge case of more than full deck
    for (int i = 0; i < 5; i++) {
      deck.addCard(new Card(i));
    }
    assertFalse(deck.canGiveCard());
  }

  /**
   * Test the logDeck method of the deck.
   */
  @Test
  public void testDeckLogNormal() {
    // Create an example deck to log
    Deck deck = new Deck(101);
    deck.dealCard(new Card(1));
    deck.dealCard(new Card(2));
    deck.dealCard(new Card(3));
    deck.logDeck();

    // should now be saved to file - read it to check
    String expectedPath = "gameData/deck101.txt";
    String expectedContents = "Deck 101 contents: 1 2 3 ";

    // assert file exists
    assertTrue(new java.io.File(expectedPath).exists());

    // read file into a string
    String actualContents = "";
    try {
      java.io.File file = new java.io.File(expectedPath);
      java.util.Scanner scanner = new java.util.Scanner(file);
      while (scanner.hasNextLine()) {
        actualContents += scanner.nextLine();
      }
      scanner.close();
    } catch (java.io.FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals(expectedContents, actualContents);

  }
}