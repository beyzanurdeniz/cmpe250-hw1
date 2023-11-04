# Tattaglia Crime Family Hierarchy Structure

## Project Overview

This README provides a concise summary of the data structures and algorithms class project related to the Tattaglia Crime Family hierarchy structure. The project description PDF contains detailed instructions for implementing the project.

This project is a Java implementation for AVL tree.
## Project Description

The project focuses on understanding and simulating the hierarchical structure of the Tattaglia Crime Family. The family's structure is defined by specific rules and principles:

### Basics
- The family has a hierarchical structure, with a current boss at the top (rank 0).
- Member ranks are based on their proximity to the boss.
- Each member has a superior giving them orders.
- Members can have at most two lower-ranking members under their command.
- New members join at the bottom of the hierarchy.
- No two members have the same Golden Mean Score (GMS).

### Keeping the Balance
- The family values lower hierarchy levels.
- To maintain balance, the highest-ranking and lowest-ranking members with no subordinates should have at most a 1 rank difference.
- Reorganization processes are used to achieve this balance.

### Reorganization Process
- Initiated by members with new subordinates or members leaving the family.
- Four scenarios determine who replaces the reorganizing member based on substructure heights and GMS values.

### Leaving the Family
- Different outcomes when a member leaves, depending on their rank and number of subordinates.

## Project Files

- [Project Description PDF](https://github.com/beyzanurdeniz/cmpe250-hw1/blob/31d701189295ded005f09cf4712b9af70057d06b/project_description.pdf)
