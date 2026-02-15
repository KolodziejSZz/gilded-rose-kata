package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

final class QualityStrategyResolver {

    private static final Map<String, QualityUpdateStrategy> STRATEGIES = new HashMap<>();

    static {
        STRATEGIES.put("Aged Brie", AgedBrieStrategy.INSTANCE);
        STRATEGIES.put("Sulfuras, Hand of Ragnaros", SulfurasStrategy.INSTANCE);
        STRATEGIES.put("Backstage passes to a TAFKAL80ETC concert", BackstagePassStrategy.INSTANCE);
    }

    static QualityUpdateStrategy resolve(Item item) {
        QualityUpdateStrategy strategy = STRATEGIES.get(item.name);
        if (strategy != null) {
            return strategy;
        }
        if (item.name.startsWith("Conjured")) {
            return ConjuredItemStrategy.INSTANCE;
        }
        return NormalItemStrategy.INSTANCE;
    }
}
