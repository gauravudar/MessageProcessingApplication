package com.jpmorgan.test.java;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.jpmorgan.main.java.SalesProcessor;

import junit.framework.TestCase;

public class SalesProcessorTest extends TestCase {
	private SalesProcessor salesProcessor;	
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	
	@Test(expected = FileNotFoundException.class)
	public void testGenerateAndPrintTradeReportWithWrongFilePath() throws Exception {
		thrown.expect(Exception.class);
		thrown.expectMessage("The system can not find the file specified");
		salesProcessor.readInputMessageDataAndPrintReports(new FileReader("testmessages/wrontInputData.txt"));
	}
	
}