package com.amazon.ata.types;

import java.util.Objects;

/**
 * How to fulfill, package, and ship an item.
 */
public class ShipmentOption {
    /**
     * The item to ship.
     */
    private Item item;

    /**
     * The packaging option used.
     */
    private Packaging packaging;

    /**
     * The fulfillment center shipping the item.
     */
    private FulfillmentCenter fulfillmentCenter;

    public ShipmentOption(Item item, Packaging packaging, FulfillmentCenter fulfillmentCenter) {
        this.item = item;
        this.packaging = packaging;
        this.fulfillmentCenter = fulfillmentCenter;
    }

    public static Builder builder() {
        return new Builder();
    }


    // Existing builder and getter methods remain unchanged.

    public Item getItem() {
        return item;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public FulfillmentCenter getFulfillmentCenter() {
        return fulfillmentCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentOption that = (ShipmentOption) o;
        return item.equals(that.item) && packaging.equals(that.packaging) &&
                fulfillmentCenter.equals(that.fulfillmentCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item , packaging , fulfillmentCenter);
    }

    /**
     * {@code ShipmentOption} builder static inner class.
     */
    public static class Builder {
        private Item item;
        private Packaging packaging;
        private FulfillmentCenter fulfillmentCenter;

        private Builder() {
        }

        public Builder withItem(Item itemToUse) {
            this.item = itemToUse;
            return this;
        }

        public Builder withPackaging(Packaging packagingToUse) {
            this.packaging = packagingToUse;
            return this;
        }

        public Builder withFulfillmentCenter(FulfillmentCenter fulfillmentCenterToUse) {
            this.fulfillmentCenter = fulfillmentCenterToUse;
            return this;
        }

        public ShipmentOption build() {
            return new ShipmentOption(item, packaging, fulfillmentCenter);
        }

        public Item getItem() {
            return item;
        }
    }
}
