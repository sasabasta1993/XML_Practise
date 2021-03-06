package util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class XMLUtil {
	
	public static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	public static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZOPF10235876";
	public static boolean validateSchema(Document document, String schemaPath){
    	try{
	    	SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
	    	Schema schema = factory.newSchema(new URL(schemaPath));
	    	Validator validator = schema.newValidator();
	    	validator.validate(new DOMSource(document));

    	}catch(Exception e){
    		//ukoliko se uhvati izuzetak, to znaci da faktura nije validna po semi.
    		System.out.println("Nije validna po shemi");
    		e.printStackTrace();
    		return false;
    	}
    	//ukoliko ne dodje do izuzetka, znaci da je faktura validna po semi.
    	return true;
    }
	
	public static String randomString( int len ) 
	{
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder( len );
		for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		return sb.toString();
	}
	
	public static Document buildDocument(String fileName) {
		Document document = null;
		// Kreira se DocumentBuilderFactory klasa, metoda je staticka
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			// factory.setValidating(true);
			factory.setNamespaceAware(true);
			// validacija XML scheme
			factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

			// Kreira DocumentBuilder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parsiramo ulaz, tj. XML file
			document = builder.parse(new File(fileName));

			if (document != null)
				System.out.println("UtilFile parsed with no errors \n");
			else
				System.out.println("Document is null \n");

		}
		// Hvatamo sve moguce exception-e koji se mogu desiti (SVI su preuzeti
		// iz SAX-a)
		// greska usled parsiranja
		catch (SAXParseException spe) {
			// Error generated by the parser
			System.out.println("\n** Parsing error" + ", line "
					+ spe.getLineNumber() + ", uri " + spe.getSystemId());
			System.out.println("   " + spe.getMessage());

			// Use the contained exception, if any
			Exception x = spe;
			if (spe.getException() != null)
				x = spe.getException();
			x.printStackTrace();
		}
		// obrada SAXException-a
		// obicno ih kreira handler ili prilikom inicijalizacije parsera
		catch (SAXException sxe) {
			// Error generated during parsing)
			Exception x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
		}
		// ako postoje problemi u kreiranju parsera za zahtevanim opcijama
		// zahteva se nesto, a parser nije u stanju da to nesto podrzi
		// npr. rad sa XML semom
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			// I/O error
			ioe.printStackTrace();
		}

		return document;
	}

}
