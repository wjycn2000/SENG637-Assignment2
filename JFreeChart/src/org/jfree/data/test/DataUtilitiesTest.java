package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals; 
import org.jmock.*;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jfree.data.KeyedValues;
import java.lang.IllegalArgumentException;
import java.util.Arrays;

public class DataUtilitiesTest extends DataUtilities {
	private final Mockery context = new JUnit4Mockery();
	
	/**
     * Test Case: Standard case with positive values.
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
     * Test Case: Single-value dataset.
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
     * Test Case: Values including zero.
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
     * Test Case: Negative values.
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

    /**
     * Test Case: Null input.
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
     * Test Case: Empty dataset.
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

 /**
  * Test Case: Convert a valid array of doubles into a Number[] array.
  * Test Strategy: Equivalence Class Partitioning (ECP) - Normal case.
  * Expected Behavior: The method should correctly convert a double[] to Number[].
  */
   
 @Test
 public void testCreateNumberArray_ValidInput() {
     double[] input = {1.5, 2.5, 3.5};
     Number[] expected = {1.5, 2.5, 3.5};
     
     Number[] result = DataUtilities.createNumberArray(input);
     
     assertArrayEquals("The method should return an array of Number objects matching the input doubles.",
                       expected, result);
 }

 /**
  * Test Case: Convert a valid 2D array of doubles into a Number[][] array.
  * Test Strategy: Equivalence Class Partitioning (ECP) - Normal case.
  * Expected Behavior: The method should correctly convert a double[][] to Number[][].
  */
     
 @Test
 public void testCreateNumberArray2D_ValidInput() {
     double[][] input = {
         {1.1, 2.2, 3.3},
         {4.4, 5.5, 6.6}
     };

     Number[][] expected = {
         {1.1, 2.2, 3.3},
         {4.4, 5.5, 6.6}
     };

     Number[][] result = DataUtilities.createNumberArray2D(input);

     assertArrayEquals("The method should return a 2D array of Number objects matching the input doubles.",
                       expected, result);
 }
}