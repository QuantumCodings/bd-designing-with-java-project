package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {
    private final BigDecimal volume;

    /**
     * Instantiates a new Packaging object.
     * @param material - the Material of the package
     * @param volume  - the volume of the package
     */
    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        BigDecimal length = item.getLength();
        BigDecimal height = item.getHeight();
        BigDecimal width = item.getWidth();
        BigDecimal itemVolume = length.multiply(width).multiply(height);

        return this.volume.compareTo(itemVolume) > 0;
    }

    @Override
    public BigDecimal getMass() {
        return BigDecimal.valueOf(Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6));
    }

    public BigDecimal getVolume() {
        return volume;
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
        PolyBag polyBag = (PolyBag) o;
        return volume.equals(polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode() , volume);
    }
}
