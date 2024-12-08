package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * This class runs all the tests in the test suite.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CardTest.class, PackTest.class, DeckTest.class,
    PlayerTest.class, CardGameTest.class})
public class TestSuite {
// empty , as the class is just a holder
// for the annotations above
}