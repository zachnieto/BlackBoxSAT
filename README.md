# BlackBoxSAT
Solving the consistency problem for the 70's board game BlackBox

## Overview of the game
Inspired by the invention of the CAT scanner, BlackBox is a puzzle game in which players try to locate balls, called "atoms" that have been hidden inside a grid, the namesake "blackbox." Player can "shoot" rays of light from the border of the grid into the blackbox, which may hit atoms directly or deflect into a new path. In practice, players uncover hints along the border that each show the end behavior of a ray shot into the blackbox from that position. From these hints, players can deduce the locations of these atoms.  

A full overview of the rules can be found here: https://en.wikipedia.org/wiki/Black_Box_(game)

You can try out an online version of the game @ http://bibeault.ninja/blackbox/ 

## Our goal
We have written a program that represents Black Box hints as a SAT problems. Bundling several hints together allows us to determine their consistency (i.e. check for contradictions). For consistent boards, our algorithm can also recover a set of ball positions that satisfy each of the "hints."

## Dependencies
A dependency necessary for implementation is [LogicNG] (https://github.com/logic-ng/LogicNG). LogicNG is a library that allows for memory-effecient Boolean formula manipulation in Java. It provides a flexible tool for parsing complex boolean formulas into conjunctive normal form (CNF), as well as a Java implementation of MiniSAT.  

For property-based testing, we use a framework for JUnit called [Quick-Check] (https://pholser.github.io/junit-quickcheck/site/0.9.5/).

Both of these dependencies are part of the Maven Central Repository, and they should already be included in the project's pom.xml file. If, for whatever reason, it gives an error, manually paste the following into your pom.xml copy: 

```
<dependencies>
  <dependency>
    <groupId>org.logicng</groupId>
    <artifactId>logicng</artifactId>
    <version>2.0.2</version>
  </dependency>
	<dependency>
	  <groupId>com.pholser</groupId>
	  <artifactId>junit-quickcheck-core</artifactId>
	  <version>0.9.5</version>
	</dependency>
	<dependency>
    <groupId>com.pholser</groupId>
    <artifactId>junit-quickcheck-generators</artifactId>
    <version>0.9.5</version>
  </dependency>
</dependencies>
```

## Getting Started

### Instantiating Hints
Hints can be instantiated as Hit or Exit objects, children of the AHint abstract class. All that is required for Boolean formula generation is an AHint object. 


