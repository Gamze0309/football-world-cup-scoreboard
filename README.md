# Football World Cup Score Board

A simple in-memory library for tracking football match scores.

## Features

- Start a game (initial score 0-0)
- Finish a game (removes from score board)
- Update score (set both home and away team scores)
- Get summary sorted by total score (ties ordered by most recently added)

## Running Tests

```bash
# Prerequisites:
# - JDK 17 or newer installed and `java` on PATH
# - Apache Maven installed (Maven 3.8+ recommended) and `mvn` on PATH

# Run tests
mvn test
```

## Assumptions

- Team names are case-insensitive ("Mexico" equals "mexico")
- A team can only play in one active match at a time
- Scores cannot be negative
- Team names cannot be null, empty, same or whitespace-only
