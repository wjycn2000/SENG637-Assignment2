package org.jfree.data.test;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
	/*
	 * Test combine()
	 */
	
    /**
     * Test Case: Both input ranges are null.
     * Test Strategy: ECP - Null case
     * Expected Behavior: The method should return null.
     */
    @Test
    public void testCombineBothNullReturnsNull() {
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(null, null);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertNull("Combining two null ranges should return null.", combinedRange);
    }

    /**
     * Test Case: First input range is null.
     * Test Strategy: ECP - Null handling
     * Expected Behavior: The method should return the second range.
     */
    @Test
    public void testCombineFirstNullReturnsSecondRange() {
        Range range2 = new Range(2, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(null, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Combining null with a valid range should return the valid range.", range2, combinedRange);
    }

    /**
     * Test Case: Second input range is null.
     * Test Strategy: ECP - Null handling
     * Expected Behavior: The method should return the first range.
     */
    @Test
    public void testCombineSecondNullReturnsFirstRange() {
        Range range1 = new Range(3, 8);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, null);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Combining a valid range with null should return the valid range.", range1, combinedRange);
    }

    /**
     * Test Case: Two non-overlapping ranges.
     * Test Strategy: ECP - Non-overlapping ranges
     * Expected Behavior: The method should return a new range covering both inputs.
     */
    @Test
    public void testCombineNonOverlappingRangesMergesCorrectly() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(10, 15);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Non-overlapping ranges should be merged into (1, 15).", new Range(1, 15), combinedRange);
    }

    /**
     * Test Case: Two touching ranges.
     * Test Strategy: BVA - Touching boundaries
     * Expected Behavior: The method should merge them into a single continuous range.
     */
    @Test
    public void testCombineTouchingRangesMerges() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(5, 10);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Touching ranges should merge into (1,10).", new Range(1, 10), combinedRange);
    }

    /**
     * Test Case: Two overlapping ranges.
     * Test Strategy: ECP - Overlapping ranges
     * Expected Behavior: The method should merge the overlapping portions into a single range.
     */
    @Test
    public void testCombineOverlappingRangesMergesCorrectly() {
        Range range1 = new Range(1, 6);
        Range range2 = new Range(4, 10);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Overlapping ranges should merge into (1,10).", new Range(1, 10), combinedRange);
    }

    /**
     * Test Case: Identical ranges.
     * Test Strategy: ECP - Identical ranges
     * Expected Behavior: The method should return the same range.
     */
    @Test
    public void testCombineIdenticalRangesReturnsSameRange() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(1, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Identical ranges should return the same range.", range1, combinedRange);
    }

    /**
     * Test Case: One range is fully inside another.
     * Test Strategy: ECP - Subset range
     * Expected Behavior: The method should return the larger range.
     */
    @Test
    public void testCombineOneRangeInsideAnotherReturnsLargerRange() {
        Range range1 = new Range(1, 10);
        Range range2 = new Range(3, 7);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Range inside another should return the larger range.", range1, combinedRange);
    }

    /**
     * Test Case: Merging when one range has Integer.MIN_VALUE.
     * Test Strategy: BVA - Lower boundary
     * Expected Behavior: The method should correctly handle the extreme lower bound.
     */
    @Test
    public void testCombineLowerBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(Integer.MIN_VALUE, -1);
        Range range2 = new Range(0, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Lower boundary values should merge into (Integer.MIN_VALUE, 5).", new Range(Integer.MIN_VALUE, 5), combinedRange);
    }

    /**
     * Test Case: Merging when one range has Integer.MAX_VALUE.
     * Test Strategy: BVA - Upper boundary
     * Expected Behavior: The method should correctly handle the extreme upper bound.
     */
    @Test
    public void testCombineUpperBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(10, Integer.MAX_VALUE);
        Range range2 = new Range(5, 9);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Upper boundary values should merge into (5, Integer.MAX_VALUE).", new Range(5, Integer.MAX_VALUE), combinedRange);
    }

    /**
     * Test Case: Merging two ranges at the zero boundary.
     * Test Strategy: BVA - Zero boundary
     * Expected Behavior: The method should correctly handle zero-based merging.
     */
    @Test
    public void testCombineZeroBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(-5, 0);
        Range range2 = new Range(0, 5);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Zero boundary values should merge into (-5,5).", new Range(-5, 5), combinedRange);
    }
    
    /**
     * Test Case: Identical single-point ranges.
     * Test Strategy: ECP - Single-point range
     * Expected Behavior: The method should return the same single-point range.
     */
    @Test
    public void testCombineSinglePointRangesIdenticalReturnsSamePoint() {
        Range range1 = new Range(3, 3);
        Range range2 = new Range(3, 3);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Identical single-point ranges (3,3) should return (3,3).",
            new Range(3, 3), combinedRange);
    }

    /**
     * Test Case: Merging two adjacent single-point ranges.
     * Test Strategy: BVA - Adjacent single points
     * Expected Behavior: The method should merge them into a larger range.
     */
    @Test
    public void testCombineSinglePointRangesAdjacentMerges() {
        Range range1 = new Range(3, 3);
        Range range2 = new Range(4, 4);
        Range combinedRange = null;
        try {
            combinedRange = Range.combine(range1, range2);
        } catch (IllegalArgumentException e) {
            fail("Failed to combine the range.");
        }
        assertEquals("Adjacent single-point ranges should merge into (3,4).", new Range(3, 4), combinedRange);
    }
    
    /*
     * Test constrain()
     */
    
    /**
     * Test Case: A normal value within the valid range.
     * Test Strategy: Inside the range
     * Expected Behavior: The method should return the input value as is.
     */
    @Test
    public void testConstrainWithinRangeReturnsSameValue() {
        Range range = new Range(2, 8);
        assertEquals("A value inside the range should return itself.",
            5, range.constrain(5), 0.000000001d);
    }

    /**
     * Test Case: The lower boundary value of the range.
     * Test Strategy: Lower boundary inside the range
     * Expected Behavior: The method should return the lower boundary itself.
     */
    @Test
    public void testConstrainLowerBoundaryReturnsSameValue() {
        Range range = new Range(2, 8);
        assertEquals("The lower boundary should return itself.",
            2, range.constrain(2), 0.000000001d);
    }

    /**
     * Test Case: The upper boundary value of the range.
     * Test Strategy: Upper boundary inside the range
     * Expected Behavior: The method should return the upper boundary itself.
     */
    @Test
    public void testConstrainUpperBoundaryReturnsSameValue() {
        Range range = new Range(2, 8);
        assertEquals("The upper boundary should return itself.",
            8, range.constrain(8), 0.000000001d);
    }

    /**
     * Test Case: A value below the lower boundary.
     * Test Strategy: Below the range
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainBelowRangeReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value below the range should return the lower bound.",
            2, range.constrain(0), 0.000000001d);
    }

    /**
     * Test Case: A significantly lower value, far outside the range.
     * Test Strategy: Far below the range
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainFarBelowRangeReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value far below the range should return the lower bound.",
            2, range.constrain(-5), 0.000000001d);
    }

    /**
     * Test Case: A value above the upper boundary.
     * Test Strategy: Above the range
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainAboveRangeReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value above the range should return the upper bound.",
            8, range.constrain(10), 0.000000001d);
    }

    /**
     * Test Case: A significantly higher value, far outside the range.
     * Test Strategy: Far above the range
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainFarAboveRangeReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value far above the range should return the upper bound.",
            8, range.constrain(100), 0.000000001d);
    }

    /**
     * Test Case: A value just below the lower boundary.
     * Test Strategy: Just below the lower boundary
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainJustBelowLowerBoundaryReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just below the lower boundary should return the lower bound.",
            2, range.constrain(1.9999), 0.000000001d);
    }

    /**
     * Test Case: A value slightly lower than the range.
     * Test Strategy: Lower boundary outside range
     * Expected Behavior: The method should return the lower boundary.
     */
    @Test
    public void testConstrainLowerBoundaryOutsideReturnsLowerBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just outside the lower boundary should return the lower bound.",
            2, range.constrain(1), 0.000000001d);
    }

    /**
     * Test Case: A value just above the upper boundary.
     * Test Strategy: Just above the upper boundary
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainJustAboveUpperBoundaryReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just above the upper boundary should return the upper bound.",
            8, range.constrain(8.0001), 0.000000001d);
    }

    /**
     * Test Case: A value slightly higher than the range.
     * Test Strategy: Upper boundary outside range
     * Expected Behavior: The method should return the upper boundary.
     */
    @Test
    public void testConstrainUpperBoundaryOutsideReturnsUpperBound() {
        Range range = new Range(2, 8);
        assertEquals("A value just outside the upper boundary should return the upper bound.",
            8, range.constrain(9), 0.000000001d);
    }
}
