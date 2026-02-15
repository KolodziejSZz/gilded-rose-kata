package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void normalItem_beforeSellDate_decreasesQualityByOne() {
        Item item = updateItem("Normal Item", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(19, item.quality);
    }

    @Test
    void normalItem_onSellDate_decreasesQualityByTwo() {
        Item item = updateItem("Normal Item", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(18, item.quality);
    }

    @Test
    void normalItem_afterSellDate_decreasesQualityByTwo() {
        Item item = updateItem("Normal Item", -1, 20);
        assertEquals(-2, item.sellIn);
        assertEquals(18, item.quality);
    }

    @Test
    void normalItem_qualityNeverNegative() {
        Item item = updateItem("Normal Item", 5, 0);
        assertEquals(0, item.quality);
    }

    @Test
    void normalItem_qualityNeverNegativeAfterSellDate() {
        Item item = updateItem("Normal Item", -1, 1);
        assertEquals(0, item.quality);
    }

    @Test
    void agedBrie_beforeSellDate_increasesQualityByOne() {
        Item item = updateItem("Aged Brie", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void agedBrie_onSellDate_increasesQualityByTwo() {
        Item item = updateItem("Aged Brie", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void agedBrie_afterSellDate_increasesQualityByTwo() {
        Item item = updateItem("Aged Brie", -1, 20);
        assertEquals(-2, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void agedBrie_qualityNeverExceedsFifty() {
        Item item = updateItem("Aged Brie", 10, 50);
        assertEquals(50, item.quality);
    }

    @Test
    void agedBrie_qualityNeverExceedsFiftyAfterSellDate() {
        Item item = updateItem("Aged Brie", -1, 49);
        assertEquals(50, item.quality);
    }

    @Test
    void sulfuras_neverChangesSellIn() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", 10, 80);
        assertEquals(10, item.sellIn);
    }

    @Test
    void sulfuras_neverChangesQuality() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", 10, 80);
        assertEquals(80, item.quality);
    }

    @Test
    void sulfuras_neverChangesEvenWhenExpired() {
        Item item = updateItem("Sulfuras, Hand of Ragnaros", -1, 80);
        assertEquals(-1, item.sellIn);
        assertEquals(80, item.quality);
    }

    @Test
    void backstagePass_moreThanTenDays_increasesQualityByOne() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        assertEquals(14, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePass_exactlyElevenDays_increasesQualityByOne() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 11, 20);
        assertEquals(10, item.sellIn);
        assertEquals(21, item.quality);
    }

    @Test
    void backstagePass_tenDays_increasesQualityByTwo() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePass_sixDays_increasesQualityByTwo() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 6, 20);
        assertEquals(5, item.sellIn);
        assertEquals(22, item.quality);
    }

    @Test
    void backstagePass_fiveDays_increasesQualityByThree() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        assertEquals(4, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePass_oneDay_increasesQualityByThree() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 1, 20);
        assertEquals(0, item.sellIn);
        assertEquals(23, item.quality);
    }

    @Test
    void backstagePass_afterConcert_qualityDropsToZero() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(0, item.quality);
    }

    @Test
    void backstagePass_qualityNeverExceedsFifty() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        assertEquals(50, item.quality);
    }

    @Test
    void backstagePass_qualityNeverExceedsFiftyAtTenDays() {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        assertEquals(50, item.quality);
    }

    @Test
    void conjuredItem_beforeSellDate_decreasesQualityByTwo() {
        Item item = updateItem("Conjured Mana Cake", 10, 20);
        assertEquals(9, item.sellIn);
        assertEquals(18, item.quality);
    }

    @Test
    void conjuredItem_onSellDate_decreasesQualityByFour() {
        Item item = updateItem("Conjured Mana Cake", 0, 20);
        assertEquals(-1, item.sellIn);
        assertEquals(16, item.quality);
    }

    @Test
    void conjuredItem_afterSellDate_decreasesQualityByFour() {
        Item item = updateItem("Conjured Mana Cake", -1, 20);
        assertEquals(-2, item.sellIn);
        assertEquals(16, item.quality);
    }

    @Test
    void conjuredItem_qualityNeverNegative() {
        Item item = updateItem("Conjured Mana Cake", 10, 1);
        assertEquals(0, item.quality);
    }

    @Test
    void conjuredItem_qualityNeverNegativeAfterSellDate() {
        Item item = updateItem("Conjured Mana Cake", -1, 3);
        assertEquals(0, item.quality);
    }

    @Test
    void conjuredItem_differentName_stillRecognized() {
        Item item = updateItem("Conjured Dark Blade", 5, 10);
        assertEquals(4, item.sellIn);
        assertEquals(8, item.quality);
    }

    @ParameterizedTest(name = "Normal item: sellIn={0}, quality={1} -> quality={2}")
    @CsvSource({
        "10, 20, 19",
        " 1, 20, 19",
        " 0, 20, 18",
        "-1, 20, 18",
        " 5,  0,  0",
        " 0,  1,  0",
        "-1,  1,  0",
    })
    void normalItem_parameterized(int sellIn, int quality, int expectedQuality) {
        Item item = updateItem("+5 Dexterity Vest", sellIn, quality);
        assertEquals(expectedQuality, item.quality);
    }

    @ParameterizedTest(name = "Aged Brie: sellIn={0}, quality={1} -> quality={2}")
    @CsvSource({
        "10, 20, 21",
        " 0, 20, 22",
        "-1, 20, 22",
        "10, 50, 50",
        " 0, 49, 50",
        " 0, 50, 50",
    })
    void agedBrie_parameterized(int sellIn, int quality, int expectedQuality) {
        Item item = updateItem("Aged Brie", sellIn, quality);
        assertEquals(expectedQuality, item.quality);
    }

    @ParameterizedTest(name = "Backstage pass: sellIn={0}, quality={1} -> quality={2}")
    @CsvSource({
        "15, 20, 21",
        "11, 20, 21",
        "10, 20, 22",
        " 6, 20, 22",
        " 5, 20, 23",
        " 1, 20, 23",
        " 0, 20,  0",
        "-1, 20,  0",
        " 5, 49, 50",
        "10, 49, 50",
    })
    void backstagePass_parameterized(int sellIn, int quality, int expectedQuality) {
        Item item = updateItem("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
        assertEquals(expectedQuality, item.quality);
    }

    @ParameterizedTest(name = "Conjured: sellIn={0}, quality={1} -> quality={2}")
    @CsvSource({
        "10, 20, 18",
        " 1, 20, 18",
        " 0, 20, 16",
        "-1, 20, 16",
        "10,  1,  0",
        " 0,  3,  0",
        "10,  0,  0",
    })
    void conjuredItem_parameterized(int sellIn, int quality, int expectedQuality) {
        Item item = updateItem("Conjured Mana Cake", sellIn, quality);
        assertEquals(expectedQuality, item.quality);
    }

    @Test
    void updateQuality_handlesMultipleItemsCorrectly() {
        Item[] items = new Item[] {
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Conjured Mana Cake", 3, 6),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();

        assertEquals(19, items[0].quality);
        assertEquals(1, items[1].quality);
        assertEquals(80, items[2].quality);
        assertEquals(50, items[3].quality);
        assertEquals(4, items[4].quality);
    }

    private Item updateItem(String name, int sellIn, int quality) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return items[0];
    }
}
