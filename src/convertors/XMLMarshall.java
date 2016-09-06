package convertors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import model.akt.Akt;

public class XMLMarshall {
	
	public static InputStream objectoToXML(Object ofc)
	{
		InputStream retVal = null;
		try{
			if(ofc instanceof Akt)
			{
				JAXBContext context = JAXBContext.newInstance("model.akt");
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				marshaller.marshal(ofc, baos);
				
				retVal = new ByteArrayInputStream(baos.toByteArray());
				return retVal;
				
			}else { 
				JAXBContext context = JAXBContext.newInstance("model.amandman");
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				
				//marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				marshaller.marshal(ofc, baos);
				
				retVal = new ByteArrayInputStream(baos.toByteArray());
				return retVal;
				
			}
		}catch(Exception e)
		{
			return null;
		}
	}
		public static void objectToFile(Object ofc)
		{
			try{
				if(ofc instanceof Akt)
				{
					JAXBContext context = JAXBContext.newInstance("model.akt");
					Marshaller marshaller = context.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					
					marshaller.marshal(ofc, new FileOutputStream(new File("temp.xml")));
				}else
				{
					JAXBContext context = JAXBContext.newInstance("model.amandman");
					Marshaller marshaller = context.createMarshaller();
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					//marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
					marshaller.marshal(ofc, new FileOutputStream(new File("temp.xml")));
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
