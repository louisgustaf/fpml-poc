package com.cpqi.poc.util;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Before;
import org.junit.Test;

import biz.c24.io.api.ParserException;
import biz.c24.io.api.data.ValidationException;


/**
 *  Test class used to check standard behaviour C24 component FpmlValidator class
 *
 * </pre>
 */
public class FpmlValidatorTest{
	
	private CamelContext context;
	
	@Before
	public void setUp(){
		//Since we are using C24 FpML component, even though being free, we need to load a license for it
		File file = new File("license-ads.dat");
		System.setProperty("IO_HOME", file.getAbsoluteFile().getParentFile().getAbsolutePath());
		//Create a camel context to read a XML file into a String. 
		context = new DefaultCamelContext();
	}
	
	 /**
     * Test to verify if an xml String is being converted into a ComplexDataObject
	 * The parser verify for structure issues on the xml file.
     */	
	@Test
	public void parseSuccessTest() throws Exception{
		
		File testXml = new File("data/test/valid-1.xml");
		String xmlString = context.getTypeConverter().convertTo(String.class,
				testXml);
		
		FpmlValidator fpmlValidator = new FpmlValidator();
        // We expect a exception to be thrown here...
		// The parse method will try to convert the xml String into a ComplexDataObject
		fpmlValidator.parse(xmlString);
		
	}
	
	 /**
     * Test to verify if a faulty xml String, missing a tag, is being converted into a ComplexDataObject
	 * For this test to succeed a ParserException should be thrown
	 * The parser process verifies for structure issues on the xml file.
     */	
	@Test(expected=ParserException.class)
	public void parseFailTest() throws Exception{
		
		File testXml = new File("data/test/invalid-Unparseable-1.xml");
		String xmlString = context.getTypeConverter().convertTo(String.class,
				testXml);
		
		FpmlValidator fpmlValidator = new FpmlValidator();
        // We expect a ParserException to be thrown here...
		// The parse method will try to convest the faulty xml String into a ComplexDataObject
		fpmlValidator.parse(xmlString);
	
	}
	
	/**
     * Test to verify if a xml is in accord to all validation rules.
	 * The validation process verifies for rules violations issues on the xml file.
     */	
	@Test
	public void validateSuccessTest() throws Exception{
		
		File testXml = new File("data/test/valid-1.xml");
		String xmlString = context.getTypeConverter().convertTo(String.class,
				testXml);
		
		FpmlValidator fpmlValidator = new FpmlValidator();
		// an exception should *not* be thrown here
		//The validate method will try to parse the xml String into a ComplexDataObject and after that  check the validation rules
		fpmlValidator.validate(xmlString);
		
	}
	
	/**
     * Test to verify if a xml with a rule violation is not validated
	 * For this test succeed a ValidationException should be thrown
     */	
	@Test(expected=ValidationException.class)
	public void validateFailTest() throws Exception{
		
		File testXml = new File("data/test/invalid-ValidationRule-1.xml");
		String xmlString = context.getTypeConverter().convertTo(String.class,
				testXml);
		
		FpmlValidator fpmlValidator = new FpmlValidator();
		// We expect a ValidationException to be thrown here.
		//The validate method will try to parse the faulty xml String into a ComplexDataObject and after that  check the validation rules
		fpmlValidator.validate(xmlString);
		
	}
	
}
