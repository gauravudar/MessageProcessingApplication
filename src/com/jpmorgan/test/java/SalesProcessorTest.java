package com.jpmorgan.test.java;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import com.jpmorgan.main.java.SalesProcessor;
import junit.framework.TestCase;

public class SalesProcessorTest extends TestCase {
	private SalesProcessor salesProcessor;	

	public void setUp() throws Exception {
		super.setUp();
		salesProcessor = new SalesProcessor();	
	}
	
	public void testGenerateAndPrintTradeReport() throws Exception {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		salesProcessor.readInputMessageDataAndPrintReports(new FileReader("testmessages/inputData.txt"));
		assertTrue(out.toString().contains("***************Adjustment Report after 50 sales *****************"));	
	}

	public void testGenerateAndPrintTradeReportWithWrongFilePath() throws Exception {
		try {
			salesProcessor.readInputMessageDataAndPrintReports(new FileReader("testmessages/wrongInputData.txt"));
		}
		catch (Throwable expected) {
			assertEquals(NullPointerException.class, expected.getClass());
		}
	}
	
}