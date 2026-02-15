package com.gildedrose;

final class BackstagePassStrategy implements QualityUpdateStrategy {

    static final BackstagePassStrategy INSTANCE = new BackstagePassStrategy();

    private static final int MAX_QUALITY = 50;

    @Override
    public void update(Item item) {
        item.sellIn -= 1;

        if (item.sellIn < 0) {
            item.quality = 0;
        } else {
            int increase = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
            item.quality = Math.min(item.quality + increase, MAX_QUALITY);
        }
    }
}
