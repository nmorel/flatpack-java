package com.getperka.flatpack.gwt.codexes;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.getperka.flatpack.gwt.FlatPackTestCase;
import com.getperka.flatpack.gwt.stub.TestCodexFactory;

public class NumberCodexTestGwt
    extends FlatPackTestCase
{

    public void testBigDecimal()
    {
        Codex<BigDecimal> codex = TestCodexFactory.get().bigDecimalCodex();

        BigDecimal value = BigDecimal.valueOf( Double.MAX_VALUE ).multiply( BigDecimal.valueOf( 1.5 ) );
        testCodex( codex, value );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( BigDecimal.ZERO ));
        assertFalse(codex.isDefaultValue( BigDecimal.ONE ));
    }

    public void testBigInteger()
    {
        Codex<BigInteger> codex = TestCodexFactory.get().bigIntegerCodex();

        BigInteger value = BigInteger.valueOf( Long.MAX_VALUE ).multiply( BigInteger.valueOf( 2 ) );
        testCodex( codex, value );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( BigInteger.ZERO ));
        assertFalse(codex.isDefaultValue( BigInteger.ONE ));
    }

    public void testByte()
    {
        Codex<Byte> codex = TestCodexFactory.get().byteCodex();

        testCodex( codex, new Integer( 34 ).byteValue() );
        testCodex( codex, Byte.valueOf( new Integer( 1255 ).byteValue() ) );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( new Integer( 0 ).byteValue() ));
        assertFalse(codex.isDefaultValue( new Integer( 1 ).byteValue() ));
    }

    public void testShort()
    {
        Codex<Short> codex = TestCodexFactory.get().shortCodex();

        testCodex( codex, new Integer( 1472 ).shortValue() );
        testCodex( codex, Short.valueOf( new Integer( 1544 ).shortValue() ) );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( new Integer( 0 ).shortValue() ));
        assertFalse(codex.isDefaultValue( new Integer( 1 ).shortValue() ));
    }

    public void testInteger()
    {
        Codex<Integer> codex = TestCodexFactory.get().integerCodex();

        testCodex( codex, 1472 );
        testCodex( codex, Integer.valueOf( 544 ) );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( 0 ));
        assertFalse(codex.isDefaultValue( 1 ));
    }

    public void testLong()
    {
        Codex<Long> codex = TestCodexFactory.get().longCodex();

        testCodex( codex, 14725487l );
        testCodex( codex, Long.valueOf( 1541574845154l ) );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( 0l ));
        assertFalse(codex.isDefaultValue( 1l ));
    }

    public void testDouble()
    {
        Codex<Double> codex = TestCodexFactory.get().doubleCodex();

        testCodex( codex, 1472.5487d );
        testCodex( codex, Double.valueOf( 154.1574845154d ) );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( 0d ));
        assertFalse(codex.isDefaultValue( 1d ));
    }

    public void testFloat()
    {
        Codex<Float> codex = TestCodexFactory.get().floatCodex();

        testCodex( codex, Double.valueOf( 1472.5487d ).floatValue() );
        testCodex( codex, Float.valueOf( Double.valueOf( 154.1574845154d ).floatValue() ) );
        testCodex( codex, null );

        assertTrue(codex.isDefaultValue( null ));
        assertTrue(codex.isDefaultValue( 0f ));
        assertFalse(codex.isDefaultValue( 1f ));
    }

    public void testNumberIncorrectType() throws Exception
    {
        Codex<Integer> codex = TestCodexFactory.get().integerCodex();

        try
        {
            codex.readNotNull( "er", deserializationContext() );
            fail();
        }
        catch ( IllegalArgumentException e )
        {
        }
    }
}
