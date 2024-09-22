package com.amazon.ata.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarbonCostStrategy implements CostStrategy {
    private final Map<Material, BigDecimal> carbonCostPerGram;

    /**
     * Creates a carbonCostStrategy object.
     */
    public CarbonCostStrategy() {
        carbonCostPerGram = new HashMap<>();
        carbonCostPerGram.put(Material.CORRUGATE, BigDecimal.valueOf(0.017));
        carbonCostPerGram.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(0.012));
    }

    /**
     * Calculate the carbon cost of the packaging using the shipment option and carbonCostMap.
     * @param shipmentOption a shipment option with packaging.
     * @return a new shipmentCost with the shipment option and carbon cost.
     */
    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal materialCarbonCost = this.carbonCostPerGram.get(packaging.getMaterial());
        BigDecimal carbonCost = materialCarbonCost.multiply(shipmentOption.getPackaging().getMass());
        return new ShipmentCost(shipmentOption, carbonCost);
    }
}