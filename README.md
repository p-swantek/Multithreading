# Multithreading
Some multithreading work using Java

## HammingNumbers
- Multithreaded application to generate the first 60 [Hamming Numbers](https://en.wikipedia.org/wiki/Regular_number)
- Concepts of dataflow as well as using thread pools

## MatrixMath
- Application that uses different multithreading techniques to perform [matrix math](https://en.wikipedia.org/wiki/Matrix_(mathematics))
- Explores techniques such as using single thread per task model, thread pools, and the Streams API from Java 8

## DiningPhilosophers
- Application that uses different multithreading techniques to solve the [dining philosophers problem](https://en.wikipedia.org/wiki/Dining_philosophers_problem)
- Demonstrates how using either resource ordering or tokens can avoid having the philosophers deadlock

## Optimistic
- Application that creates and uses a threadsafe list that doesn't use any blocking to maintain thread safety
- The list implementation uses an optimistic strategy to maintain thread safety, does not need to use any blocking

## SafeStack
- Application that creates and uses a thread safe stack
- Safe stack is an aggregate, uses a non thread safe data structure to perform operations, but the safe stack uses synchronization to ensure thread safety