# Ejada Java Internship

My daily journey through the Ejada Java internship program. Each day I build a small project to practice a new Java concept.

## Goal

Learn something new every day and reinforce it by building a small, self-contained project around it.

## Structure

Each day gets its own folder, numbered sequentially:

```
DayX/
├── Main.java   # the code for that day
└── Justfile    # compile & run commands
```

Repo root also includes:

```
devenv.nix   # dev environment setup (dependencies, services, etc.)
```

## Running a day's project

Each `DayX` folder has a `Justfile` for compiling and running the code:

```bash
cd DayX
just run
```

Each `Justfile` should have a `run` target that will be the default targert, but implementation of the target will differ throughout the repo.
As we get more advanced, we will swich from a single java file to multiple ones so i will use maven (or gradle) later on.

## Progress log

| Day | Topic                                                      | Notes                                           |
| --- | ---------------------------------------------------------- | ----------------------------------------------- |
| 1   | Basic Java syntax (variables, data types, arrays, strings) | Created a simple student managment sytem in cli |

## Setup

This repo uses [devenv](https://devenv.sh/) to manage the development environment:

```bash
devenv shell
```
## Links

The videos I watched to learn Java are listed in [VIDEOS.md](VIDEOS.md).