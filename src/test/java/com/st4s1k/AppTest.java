package com.st4s1k;

import static org.junit.Assert.assertTrue;

import com.st4s1k.binary.BinaryWord;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testOperator_Or()
    {
        BinaryWord bw1 = new BinaryWord();
        BinaryWord bw2 = new BinaryWord();

        bw1.setBit(255);
        bw2.setBit(256);

        System.out.println(bw1);
        System.out.println(bw2);
        System.out.println(bw1.or(bw2));
        assertTrue( true );
    }
}
