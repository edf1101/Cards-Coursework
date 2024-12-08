# Software Development (ECM2414) Coursework
_This is the ECM2414 Software Development Pair Programming Coursework_


Note these commands must be run from the root Submission directory, ie the directory containing 'cards.jar' and 'cardsTest' folder.
Given this README is in the 'cardsTest' you should be able to get there with
```bash
cd ..
```

To run the application via the JAR File (macOS / Linux / Windows) use the following command from within the `Submission` folder:
```bash
java -jar cards.jar
```

To run the tests run from within the `Submission` folder using the following command (macOS / Linux):
```bash
java -cp cardsTest/lib/junit-4.13.2.jar:cardsTest/lib/hamcrest-core-1.3.jar:cardsTest org.junit.runner.JUnitCore tests.TestSuite
```

To run the tests on Windows use the following command from within the `Submission` folder:
```bash
java -cp "cardsTest\lib\junit-4.13.2.jar;cardsTest\lib\hamcrest-core-1.3.jar;cardsTest" org.junit.runner.JUnitCore tests.TestSuite
```