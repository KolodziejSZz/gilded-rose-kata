# Gilded Rose — Refactoring Kata

## Task Description

Gilded Rose is an inventory management system for an inn that updates `sellIn` (days to sell) and `quality` for each item at the end of every day. The original implementation consisted of a single monolithic `updateQuality()` method with deeply nested conditionals — hard to read, test, and extend.

**Goal:** refactor the existing code, then add support for a new item category — **Conjured** items, which degrade in quality twice as fast as normal items.

## Business Rules

| Item | sellIn | quality |
|---|---|---|
| **Normal** | -1/day | -1 before sell date, -2 after, min 0 |
| **Aged Brie** | -1/day | +1 before sell date, +2 after, max 50 |
| **Sulfuras** | never changes | never changes (always 80) |
| **Backstage passes** | -1/day | +1 (>10 days), +2 (6–10 days), +3 (1–5 days), drops to 0 after concert, max 50 |
| **Conjured** *(new)* | -1/day | -2 before sell date, -4 after, min 0 |

## Approach

### The Problem

The original `updateQuality()` method (~55 lines) had 5 levels of nested `if/else` statements, with logic for different item types interleaved throughout. Adding a new type (Conjured) in such code would be risky and unreadable.

### Applied Pattern — Strategy Pattern

The quality update logic was extracted into separate strategy classes. Each item type has its own implementation of the `QualityUpdateStrategy` interface, and the `QualityStrategyResolver` class acts as a factory that selects the appropriate strategy based on the item name.

### Architecture

```
GildedRose.updateQuality()
  └─ for each item:
       QualityStrategyResolver.resolve(item)  →  QualityUpdateStrategy
       strategy.update(item)
```

```
QualityUpdateStrategy (interface)
  ├── NormalItemStrategy
  ├── AgedBrieStrategy
  ├── SulfurasStrategy
  ├── BackstagePassStrategy
  └── ConjuredItemStrategy
```

### Key Design Decisions

1. **Functional interface** — `QualityUpdateStrategy` is annotated with `@FunctionalInterface` and defines a single method `update(Item)`.
2. **Singletons** — each strategy exposes a static `INSTANCE` field, since strategies hold no state.
3. **HashMap in resolver** — mapping item names to strategies provides O(1) lookup; Conjured items are identified by the `startsWith("Conjured")` prefix.
4. **`Item` class untouched** — as required by the kata, the `Item` class was not modified.

## File Structure

### Modified
- `src/main/java/com/gildedrose/GildedRose.java` — simplified to 3 lines of logic
- `src/test/java/com/gildedrose/GildedRoseTest.java` — expanded from 1 to 33+ tests

### New
| File | Role |
|---|---|
| `QualityUpdateStrategy.java` | Strategy interface |
| `QualityStrategyResolver.java` | Factory — resolves the correct strategy by item name |
| `NormalItemStrategy.java` | Standard quality degradation |
| `AgedBrieStrategy.java` | Quality increases with age |
| `SulfurasStrategy.java` | No changes (legendary item) |
| `BackstagePassStrategy.java` | Complex backstage pass logic |
| `ConjuredItemStrategy.java` | Double degradation rate (new feature) |

## Tests

Tests in `GildedRoseTest.java` cover all item types and edge cases:

- **Normal items** (5 tests) — degradation before/after sell date, quality floor at 0
- **Aged Brie** (5 tests) — increase before/after sell date, quality cap at 50
- **Sulfuras** (3 tests) — sellIn and quality never change
- **Backstage passes** (9 tests) — thresholds at 10/5 days, drop to 0, quality cap at 50
- **Conjured items** (6 tests) — double degradation, different names with "Conjured" prefix
- **Parameterized tests** (`@ParameterizedTest` + `@CsvSource`) — systematic boundary checks for each type
- **Multi-item test** — verifies all item types are handled correctly in a single update call

## How to Run

```bash
# Build the project
./mvnw compile

# Run tests
./mvnw test
```
