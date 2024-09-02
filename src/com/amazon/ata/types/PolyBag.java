package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {

    private BigDecimal volume;

    /**
     * constructs a polybag.
     * @param material the material is laminated plastic.
     * @param volume volume of bag
     */
    public PolyBag(Material material, BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        return this.volume.compareTo(item.getLength()
                .multiply(item.getHeight())
                .multiply(item.getWidth())) > 0;
    }

    @Override
    public BigDecimal getMass() {
        double mass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        return BigDecimal.valueOf(mass);
    }

    @Override
    public Material getMaterial() {
        return Material.LAMINATED_PLASTIC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PolyBag polyBag = (PolyBag) o;
        return volume.equals(polyBag.volume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume);
    }
}
