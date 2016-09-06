package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transformations {
	
	public Transformations() {
		super();
	}

	@Deprecated
	public static void aktToHTML(String xmlName) {
		try {
	        TransformerFactory factory = TransformerFactory.newInstance();
	        Source xslt = new StreamSource(new File("data/akt.xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        Source text = new StreamSource(new File("data/"+xmlName+".xml"));
	        
	        transformer.transform(text, new StreamResult(new File("data/xml_to_html/"+xmlName+".html")));
	        System.out.println(xmlName + " uspesno transformisan u html!");
	        
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static String docToHTMLStream(InputStream xmlName, String tip) throws UnsupportedEncodingException {
		try {//dodati eventualno da se parsira html da se ne prikazuje ID
	        TransformerFactory factory = TransformerFactory.newInstance();
	        
	        Source xslt = new StreamSource(new File("../webapps/xws_xmldb/data/"+tip+".xsl"));
	        Transformer transformer = factory.newTransformer(xslt);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        
	        Source text = new StreamSource(xmlName);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        transformer.transform(text, new StreamResult(baos));
	        
	        System.out.println("Uspesno transformisano u html!");
	        return baos.toString("UTF-8");
	        
		} catch (TransformerConfigurationException e) {			
			e.printStackTrace();
			return "error";
		} catch (TransformerException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
