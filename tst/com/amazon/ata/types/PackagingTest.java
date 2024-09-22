package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackagingTest {
    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Packaging packaging = new Packaging(Material.CORRUGATE);

        // WHEN
        boolean result = packaging.equals(packaging);

        // THEN
        assertTrue(result, "An object should be equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Packaging packaging = new Packaging(Material.CORRUGATE);

        // WHEN
        boolean isEqual = packaging.equals(null);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Packaging packaging = new Packaging(Material.CORRUGATE);
        Object other = "String type!";

        // WHEN
        boolean isEqual = packaging.equals(other);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal to an object of a different type.");
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        // GIVEN
        Packaging packaging = new Packaging(Material.CORRUGATE);
        Object other = new Packaging(Material.CORRUGATE);

        // WHEN
        boolean isEqual = packaging.equals(other);

        // THEN
        assertTrue(isEqual, "Packaging with the same attributes should be equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        Packaging packaging = new Packaging(Material.CORRUGATE);
        Packaging other = new Packaging(Material.CORRUGATE);

        // WHEN + THEN
        assertEquals(packaging.hashCode(), other.hashCode(), "Equal objects should have equal hashCodes");
    }
    @Test
    public void height_width_length_variables_removed_from_packaging_class() {
        Field[] fields = Packaging.class.getDeclaredFields();
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("height")), "height attribute should be removed from Packaging class since it was moved to Box class");
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("width")),  "width attribute should be removed from Packaging class since it was moved to Box class");
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("length")), "length attribute should be removed from Packaging class since it was moved to Box class");
    }
}
