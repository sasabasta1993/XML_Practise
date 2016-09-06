package rest;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import model.amandman.Amandman;
import validation.XMLValidator;
import dao.AmandmanDaoLocal;

@Path("/amandman")
public class AmandmanService {
	
	@EJB
	AmandmanDaoLocal amandmanDao;
	
	@POST
	@Path("/predlozi")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean predloziAmandman(String document){
		
		Object retVal;
		if((retVal = XMLValidator.validateXML("amandman", document)) == null)
			System.out.println("XML nije validan!");
		else{
			try {
				amandmanDao.persist(((Amandman)retVal), ((Amandman)retVal).getKontekst().getReferentniZakon()+"/"+((Amandman)retVal).getNaziv());
			} catch (JAXBException | IOException e) {
				System.out.println("Greska prilikom dodavanja amandmana.");
				return false;
			}
		}
		return true;
	}

}
