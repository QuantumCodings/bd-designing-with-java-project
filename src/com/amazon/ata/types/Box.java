package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class Box extends Packaging {

    /**
     * This packaging's length.
     */
    private BigDecimal length;

    /**
     * This packaging's smallest dimension.
     */
    private BigDecimal width;

    /**
     * This packaging's largest dimension.
     */
    private BigDecimal height;
    /**
     * Instantiates a new box object.
     *
     * @param material - the Material of the package
     * @param length   - the length of the package
     * @param width    - the width of the package
     * @param height   - the height of the package
     */
    public Box(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        Material material1 = Material.CORRUGATE;
        this.length = length;
        this.width = width;
        this.height = height;
    }


    @Override
    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
    }

    @Override
    public BigDecimal getMass() {
        BigDecimal two = BigDecimal.valueOf(2);
        // For simplicity, we ignore overlapping flaps
        BigDecimal endsArea = length.multiply(width).multiply(two);
        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
        BigDecimal longSidesArea = width.multiply(height).multiply(two);

        return endsArea.add(shortSidesArea).add(longSidesArea);
    }
    @Override
    public Material getMaterial() {
        return Material.CORRUGATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Box box = (Box) o;
        return length.equals(box.length) && width.equals(box.width) && height.equals(box.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), length, width, height);
    }
}
