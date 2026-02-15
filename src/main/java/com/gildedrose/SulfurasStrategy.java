package com.gildedrose;

final class SulfurasStrategy implements QualityUpdateStrategy {

    static final SulfurasStrategy INSTANCE = new SulfurasStrategy();

    @Override
    public void update(Item item) {
        // Legendary item: never changes
    }
}
