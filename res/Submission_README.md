# Software Development (ECM2414) Coursework
_This is the ECM2414 Software Development Pair Programming Coursework_

## Introduction
This is the ECM2414 Software Development Pair Programming Coursework. The purpose is to create
a threaded card game where multiple players compete to have a hand of 4 same cards.

## How to Run
Note these commands must be run from the root Submission directory, ie the directory containing 'cards.jar' and 'cardsTest' folder.
Given this README is in the 'cardsTest' you should be able to get there with
```bash
cd ..
```

Regarding JUnit, this uses JUnit 4.13.2 and Hamcrest 1.3. These are included in the 'cardsTest/lib' folder.
You shouldn't need to install these or modify any of the below commands to run the tests but if you do those are the versions.

To run the application via the JAR File (macOS / Linux / Windows) use the following command from within the root
(containing cards.jar and cardsTest.zip) folder:
```bash
java -jar cards.jar
```

To run the tests run from within the root (containing cards.jar and cardsTest.zip) folder using the following command (macOS / Linux):
```bash
java -cp cardsTest/lib/junit-4.13.2.jar:cardsTest/lib/hamcrest-core-1.3.jar:cardsTest org.junit.runner.JUnitCore tests.TestSuite
```

To run the tests on Windows use the following command from within the root (containing cards.jar and cardsTest.zip) folder:
```bash
java -cp "cardsTest\lib\junit-4.13.2.jar;cardsTest\lib\hamcrest-core-1.3.jar;cardsTest" org.junit.runner.JUnitCore tests.TestSuite
```

## Details
### Authors
This project was developed By Students 730003140 & 7300049916
### License
This falls under the MIT License.