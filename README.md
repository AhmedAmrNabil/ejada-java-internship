# Ejada Java Internship

My daily journey through the Ejada Java internship program. Each day I build a small project to practice a new Java concept.

## Goal

Learn something new every day and reinforce it by building a small, self-contained project around it.

## Structure

Day1-5 each get their own folder, numbered sequentially:

```
DayX/
├── Main.java   # the code for that day
└── Justfile    # compile & run commands
```
Starting from Day 6, work will move to the `hr-demo/` Maven project instead of a new `DayX/` folder each day, as Day6 is the starter template for the Spring Boot project. All subsequent days will iterate on it rather than creating new top-level folders:
```
hr-demo/
├── pom.xml
├── src/main/java/com/global/hr/   # controllers, services, etc.
├── src/main/resources/             # application.yml, i18n, templates
└── src/test/java/...
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

| Day | Topic                                                                                                                          | Notes                                                                                           |
| --- | ------------------------------------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------------------- |
| 1   | Basic Java syntax (variables, data types, arrays, strings)                                                                     | Created a simple student managment sytem in cli                                                 |
| 2   | Object-oriented programming (static members, encapsulation, inheritance, polymorphism, abstract classes, interfaces, packages) | Built a library management system and an employee hierarchy with polymorphic salary calculation |
| 3   | Exception handling, collection api,stream api, threads and synchronizations                                                    | Built a bank CLI with custom exceptions and a tiny tcp server                                   |
| 4   | SQL                                                                                                                            | main.sql file to test sql queries and DDL,DML statemets                                         |
| 5   | SQL functions, constraints, joins, subqueries, and set operators                                                               | Expanded the SQL practice scripts with more advanced query patterns                             |
| 6   | Java web frameworks, Servlets, Spring framework and Spring Boot                                                                | Intilized Springboot template with logging,i18n and monitoring                                  |

## Setup

This repo uses [devenv](https://devenv.sh/) to manage the development environment:

```bash
devenv shell
```

> [!NOTE]
> if you are running nixos, then sql developer extension won't work with non-fhsenv vscode
> so you will need to run vscode in fhsenv mode
> thats why I added it to the packages in devenv.nix, so you can run `devenv shell` and then run `code .` to open vscode in fhsenv mode.

To run the database setup, run:

```bash
devenv up --mode all
```

this will start the database container and run the init-db task to create the app user and the app schema.

To run the database itself later, run:

```bash
devenv up
```

## Links

The videos I watched to learn Java are listed in [JAVAVIDEOS.md](JAVAVIDEOS.md).

For the database videos, check [DBVIDEOS.md](DBVIDEOS.md).

For the Spring Boot videos, check [SPRINGBOOTVIDEOS.md](SPRINGBOOTVIDEOS.md).