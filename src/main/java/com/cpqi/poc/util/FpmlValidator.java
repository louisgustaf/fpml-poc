package com.cpqi.poc.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.camel.Body;
import org.fpml.x2004.fpml41.rec20050714.Fpmlmain41DocumentRootElement;

import biz.c24.io.api.ParserException;
import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.ValidationException;
import biz.c24.io.api.data.ValidationManager;
import biz.c24.io.api.presentation.XMLSource;

public class FpmlValidator {

    /**
     * Method to parse an xml String into a ComplexDataObject
     * 
     * @param xmlString
     */
    public ComplexDataObject parse(String xmlString) throws IOException,
	    ParserException {

	// From a xml File resource
	// FileReader fileReader = new FileReader(fileName);

	// XMLSource src = new XMLSource(fileReader);

	// from a string XML
	InputStream is = new ByteArrayInputStream(xmlString.getBytes());

	XMLSource src = new XMLSource(is);

	ComplexDataObject obj = null;

	// parse the XML, throws a ParserException
	obj = src.readObject(Fpmlmain41DocumentRootElement.getInstance());

	return obj;

    }

    /**
     * Method to check validation rules of an xml String that come as a body in
     * a exchange
     * 
     * @param xmlString
     */

    public void validate(@Body String xmlString) throws ValidationException,
	    IOException {

	// try to parse the XML. Checks for semantic issues
	ComplexDataObject obj = parse(xmlString);

	ValidationManager manager = new ValidationManager();

	// Validate the XML against validation rules, throws a
	// ValidationException
	manager.validateByException(obj);

    }

}
