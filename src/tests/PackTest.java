package tests;

import org.junit.Test;

import static org.junit.Assert.*;

import main.Pack;
import main.Card;

import java.io.IOException;

/**
 * This class tests the Pack class.
 */
public class PackTest {

  /**
   * Test the constructor with an invalid file name.
   */
  @Test
  public void testInvalidFileName() {
    try {
      Pack pack = new Pack(2, "demoPacks/doesntExist.txt");
      fail("Should have thrown an IOException for invalid file name");
    } catch (IOException e) {
      // This is what we expect
    }
  }

  /**
   * Test the constructor with a valid pack.
   */
  @Test
  public void testConstructor() {
    try {
      Pack pack = new Pack(2, "demoPacks/demoPackValid.txt");
    } catch (Exception e) {
      fail("Should not have thrown any errors for a valid pack");
    }
  }

  /**
   * Test the getCards method.
   *
   * @throws IOException If the file is not found or cannot be read. This should not happen in this
   */
  @Test
  public void getCards() throws IOException {
    Pack pack = new Pack(2, "demoPacks/demoPackValid.txt");

    // Expect simply 16 cards from 1 to 16
    Card[] expectedCards = {new Card(1), new Card(2), new Card(3),
        new Card(4), new Card(5), new Card(6), new Card(7),
        new Card(8), new Card(9), new Card(10), new Card(11),
        new Card(12), new Card(13), new Card(14), new Card(15),
        new Card(16)};

    Card[] actualCards = pack.getCards();

    for (int i = 0; i < expectedCards.length; i++) {
      assertEquals(expectedCards[i].getDenomination(), actualCards[i].getDenomination());
    }
  }

  /**
   * Test the toString method.
   *
   * @throws IOException If the file is not found or cannot be read. This should not happen in this
   */
  @Test
  public void testToString() throws IOException {
    Pack pack = new Pack(2, "demoPacks/demoPackValid.txt");

    // Expect simply 16 cards from 1 to 16
    Card[] expectedCards = {new Card(1), new Card(2), new Card(3),
        new Card(4), new Card(5), new Card(6), new Card(7),
        new Card(8), new Card(9), new Card(10), new Card(11),
        new Card(12), new Card(13), new Card(14), new Card(15),
        new Card(16)};

    String expectedString = "This is a valid pack with the following 16 cards:\n1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16";
    String actualString = pack.toString();

    assertEquals(expectedString, actualString);
  }

  /**
   * Test the constructor with an invalid pack.
   * We will test, wrong length and NaN cards
   */
  @Test
  public void testInvalidPacks() throws IOException {

    // Test a pack with the wrong number of cards
    try {
      new Pack(4, "demoPacks/demoPackBadLength.txt");
      fail("Should have thrown an IOException for invalid pack length");
    } catch (IllegalArgumentException e) {
      // This is what we expect
    }

    // Test a pack with a NaN card
    try {
      new Pack(4, "demoPacks/demoPackNaN.txt");
      fail("Should have thrown an NumberFormatException for a number not being an int");
    } catch (IOException e) {
      fail("Should have thrown an NumberFormatException for a number not being an int");
    } catch (NumberFormatException e) {
      // This is what we expect
    }

    // Test a pack with a negative int card
    try {
      new Pack(4, "demoPacks/demoPackNegativeNums.txt");
      fail("Card class should have thrown an IllegalArgumentException for a number not being a positive int");
    } catch (IOException | NumberFormatException e) {
      fail("Card class should have thrown an IllegalArgumentException for a number not being a positive int");
    } catch (IllegalArgumentException e) {
      // This is what we expect

    }

  }
}