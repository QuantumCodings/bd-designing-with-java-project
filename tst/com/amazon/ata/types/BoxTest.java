package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
    private Material BoxMaterial = Material.CORRUGATE;
    private BigDecimal BoxLength = BigDecimal.valueOf(5.6);
    private BigDecimal BoxWidth = BigDecimal.valueOf(3.3);
    private BigDecimal BoxHeight = BigDecimal.valueOf(8.1);

    private Box packaging;

    @BeforeEach
    public void setUp() {
        packaging = new Box(BoxMaterial, BoxLength, BoxWidth, BoxHeight);
    }

    @Test
    public void canFitItem_itemLengthTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(BoxLength.add(BigDecimal.ONE))
                .withWidth(BoxWidth)
                .withHeight(BoxHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer length than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemWidthTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(BoxLength)
                .withWidth(BoxWidth.add(BigDecimal.ONE))
                .withHeight(BoxHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer width than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemHeightTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(BoxLength)
                .withWidth(BoxWidth)
                .withHeight(BoxHeight.add(BigDecimal.ONE))
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer height than package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSameSizeAsBox_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(BoxLength)
                .withWidth(BoxWidth)
                .withHeight(BoxHeight)
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item the same size as the package should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSmallerThanBox_doesFit() {
        // GIVEN
        Item item = Item.builder()
                .withLength(BoxLength.subtract(BigDecimal.ONE))
                .withWidth(BoxWidth.subtract(BigDecimal.ONE))
                .withHeight(BoxHeight.subtract(BigDecimal.ONE))
                .build();

        // WHEN
        boolean canFit = packaging.canFitItem(item);

        // THEN
        assertTrue(canFit, "Item smaller than the package should fit in the package.");
    }

    @Test
    public void getMass_calculatesMass_returnsCorrectMass() {
        // GIVEN
        packaging = new Box(Material.CORRUGATE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.valueOf(20));

        // WHEN
        BigDecimal mass = packaging.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(1000), mass,
                "Item smaller than the box should fit in the package.");
    }
}