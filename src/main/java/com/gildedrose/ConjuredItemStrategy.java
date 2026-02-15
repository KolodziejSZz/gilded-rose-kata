package com.gildedrose;

final class ConjuredItemStrategy implements QualityUpdateStrategy {

    static final ConjuredItemStrategy INSTANCE = new ConjuredItemStrategy();

    private static final int MIN_QUALITY = 0;

    @Override
    public void update(Item item) {
        item.sellIn -= 1;
        int degradation = item.sellIn < 0 ? 4 : 2;
        item.quality = Math.max(item.quality - degradation, MIN_QUALITY);
    }
}
