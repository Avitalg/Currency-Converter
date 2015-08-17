package il.ac.shenkar.CurrencyExchanger;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExchangerTest {

	double val;
	String currFrom;
	String currTo;
	
	@Before
	public void setUp() throws Exception
	{
		currFrom = new String("NIS");
		currTo = new String("USD");
		val = 100;
	}

	@After
	public void tearDown() throws Exception
	{
		currFrom = null;
		currTo = null;
		val = 0;
	}

	@Test
	public void testParseXML() {
		fail("Not yet implemented");
	}

	@Test
	public void testExchange()
	{
		Exchanger ex = new Exchanger();
		double expected = 26.13;
		double result = ex.exchange(val, currFrom, currTo);
		assertEquals("testing result", expected, result, 0.5);
		
	}

	@Test
	public void testReturnDate() {
		fail("Not yet implemented");
	}

}
