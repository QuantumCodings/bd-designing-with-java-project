package com.amazon.ata.dao;

import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FcPackagingOption;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Access data for which packaging is available at which fulfillment center.
 */
public class PackagingDAO {

    /**
     * A list of fulfillment centers with a packaging options they provide.
     */
    private Map<FulfillmentCenter, Set<FcPackagingOption>> fcPackagingOptions1;

    /**
     * Instantiates a PackagingDAO object.
     * @param datastore Where to pull the data from for fulfillment center/packaging available mappings.
     */
    public PackagingDAO(PackagingDatastore datastore) {
        Set<FcPackagingOption> tempPackaging = new HashSet<>(datastore.getFcPackagingOptions());
        this.fcPackagingOptions1 = new HashMap<>();
        for (FcPackagingOption fcPackagingOption : datastore.getFcPackagingOptions()) {
            fcPackagingOptions1.put(fcPackagingOption.getFulfillmentCenter(), tempPackaging);
        }
    }

    /**
     * Checks if an item can fit in any packaging available at the specified fulfillment center.
     *
     * @param item The item to check.
     * @param fulfillmentCenter The fulfillment center to check for available packaging options.
     * @return true if the item can fit in any packaging; false otherwise.
     */
    public boolean canFit(Item item, FulfillmentCenter fulfillmentCenter) {
        // Get all the FcPackagingOptions for the specified FulfillmentCenter
        Set<FcPackagingOption> packagingOptions = fcPackagingOptions1.get(fulfillmentCenter);
        if (packagingOptions == null) {
            return false;
        }

        // Iterate over all the packaging options for the given FulfillmentCenter
        for (FcPackagingOption option : packagingOptions) {
            Packaging packaging = option.getPackaging();
            // Check if the packaging can fit the item based on its type (Box or PolyBag)
            if (packaging.canFitItem(item)) {
                return true; // Item can fit in this packaging
            }
        }

        return false; // No packaging found that can fit the item
    }

    /**
     * Returns the packaging options available for a given item at the specified fulfillment center.
     * Throws exceptions if no packaging fits.
     *
     * @param item the item to pack
     * @param fulfillmentCenter fulfillment center to fulfill the order from
     * @return the shipping options available for that item; never empty
     * @throws UnknownFulfillmentCenterException if the fulfillmentCenter is not in the fcPackagingOptions list
     * @throws NoPackagingFitsItemException if the item doesn't fit in any packaging at the FC
     */
    public List<ShipmentOption> findShipmentOptions(Item item, FulfillmentCenter fulfillmentCenter)
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {

        // Check if the fulfillment center exists
        if (fcPackagingOptions1.get(fulfillmentCenter) == null) {
            throw new UnknownFulfillmentCenterException(
                    String.format("Unknown FC: %s!", fulfillmentCenter.getFcCode()));
        }

        // List to store shipment options that can fit the item
        List<ShipmentOption> result = new ArrayList<>();

        // Iterate over the packaging options for the given fulfillment center
        for (FcPackagingOption fcPackagingOption : fcPackagingOptions1.get(fulfillmentCenter)) {
            Packaging packaging = fcPackagingOption.getPackaging();
            String fcCode = fcPackagingOption.getFulfillmentCenter().getFcCode();
            // If the fulfillment center matches, check if the packaging fits the item
            if (fcCode.equals(fulfillmentCenter.getFcCode()) && packaging.canFitItem(item)) {
                // If it fits, create a shipment option and add it to the list
                result.add(ShipmentOption.builder()
                        .withItem(item)
                        .withPackaging(packaging)
                        .withFulfillmentCenter(fulfillmentCenter)
                        .build());
            }
        }

        // If no packaging was found that fits the item, throw an exception
        if (result.isEmpty()) {
            throw new NoPackagingFitsItemException(
                    String.format("No packaging at %s fits %s!", fulfillmentCenter.getFcCode(), item));
        }

        return result; // Return the list of valid shipment options
    }

    /**
     * Duplicate checker method to find duplicate items and remove them.
     *
     * @param fcPackagingOptions a list of ShipmentOptions
     * @return returns a list of unique ShipmentOptions
     */
    public List<ShipmentOption> duplicateCheck(List<ShipmentOption> fcPackagingOptions) {
        if (fcPackagingOptions != null) {
            for (int i = 0; i < fcPackagingOptions.size() - 1; i++) {
                String fcCode1 = fcPackagingOptions.get(i).getFulfillmentCenter().getFcCode();
                String fcCode2 = fcPackagingOptions.get(i + 1).getFulfillmentCenter().getFcCode();

                // Remove duplicates based on Fulfillment Center code
                if (fcCode1.equals(fcCode2)) {
                    fcPackagingOptions.remove(i);
                }
            }
        }
        return fcPackagingOptions;
    }
}
