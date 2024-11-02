package tests;

import org.junit.Before;
import org.junit.Test;

import main.Card;

import static org.junit.Assert.*;

public class CardTest {

  Card validCard;

  /**
   * Set up a valid card for testing.
   *
   * @throws Exception If the card cannot be created.
   */
  @Before
  public void setUp() throws Exception {
    validCard = new Card(5);
    assertTrue(true); // If we get here, the card was created successfully.
  }

  /**
   * Test that the denomination is correctly set, we set it as 5.
   */
  @Test
  public void getDenomination() {
    assertEquals(5, validCard.getDenomination());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("Card of Value: 5", validCard.toString());
  }


}