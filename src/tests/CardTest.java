package tests;

import main.Card;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the Card class.
 *
 * @author 730003140 & 7300049916
 * @version 1.0
 */
public class CardTest {

  private Card validCard;

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
  public void testGetDenomination() {
    assertEquals(5, validCard.getDenomination());
  }

  /**
   * Test the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("Card of Value: 5", validCard.toString());
  }

  /**
   * This tests that the card cannot be created with a negative denomination.
   */
  @Test
  public void testNegativeDenomination() {
    try {
      Card invalidCard = new Card(-1);
      fail("Should have thrown an exception");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

}