# Kariba
Africa is hot and water is scarce. 
The animals want to find a waterhole where they can refresh themselves. 
Of course, every animal wants to be the first to drink and so the elephant chases away the rhino, and the rhino chases away the mouse. 
But it is well known that elephants are afraid of mice. 
Therefore, the little mouse chases away the elephant.
--- 
# Game

## Preparation
- Each player draw five random cards from a deck of 64 cards (8 animals x 8 replicas).
- The lake is set

## Lake
- The Lake has 8 spots [1-8], each animal has a corresponding position

## Play
During a round, a player lays down one or more cards of the same animal, placing the cards in the corresponding position (1-9) around the lake.

For each card lay down, the player draw a card from the deck.

If at least 3 cards of the same animal are placed in front of the lake, then these animals scare weaker animal(s) (closest lower number) while they are drinking. 
The player picks all the cards from the weaker animal off the board and puts them face down in front of her/him. Each card is worth 1 point

## Goal
When there are no more cards in play. Whoever has the most points wins.

--- 
## Prerequisites
- JDK 11+: [Linux](https://tecadmin.net/install-openjdk-java-ubuntu/)
- Kotlin 1.3.50: [Install](https://medium.com/@sushanthande1/install-kotlin-on-linux-ubuntu-1a3f97dffa40)

## Technologies
- Gradle
- Kotlin
- Clikt

## Architecture
Based on Clean Architecture: [Reference](https://www.freecodecamp.org/news/a-quick-introduction-to-clean-architecture-990c014448d2/)

### Modules
- entities:
    - Domain and associated business logic
- use cases:
    - Application specific business logic
    - Orchestration
- cli:
    - Application entrypoint
    - Receives and responds to command from terminal
- main
    - main component
    - Dependency Injection (No framework used)

---
# E