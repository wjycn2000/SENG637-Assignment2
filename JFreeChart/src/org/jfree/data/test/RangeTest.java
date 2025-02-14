package org.jfree.data.test;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
	// Testing method Range.combine()
    // Test case: Both ranges are null
    @Test
    public void testCombineBothNullReturnsNull() {
        assertNull("Combining two null ranges should return null.", Range.combine(null, null));
    }

    // Test case: First range is null, should return the second range
    @Test
    public void testCombineFirstNullReturnsSecondRange() {
        Range range2 = new Range(2, 5);
        assertEquals("Combining null with a valid range should return the valid range.",
            range2, Range.combine(null, range2));
    }

    // Test case: Second range is null, should return the first range
    @Test
    public void testCombineSecondNullReturnsFirstRange() {
        Range range1 = new Range(3, 8);
        assertEquals("Combining a valid range with null should return the valid range.",
            range1, Range.combine(range1, null));
    }

    // Test case: Two non-overlapping ranges should merge into one larger range
    @Test
    public void testCombineNonOverlappingRangesMergesCorrectly() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(10, 15);
        assertEquals("Non-overlapping ranges should be merged into (1, 15).",
            new Range(1, 15), Range.combine(range1, range2));
    }

    // Test case: Two touching ranges should merge into a continuous range
    @Test
    public void testCombineTouchingRangesMerges() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(5, 10);
        assertEquals("Touching ranges (1,5) and (5,10) should be merged into (1,10).",
            new Range(1, 10), Range.combine(range1, range2));
    }

    // Test case: Two overlapping ranges should merge into a larger range
    @Test
    public void testCombineOverlappingRangesMergesCorrectly() {
        Range range1 = new Range(1, 6);
        Range range2 = new Range(4, 10);
        assertEquals("Overlapping ranges (1,6) and (4,10) should be merged into (1,10).",
            new Range(1, 10), Range.combine(range1, range2));
    }

    // Test case: Identical ranges should return the same range
    @Test
    public void testCombineIdenticalRangesReturnsSameRange() {
        Range range1 = new Range(1, 5);
        Range range2 = new Range(1, 5);
        assertEquals("Identical ranges (1,5) should return the same range (1,5).",
            range1, Range.combine(range1, range2));
    }

    // Test case: One range is fully inside another, should return the larger range
    @Test
    public void testCombineOneRangeInsideAnotherReturnsLargerRange() {
        Range range1 = new Range(1, 10);
        Range range2 = new Range(3, 7);
        assertEquals("Range (3,7) inside (1,10) should return the larger range (1,10).",
            range1, Range.combine(range1, range2));
    }

    // Test case: Merging with Integer.MIN_VALUE as the lower boundary
    @Test
    public void testCombineLowerBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(Integer.MIN_VALUE, -1);
        Range range2 = new Range(0, 5);
        assertEquals("Lower boundary values should be merged into (Integer.MIN_VALUE, 5).",
            new Range(Integer.MIN_VALUE, 5), Range.combine(range1, range2));
    }

    // Test case: Merging with Integer.MAX_VALUE as the upper boundary
    @Test
    public void testCombineUpperBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(10, Integer.MAX_VALUE);
        Range range2 = new Range(5, 9);
        assertEquals("Upper boundary values should be merged into (5, Integer.MAX_VALUE).",
            new Range(5, Integer.MAX_VALUE), Range.combine(range1, range2));
    }

    // Test case: Merging two ranges at zero boundary
    @Test
    public void testCombineZeroBoundaryValuesMergeCorrectly() {
        Range range1 = new Range(-5, 0);
        Range range2 = new Range(0, 5);
        assertEquals("Zero boundary values (-5,0) and (0,5) should be merged into (-5,5).",
            new Range(-5, 5), Range.combine(range1, range2));
    }

    // Test case: Merging identical single-point ranges should return the same point
    @Test
    public void testCombineSinglePointRangesIdenticalReturnsSamePoint() {
        Range range1 = new Range(3, 3);
        Range range2 = new Range(3, 3);
        assertEquals("Identical single-point ranges (3,3) should return (3,3).",
            new Range(3, 3), Range.combine(range1, range2));
    }

    // Test case: Merging adjacent single-point ranges should create a larger range
    @Test
    public void testCombineSinglePointRangesAdjacentMerges() {
        Range range1 = new Range(3, 3);
        Range range2 = new Range(4, 4);
        assertEquals("Adjacent single-point ranges (3,3) and (4,4) should merge into (3,4).",
            new Range(3, 4), Range.combine(range1, range2));
    }
}
