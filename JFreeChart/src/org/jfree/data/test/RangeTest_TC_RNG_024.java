package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

import java.util.HashSet;
import java.util.Set;

public class RangeTest_TC_RNG_024 {

    private static final int LARGE_DATASET_SIZE = 100000;

    @Test(timeout = 10000) // Ensure execution completes within 10 seconds
    public void testHashCode_WithLargeDataset() {
        long startTime = System.nanoTime();

        Set<Integer> hashCodes = new HashSet<>();
        boolean hasCollision = false;

        // Generate 100,000 unique Range objects with incrementing values
        for (int i = 0; i < LARGE_DATASET_SIZE; i++) {
            Range range = new Range(i, i + 10.0);
            int hash = range.hashCode();

            // Check for hash collisions
            if (!hashCodes.add(hash)) {
                hasCollision = true;
                System.out.println("Collision detected for Range(" + i + ", " + (i + 10.0) + ") with hash: " + hash);
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        // Print execution time for performance evaluation
        System.out.println("Execution time for generating 100,000 hash codes: " + duration + " ms");

        // Verify efficiency and uniqueness
        assertFalse("Hash codes should be unique across large datasets", hasCollision);
        assertTrue("Execution time should be within acceptable limits", duration < 10000);
    }
}
