#! /bin/bash

# Compile the submission
rm -rf Submission.zip
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
cp res/Submission_README.md Submission/cardsTest/README.md
cp -r ./lib Submission/cardsTest/
cp res/Report.pdf Submission/
cd Submission
cd cardsTest
zip -r ../cardsTest.zip .
cd ..
rm -rf ./cardsTest
cd ..
zip -r Submission.zip ./Submission
rm -rf ./Submission

# check the submission
unzip -o Submission.zip -d ./
cd Submission
unzip -o cardsTest.zip -d cardsTest
java -cp cardsTest/lib/junit-4.13.2.jar:cardsTest/lib/hamcrest-core-1.3.jar:cardsTest org.junit.runner.JUnitCore tests.TestSuite
java -jar cards.jar

# clean up
cd ..
rm -rf Submission
rm -rf Submission.zip
