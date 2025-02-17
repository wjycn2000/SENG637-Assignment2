package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.junit.Test;
import org.jmock.*;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jfree.data.KeyedValues;
import java.lang.IllegalArgumentException;
import java.util.Arrays;

public class DataUtilitiesTest extends DataUtilities {
	private final Mockery context = new JUnit4Mockery();
	
	/**
     * Test Case 1: Standard case with positive values.
     * Test Strategy: Normal case (ECP)
     * Expected: Correct cumulative percentages calculated.
     */
    @Test
    public void testGetCumulativePercentagesNormalCase() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(3));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getKey(1); will(returnValue(1));
            allowing(mockData).getKey(2); will(returnValue(2));

            allowing(mockData).getValue(0); will(returnValue(5));
            allowing(mockData).getValue(1); will(returnValue(9));
            allowing(mockData).getValue(2); will(returnValue(2));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0, 1, 2)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.3125, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.875, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }

    /**
     * Test Case 2: Single-value dataset.
     * Test Strategy: Minimum input size (BVA)
     * Expected: The only value should have a cumulative percentage of 1.0.
     */
    @Test
    public void testGetCumulativePercentagesSingleValue() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(1));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getValue(0); will(returnValue(10));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(1.0, result.getValue(0).doubleValue(), 0.0001);
    }

    /**
     * Test Case 3: Values including zero.
     * Test Strategy: Handling zero (ECP)
     * Expected: Zero should not affect cumulative percentage calculations.
     */
    @Test
    public void testGetCumulativePercentagesWithZeroValue() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(3));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getKey(1); will(returnValue(1));
            allowing(mockData).getKey(2); will(returnValue(2));

            allowing(mockData).getValue(0); will(returnValue(5));
            allowing(mockData).getValue(1); will(returnValue(0));
            allowing(mockData).getValue(2); will(returnValue(5));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0, 1, 2)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0.5, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.5, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }

    /**
     * Test Case 4: Negative values.
     * Test Strategy: Handling negative numbers (ECP)
     * Expected: If allowed, cumulative percentages should work normally.
     */
    @Test
    public void testGetCumulativePercentagesWithNegativeValues() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(3));

            allowing(mockData).getKey(0); will(returnValue(0));
            allowing(mockData).getKey(1); will(returnValue(1));
            allowing(mockData).getKey(2); will(returnValue(2));

            allowing(mockData).getValue(0); will(returnValue(-3));
            allowing(mockData).getValue(1); will(returnValue(5));
            allowing(mockData).getValue(2); will(returnValue(4));

            allowing(mockData).getKeys(); will(returnValue(Arrays.asList(0, 1, 2)));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(-0.3, result.getValue(0).doubleValue(), 0.0001);
        assertEquals(0.2, result.getValue(1).doubleValue(), 0.0001);
        assertEquals(1.0, result.getValue(2).doubleValue(), 0.0001);
    }

    // ***************** INVALID CASES *****************

    /**
     * Test Case 5: Null input.
     * Test Strategy: Invalid input (ECP)
     * Expected: The method should throw an InvalidParameterException.
     */
    @Test
    public void testGetCumulativePercentagesNullInput() {
        try {
            DataUtilities.getCumulativePercentages(null);
            fail("Expected InvalidParameterException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected behavior, test passes
        }
    }

    /**
     * Test Case 6: Empty dataset.
     * Test Strategy: Edge case (ECP)
     * Expected: The method should return an empty KeyedValues instance.
     */
    @Test
    public void testGetCumulativePercentagesEmptyDataset() {
        KeyedValues mockData = context.mock(KeyedValues.class);

        context.checking(new Expectations() {{
            allowing(mockData).getItemCount(); will(returnValue(0));
            allowing(mockData).getKeys(); will(returnValue(Arrays.asList()));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockData);
        assertEquals(0, result.getItemCount());
    }
}
