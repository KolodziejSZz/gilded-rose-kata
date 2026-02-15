package com.gildedrose;

final class AgedBrieStrategy implements QualityUpdateStrategy {

    static final AgedBrieStrategy INSTANCE = new AgedBrieStrategy();

    private static final int MAX_QUALITY = 50;

    @Override
    public void update(Item item) {
        item.sellIn -= 1;
        int increase = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(item.quality + increase, MAX_QUALITY);
    }
}
