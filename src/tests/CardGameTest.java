package tests;

import main.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

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
}
