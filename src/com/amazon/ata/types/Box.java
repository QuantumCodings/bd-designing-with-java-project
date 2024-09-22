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
     * @param width    - the width of the package
     * @param height   - the height of the package
     * @param material - the material of the package
     * @param length   - the length of the package
     */
    public Box(Material material, BigDecimal length , BigDecimal width , BigDecimal height) {
        super(material);
        this.length = length;
        this.width = width;
        this.height = height;
    }


    /**
     * Returns whether the given item will fit in this packaging.
     *
     * @param item the item
     * @return will the item fit
     */
    @Override
    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
                this.width.compareTo(item.getWidth()) > 0 &&
                this.height.compareTo(item.getHeight()) > 0;
    }

    /**
     * Returns the mass packaging in grams.
     * @return the mass packaging
     */
    @Override
    public BigDecimal getMass() {
        BigDecimal two = BigDecimal.valueOf(2);

        BigDecimal endsArea = length.multiply(width).multiply(two);
        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
        BigDecimal longSidesArea = width.multiply(height).multiply(two);

        return endsArea.add(shortSidesArea).add(longSidesArea);
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
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
        return Objects.hash(super.hashCode() , length , width , height);
    }
}
