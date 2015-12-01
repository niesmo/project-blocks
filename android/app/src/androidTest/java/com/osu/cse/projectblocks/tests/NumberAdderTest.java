package com.osu.cse.projectblocks.tests;

import android.test.InstrumentationTestCase;

import com.osu.cse.projectblocks.NumberAdder;

/**
 * Created by niesmo on 11/30/2015.
 */
public class NumberAdderTest extends InstrumentationTestCase {
    @Override
    protected void setUp() throws Exception{
        super.setUp();
    }


    public void testNumberAdder(){
        int res = NumberAdder.addNumbers(3, 5);

        assertEquals(9, res);
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
