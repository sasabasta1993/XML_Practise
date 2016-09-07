package rest;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	//ovaj vraca vezano za odredjeni akt
	@GET
	@Path("/uProceduri/{nazivZakona}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getAktiProcedura(@PathParam("nazivZakona")String name){
		try {
			return amandmanDao.findProposed(name);
		} catch (IOException | JAXBException e) {
			System.out.println("Doslo je do greske!");
			return null;
		}
	}
	
	@GET
	@Path("/glasanjeAmandman/{uri}/{za}/{uzdrani}/{protiv}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String glasanje(@PathParam("uri")String uri, @PathParam("za")String za,
			@PathParam("uzdrani")String uzdrzani, @PathParam("protiv")String protiv){
		try {
			return amandmanDao.voteAmandman(uri, za, uzdrzani, protiv);
		} catch (IOException | JAXBException e) {
			System.out.println("Doslo je do greske!");
			return null;
		}
	}
	
	@POST
	@Path("/remove/{uri}")
	public boolean removeAmandman(@PathParam("uri")String uri)
	{
		try {
			amandmanDao.remove(uri);
		} catch (IOException e) {
			System.out.println("Greska prilikom brisanja akta ili amandmana");
			return false;
		}
		return true;
	}
	
	@PUT
	@Path("/izglasan/{amandmanUri}/{idZakona}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean primeniAmandmanNaAkt(@PathParam("amandmanUri")String amandmanUri, @PathParam("idZakona")String idZakona){
		try {
			amandmanDao.primeniNaAkt(amandmanUri, idZakona);
		} catch (IOException | JAXBException e) {
			System.out.println("Amandman nije spojen sa aktom.");
			return false;
		}
		return true;
	}

}
