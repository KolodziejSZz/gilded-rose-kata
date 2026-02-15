package com.gildedrose;

final class NormalItemStrategy implements QualityUpdateStrategy {

    static final NormalItemStrategy INSTANCE = new NormalItemStrategy();

    private static final int MIN_QUALITY = 0;

    @Override
    public void update(Item item) {
        item.sellIn -= 1;
        int degradation = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(item.quality - degradation, MIN_QUALITY);
    }
}
