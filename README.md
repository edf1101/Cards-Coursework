# Software Development (ECM2414) Coursework

_This is the ECM2414 Software Development Pair Programming Coursework_

Run program from CLI using the following command:
```bash
javac -d out src/main/*.java
java -cp out main.CardGame
```

Run tests from CLI using the following command:
```bash
javac -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:out -d out src/tests/TestSuite.java 
java -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:out org.junit.runner.JUnitCore tests.TestSuite
```


Compile Tests & Program from CLI using the following command:
```bash
javac -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:out -d out src/main/*.java src/tests/*.java

```