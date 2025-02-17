package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.*;

public class DataUtilitiesTest_TC_DU_006 {

    private Mockery mockingContext;
    private Values2D values;

    @Before
    public void setUp() {
        // Initialize mocking context
        mockingContext = new Mockery();
        values = mockingContext.mock(Values2D.class);

        // Define expectations for an empty dataset
        mockingContext.checking(new Expectations() {{
            oneOf(values).getColumnCount();
            will(returnValue(0)); // No columns available

            oneOf(values).getRowCount();
            will(returnValue(0)); // No rows available
        }});
    }

    @Test
    public void testCalculateRowTotal_EmptyDataset() {
        // Expected sum for an empty dataset should be 0
        double expectedSum = 0.0;
        double result = DataUtilities.calculateRowTotal(values, 0);

        // Verify the result
        assertEquals("The sum of row 0 for an empty dataset should be 0", expectedSum, result, 0.000000001d);
    }
}
