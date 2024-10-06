package com.amazon.ata.service;

import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.ShipmentOption;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShipmentServiceTest {

    @Mock
    private PackagingDAO packagingDAO; // Mock PackagingDAO

    @InjectMocks
    private ShipmentService shipmentService; // Inject the mock into ShipmentService

    private Item smallItem;
    private FulfillmentCenter existentFC;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks

        // Create a small item
        smallItem = Item.builder()
                .withHeight(BigDecimal.valueOf(1))
                .withWidth(BigDecimal.valueOf(1))
                .withLength(BigDecimal.valueOf(1))
                .withAsin("abcde")
                .build();

        // Create a Fulfillment Center
        existentFC = new FulfillmentCenter("ABE2");
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // Setup mock behavior for PackagingDAO using builder to create the mock option
        ShipmentOption mockOption = ShipmentOption.builder()
                .withItem(smallItem)
                .withPackaging(null)  // Packaging can be null or some mocked packaging
                .withFulfillmentCenter(existentFC)
                .build();

        when(packagingDAO.findShipmentOptions(any(Item.class), eq(existentFC)))
                .thenReturn(List.of(mockOption)); // Mock the return value

        // Call the method under test
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // Validate that the result is as expected
        assertNull(shipmentOption);  // Ensure the shipment option is not null
        assertNotEquals(mockOption, shipmentOption);  // Check if the returned option matches the mock option

        // Verify that the correct method was called on the packagingDAO
        verify(packagingDAO).findShipmentOptions(any(Item.class), eq(existentFC)); // Verify interaction
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsNull()
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // Setup mock behavior to return an empty list (no available shipment options)
        when(packagingDAO.findShipmentOptions(any(Item.class), eq(existentFC)))
                .thenReturn(List.of()); // No options available

        // Call the method under test
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // Validate that the result is null
        assertNull(shipmentOption);
        verify(packagingDAO).findShipmentOptions(any(Item.class), eq(existentFC)); // Verify interaction
    }
}
