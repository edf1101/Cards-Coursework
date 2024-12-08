package tests;

import main.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * This class tests the CardGame class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class CardGameTest {

  /**
   * Test the validateInputSize method.
   */
  @Test
  public void testValidateInputSize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // Need to get the function since it is private
    Method method = CardGame.class.getDeclaredMethod("validateInputSize", String.class);
    method.setAccessible(true);

    assertEquals(1, method.invoke(null, "1")); // Test with valid input "1"
    assertEquals(-1, method.invoke(null, "-545")); // Test with very invalid input -545
    assertEquals(-1, method.invoke(null, "0")); // Test with invalid input "0"
    assertEquals(-1, method.invoke(null, "abc")); // Test with invalid input "abc"
  }

  /**
   * Test the constructor for the CardGame class.
   */
  @Test
  public void testConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

    // since constructor is private, we need to use reflection to test it
    Constructor<CardGame> constructor = CardGame.class.getDeclaredConstructor(IPack.class);
    constructor.setAccessible(true);

    // Test with a valid pack
    CardGame testGame = constructor.newInstance(createInstantWinPack());
    // Have loaded in a pack with 2 players, have not started it.

    // Test winner number is -1 on start
    Field winnerField = CardGame.class.getDeclaredField("winnerNumber");
    winnerField.setAccessible(true);
    assertEquals(-1, (int) ((AtomicInteger) winnerField.get(testGame)).get());

    // Test it has created the correct number of players
    Field playersField = CardGame.class.getDeclaredField("gamePlayers");
    playersField.setAccessible(true);
    Player[] players = (Player[]) playersField.get(testGame);
    assertEquals(2, players.length);

    // Test players are all instantiated
    for (Player player : players) {
      assertNotNull(player);
    }

    // Test it has created the correct number of decks
    Field decksField = CardGame.class.getDeclaredField("gameDecks");
    decksField.setAccessible(true);
    Deck[] decks = (Deck[]) decksField.get(testGame);
    assertEquals(2, decks.length);
  }

  /**
   * This tests the dealCards private method.
   */
  @Test
  public void testDealCards() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    // since constructor is private, we need to use reflection to test it
    Constructor<CardGame> constructor = CardGame.class.getDeclaredConstructor(IPack.class);
    constructor.setAccessible(true);

    // Create a new game with a simple pack,
    // This pack has 2 players with (4x (Card(1)) and 4x (Card(2)))
    // This pack has 2 decks with (4x (Card(3)) and 4x (Card(4)))
    int cardNums[] = {1, 2, 1, 2, 1, 2, 1, 2, 3, 4, 3, 4, 3, 4, 3, 4};
    Card[] cards = new Card[16];
    for (int i = 0; i < 16; i++) {
      cards[i] = new Card(cardNums[i]);
    }
    CardGame testGame = constructor.newInstance(new MockPack(2, cards));

    // The constructor automatically calls dealCards, so we can just check the state of the game

    // get the gameDecks field and gamePlayers field
    Field decksField = CardGame.class.getDeclaredField("gameDecks");
    decksField.setAccessible(true);
    Deck[] decks = (Deck[]) decksField.get(testGame);
    Field playersField = CardGame.class.getDeclaredField("gamePlayers");
    playersField.setAccessible(true);
    Player[] players = (Player[]) playersField.get(testGame);

    // Check the players have the correct cards
    // use reflection to get the hand field of each player
    Field handField = Player.class.getDeclaredField("hand");
    handField.setAccessible(true);
    CopyOnWriteArrayList<Card> player1Hand = (CopyOnWriteArrayList<Card>) handField.get(players[0]);
    CopyOnWriteArrayList<Card> player2Hand = (CopyOnWriteArrayList<Card>) handField.get(players[1]);
    assertEquals(4, player1Hand.size());
    assertEquals(4, player2Hand.size());
    for (Card card : player1Hand) {
      assertEquals(1, card.getDenomination());
    }
    for (Card card : player2Hand) {
      assertEquals(2, card.getDenomination());
    }

    // check the decks have the correct cards
    // use reflection to get the cards field of each deck
    Field cardsField = Deck.class.getDeclaredField("deck");
    cardsField.setAccessible(true);
    CopyOnWriteArrayList<Card> deck1Cards = (CopyOnWriteArrayList<Card>) cardsField.get(decks[0]);
    CopyOnWriteArrayList<Card> deck2Cards = (CopyOnWriteArrayList<Card>) cardsField.get(decks[1]);
    assertEquals(4, deck1Cards.size());
    assertEquals(4, deck2Cards.size());
    for (Card card : deck1Cards) {
      assertEquals(3, card.getDenomination());
    }
    for (Card card : deck2Cards) {
      assertEquals(4, card.getDenomination());
    }
  }

  /**
   * This tests the playGame method of the CardGame class,
   * in this case there should be an instant win.
   */
  @Test
  public void testPlayGameInstantWin() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    // since constructor is private, we need to use reflection to test it
    Constructor<CardGame> constructor = CardGame.class.getDeclaredConstructor(IPack.class);
    constructor.setAccessible(true);

    // Test with a valid pack
    CardGame testGame = constructor.newInstance(createInstantWinPack());
    // Have loaded in a pack with 2 players, have not started it.

    // run the private runGame method
    Method runGameMethod = CardGame.class.getDeclaredMethod("playGame");
    runGameMethod.setAccessible(true);
    runGameMethod.invoke(testGame);

    // test the winner number is correct
    Field winnerField = CardGame.class.getDeclaredField("winnerNumber");
    winnerField.setAccessible(true);
    assertEquals(1, (int) ((AtomicInteger) winnerField.get(testGame)).get());
  }

  /**
   * This tests the playGame method of the CardGame class,
   * in this case there should be a win after 1 round.
   */
  @Test
  public void testPlayGame1RoundWin() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
    // since constructor is private, we need to use reflection to test it
    Constructor<CardGame> constructor = CardGame.class.getDeclaredConstructor(IPack.class);
    constructor.setAccessible(true);

    // Test with a valid pack
    CardGame testGame = constructor.newInstance(create1RoundWinPack());
    // Have loaded in a pack with 2 players, have not started it.

    // run the private runGame method
    Method runGameMethod = CardGame.class.getDeclaredMethod("playGame");
    runGameMethod.setAccessible(true);
    runGameMethod.invoke(testGame);

    // test the winner number is correct
    Field winnerField = CardGame.class.getDeclaredField("winnerNumber");
    winnerField.setAccessible(true);
    assertEquals(1, (int) ((AtomicInteger) winnerField.get(testGame)).get());

    // test all players have 4 cards still.
    // get the gameDecks field and gamePlayers field
    Field decksField = CardGame.class.getDeclaredField("gameDecks");
    decksField.setAccessible(true);
    Deck[] decks = (Deck[]) decksField.get(testGame);
    Field playersField = CardGame.class.getDeclaredField("gamePlayers");
    playersField.setAccessible(true);
    Player[] players = (Player[]) playersField.get(testGame);

    // Check the players have the correct cards
    // use reflection to get the hand field of each player
    Field handField = Player.class.getDeclaredField("hand");
    handField.setAccessible(true);
    CopyOnWriteArrayList<Card> player1Hand = (CopyOnWriteArrayList<Card>) handField.get(players[0]);
    CopyOnWriteArrayList<Card> player2Hand = (CopyOnWriteArrayList<Card>) handField.get(players[1]);
    assertEquals(4, player1Hand.size());
    assertEquals(4, player2Hand.size());

    // check the decks have the correct cards
    // use reflection to get the cards field of each deck
    Field cardsField = Deck.class.getDeclaredField("deck");
    cardsField.setAccessible(true);
    CopyOnWriteArrayList<Card> deck1Cards = (CopyOnWriteArrayList<Card>) cardsField.get(decks[0]);
    CopyOnWriteArrayList<Card> deck2Cards = (CopyOnWriteArrayList<Card>) cardsField.get(decks[1]);
    assertEquals(8, deck1Cards.size() + deck2Cards.size());

  }

  /**
   * This function creates a mock pack that would lead to an instant win.
   *
   * @return the pack object created.
   */
  private MockPack createInstantWinPack() {
    int cardNums[] = {1, 3, 1, 4, 1, 5, 1, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    Card[] cards = new Card[16];
    for (int i = 0; i < 16; i++) {
      cards[i] = new Card(cardNums[i]);
    }
    int n = 2;
    return new MockPack(n, cards);
  }

  /**
   * This function creates a mock pack that would lead to an win after 1 round.
   *
   * @return the pack object created.
   */
  private MockPack create1RoundWinPack() {
    int cardNums[] = {2, 3, 1, 4, 1, 5, 1, 6, 1, 8, 9, 10, 11, 12, 13, 14};
    Card[] cards = new Card[16];
    for (int i = 0; i < 16; i++) {
      cards[i] = new Card(cardNums[i]);
    }
    int n = 2;
    return new MockPack(n, cards);
  }
}
