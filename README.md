---------------------------------------------------------------------------------------------------------------------------------------
                                                 GROUP 28 - UNO FLIP GAME
----------------------------------------------------------------------------------------------------------------------------------------

# UNO Flip! - Java GUI Version
A simple but fully playable UNO Flip! game made in Java with Swing 

This is the double-sided version of UNO (Light side → Dark side) with all the crazy Flip cards, just like the real card game!!!!

## How to Run the Game

1. Make sure you have **Java 17** or higher installed
2. Clone or download this repo.
3. Open a terminal/command prompt in the project folder.
4. Compile (if you don't have the .jar yet):
   ```bash
   javac -d out src/*.java
   java -cp out Game
   ```

## Milestone 5 - BONUS FEATURES: SOUND EFFECTS + BACKGROUND MUSIC
FEATURE 7- Student-Designed Features

Sound effects were added for playing the game (different effects for different scenarios: wrong card/uno card/flip/wrong move etc and Coconut Mall as background music that loops the entire game :) )

How it works:
- When a Card is clicked, a sound plays
- Background music starts when the view is loaded (GUI is loaded) to loop forever

Contributions:

```Pardis Ehsani```: Added AudioPlayer Class, implemented methods in the Game Logic Class and have the audio played in the Controller class, found  the sounds and converted them into wav from mp3
- BUZZER SOUND
- VICTORY ROUND PLAYER SOUND
- AI SOUND
- UNO CORRECT SOUND
- UNO WRONG SOUND
- BACKGROUND MUSIC SOUND

Sounds can be found in the root of the project

SYSC 3110 Project Overview Milestone 4 UNO: 

For Milestone 4, we added the required features to complete the UNO Flip game: undo/redo, replay functionality, and save/load support using Java Serialization. We also updated the UML diagrams and added new test cases to validate the new features.
What Was Added
Undo/Redo: Players can reverse or reapply actions during gameplay.
Replay: Players can replay an entire finished game from start to end.
Save/Load: Game state can be serialized, saved, and restored later.
Updated Design: UML diagrams updated to reflect new state-handling and serialization structure.
Tests: New test cases covering serialization, undo/redo behavior, and replay sequences.
Changes Since Milestone 3
Added state-tracking structures for undo/redo.
Implemented replay controller and supporting logic.
Integrated Java Serialization throughout the model.
Updated UML diagrams and documentation.
Added new test coverage for all new features.
Contributions
Pardis: Implemented redo and replay features, integrated replay logic.
Anvita: Worked on serialization, save/load, updated UML, and contributed test cases.
Charis: Worked on serialization, controller integration, UML updates, and test cases.
Cherie: Worked on serialization, UML updates, and contributed test cases.
Known Issues
Replay currently runs at a fixed speed.
Undo/Redo applies only to logical game actions.
How to Run
Run the Game class from the GUI module. The menu includes options for New Game, Save, Load, Undo, Redo, and Replay.
   
SYSC 3110 Project Overview Milestone 3 UNO: 
This milestone is continuing on from the previous milestone, but instead, its implementing an AI Player feature and a FLIP Mode. 
The AI player is like a human player, but instead it plays like a human player. It includes the addition to more Game Logic and an AI Player class.
Furtheremore, the addition of the FLIP SIDE was implemented where when a player plays a FLIP CARD, it flips from DARK MODE to LIGHT MODE and vice-versa. 
This game plays as before and match points are determined based on the points attributed to each card. 

The main deliverables include:
Implemening the AIPlayer Feature
Implemteing UNOFLIP Feature
Maintain quality code with minimal smell for maintainability, expandibility and cohesiveness. 


```Pulcherie Mbaye:```
Contributed to the implementation of GameLogicModel, UnoController and UnoViewFrame, AI Player, AIPlayerTest and UNOFLIP.
Implemented the test cases for AIPlayerTest and GameLogicModelTest.

```Anvita Ala:```
Contributed to the implementation of GameLogicModel, UnoController and UnoViewFrame, AI Player and UNOFLIP.
Created the Sequence Diagram
Created UML class diagram

```Charis Nobossi:```
Contributed to the implementation of GameLogicModel, UnoController, UnoViewFrame, AI Player and UNOFLIP.
Described the data structures of the AIPlayer class. 

```Pardis Ehsani:```
Contributed to the implementation of GameLogicModel, UnoController, AI Player, AIPlayerTest and UNOFLIP.
Implemented the test cases for AIPlayerTest and GameLogicModelTest.

_____________________________________________________________________________________________________

SYSC 3110 Project Overview Milestone 2 UNO:
This milestone is about converting milestone 1 into a GUI following MVC pattern.
It includes the main gameplay logic, player management, unoFrame, UnoController and card functionalities.
all supported by unit tests and UML/Sequence diagrams to illustrate the system.

The main deliverables include:
Complete implementation of all classes(Card, Player, PlayerOrder, GameLogicModel, UnoView, UnoFrame, UnoController and Game)
UML and Sequence Diagrams represent the system's structure and interactions between various classes.
Unit tests for all classes and methods in the game.

Team contribution:

```Pulcherie Mbaye:```
Contributed to the implementation of GameLogicModel, UnoController and UnoViewFrame.
Implemented the test cases.

```Anvita Ala:```
Contributed to the implementation of GameLogicModel, UnoController and UnoViewFrame.
Created the Sequence Diagram
Created UML class diagram

```Charis Nobossi:```
Contributed to the implementation of GameLogicModel, UnoController and UnoViewFrame.

```Pardis Ehsani:```
Contributed to the implementation of GameLogicModel, UnoController and UnoViewFrame, PlayerCardButtons and database explanations. 
Contributed to the UML class diagram.

TO BE IMPLEMENTED
SYSC 3110 Project Overview Milestone 3 UNO: A GUI-based UNO-FLIP-sytem Java game where players can be AI.

TO BE IMPLEMENTED
SYSC 3110 Project Overview Milestone 4 UNO: Continuation of the GUI-based UNO-FLIP-style Java game where there are Redo capabilities, Reply Capability and Save/Load Features are implemented.

_____________________________________________________________________________________________________
SYSC 3110 Project Overview Milestone 1 UNO:
This project is a simplified UNO-style text-based card game implemented in Java.
It includes the main gameplay logic, player management and card functionalities,
all supported by unit tests and UML/Sequence diagrams to illustrate the system.

The main deliverables include:
Complete implementation of all classes(Card, Player, PlayerOrder, GameLogicModel and Game)
UML and Sequence Diagrams represent the system's structure and interactions between various classes.
Unit tests for all classes and methods in the game.

Team Contributions:
Pulcherie Mbaye:
Implemented the Player class
Created Unit tests for both Player and Card
Sesigbed the UML Class Diagram

Anvita Ala
Implemented the Card class
Contributed to the Game and GameLogicModel classes
Created the Sequence Diagram

Charis Nobossi
Contributed to the Game and Gamelogic classes
Contributed to the Card class

Pardis Ehsani
Implemented the PlayerOrder class.
Contributed to the Game and Gamelogic classes.

TO BE IMPLEMENTED
SYSC 3110 Project Overview Milestone 3 UNO: A GUI-based UNO-FLIP-sytem Java game where players can be AI.

TO BE IMPLEMENTED
SYSC 3110 Project Overview Milestone 4 UNO: Continuation of the GUI-based UNO-FLIP-style Java game where there are Redo capabilities, Reply Capability and Save/Load Features are implemented.


