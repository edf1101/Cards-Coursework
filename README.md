# Software Development (ECM2414) Coursework

<img src="res/readme_header.png" alt="drawing" width="650"/>

![Tests stats](https://img.shields.io/badge/tests-tests%20failed%3A%200%2C%20passed%3A%2030-success)

![Tests stats](https://img.shields.io/badge/code%20coverage-89%25%20of%20lines-brightgreen)

## Introduction
This is the ECM2414 Software Development Pair Programming Coursework. The purpose is to create
a threaded card game where multiple players compete to have a hand of 4 same cards. 
The specification can be found [here](res/ECM2414-CA-2024.pdf). 

## Submission
The actual submission is in the `Submission` folder. As per the specification, the submission
contains the following:
### Usage
#### Submission Usage
To get submission ready (complete JAR, cardsTest folder etc) run the following command:
```bash
chmod +x ./tools/Submit.sh 
./tools/Submit.sh 
```

To check the validity of a submission (run tests then code) run the following command:
```bash
chmod +x ./tools/CheckSubmission.sh
./tools/checkSubmission.sh
```

The submission contains a README within the cardTest.zip archive that details how to run the software.
#### General Usage
If you want to run the code from source you can from this root directory.

First compile the code:
```bash
javac -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:out -d out src/main/*.java src/tests/*.java
```

Then run the code:
```bash
java -cp out main.CardGame
```

To run the tests:
```bash
java -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:out org.junit.runner.JUnitCore tests.TestSuite
```

### Contents
#### The Code Part:
Your code part includes the following two files (i.e., cards.jar and
cardsTest.zip).

- A copy of your finished classes in an executable jar file named cards.jar. The jar file
should include both the bytecode (.class) and source files (.java) of your
submission.

- A copy of your finished classes, and associated test classes and test suites, and
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
This falls under the MIT License. See [LICENSE](LICENSE) for more information.