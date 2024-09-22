package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {
    private final Map<BigDecimal, CostStrategy> weightedCostPerGram;

    /**
     * creates a weighted cost strategy with A 80/20 split using bot monetary cost
     * strategy and carbon cost strategy.
     * @param monetaryCostStrategy a monetary cost strategy.
     * @param carbonCostStrategy a carbon cost strategy.
     */
    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy, CarbonCostStrategy carbonCostStrategy) {
        weightedCostPerGram = new HashMap<>();
        weightedCostPerGram.put(BigDecimal.valueOf(1), monetaryCostStrategy);
        weightedCostPerGram.put(BigDecimal.valueOf(2), carbonCostStrategy);
    }

    /**
     * Calculates the weighted cost.
     * @param shipmentOption a shipment option with packaging.
     * @return a new shipment cost using the weighted cost and shipmentOption
     */
    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {

        ShipmentCost monetaryCost = this.weightedCostPerGram.get(BigDecimal.valueOf(1)).getCost(shipmentOption);
        ShipmentCost carbonCost = this.weightedCostPerGram.get(BigDecimal.valueOf(2)).getCost(shipmentOption);
        double monetaryCostDouble = monetaryCost.getCost().doubleValue() * 0.8;
        double carbonCostDouble = carbonCost.getCost().doubleValue() * 0.2;

        BigDecimal finalWeightedCost = BigDecimal.valueOf(monetaryCostDouble + carbonCostDouble);
        return new ShipmentCost(shipmentOption, finalWeightedCost);
    }
}