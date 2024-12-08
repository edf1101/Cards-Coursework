# Software Development (ECM2414) Coursework

## Introduction
This is the ECM2414 Software Development Pair Programming Coursework. The purpose is to create
a threaded card game where multiple players compete to have a hand of 4 same cards. 
The specification can be found [here](ECM2414-CA-2024.pdf). 

## Submission
The actual submission is in the `Submission` folder. As per the specification, the submission
contains the following:
### Usage

To get submission ready (complete JAR, cardsTest folder etc) run the following command:
```bash
mkdir "Submission"
mkdir "Submission/cardsTest"
mkdir "Submission/cardsTest/main"
mkdir "Submission/cardsTest/tests"
mkdir "Submission/cardsTest/lib"
javac -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:out -d out src/main/*.java src/tests/*.java
jar cvmf MANIFEST.MF Submission/cards.jar -C out/ . -C src/main/  . 
cp -r ./out/main/ ./Submission/cardsTest/main
cp -r ./out/tests/ ./Submission/cardsTest/tests
cp -r ./src/main/ ./Submission/cardsTest/main
cp -r ./src/tests/ ./Submission/cardsTest/tests
cp Submission_README.md Submission/cardsTest/README.md
cp -r ./lib Submission/cardsTest/
cp res/Report.docx Submission/

```
You can then run the JAR executable using the following command:
```bash
java -jar Submission/cards.jar
```
or run directly using the following command:
```bash
java -cp cards.jar main.CardGame
```

### Contents
#### The Code Part:
Your code part includes the following two files (i.e., cards.jar and
cardsTest.zip).

• A copy of your finished classes in an executable jar file named cards.jar. The jar file
should include both the bytecode (.class) and source files (.java) of your
submission.

• A copy of your finished classes, and associated test classes and test suites, and
any supporting files, in a zip file named cardsTest.zip. The zip file should include
both the bytecode (.class) and source files (.java) of your submission (plus any
testing files the tests may rely upon), and a README file, detailing how to run your
test suite.

#### The Report Part 
A write-up of the project and stages of development

## Details
### Authors
This project was developed by: Ed Fillingham and Andrei Lariu-Rusu
### License
Since this is a university courswork project the license is unknown and left blank.