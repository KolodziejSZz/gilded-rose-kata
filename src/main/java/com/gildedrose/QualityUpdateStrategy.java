package com.gildedrose;

@FunctionalInterface
interface QualityUpdateStrategy {

    void update(Item item);
}
