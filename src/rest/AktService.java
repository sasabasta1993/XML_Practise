package rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import model.akt.Akt;
import validation.XMLValidator;
import dao.AktDaoLocal;

@Path("/akt")
public class AktService {
	
	@EJB
	AktDaoLocal aktDao;
	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean predloziAkt(String document) throws JAXBException, IOException
	{
		Object retVal;
		if((retVal = XMLValidator.validateXML("akt", document)) == null)
			System.out.println("XML nije validan!");
		else
		{
			try {
				aktDao.persist(((Akt)retVal),  String.valueOf(((Akt)retVal).getId()));
			} catch (JAXBException | IOException e) {
				System.out.println("Greska prilikom dodavanja novog akta");
				return false;
			}
		}
		return true;
	}
	
	
	
	@GET
	@Path("/pretraga/{dateFrom}/{dateTo}") 
	@Produces(MediaType.APPLICATION_JSON)                                                                                              
	public List<Object> pretragaPoMetaPodacima(@PathParam("dateFrom")String dateFrom,@PathParam("dateTo")String dateTo)
	{
		List<Object> retVal = null;
		try {
			retVal = new ArrayList<Object>(aktDao.findByMetaData(dateFrom, dateTo));
		} catch (IOException e) {
			System.out.println("Doslo je do greske prilikom pretrage. Pokusajte ponovo.");
		}
		return retVal;
	}
	
	
	@GET
	@Path("/pretraga2/{dateFrom}/{dateTo}") 
	@Produces(MediaType.APPLICATION_JSON)                                                                                              
	public List<Object> pretragaPoMetaPodacima2(@PathParam("dateFrom")String dateFrom,@PathParam("dateTo")String dateTo)
	{
		List<Object> retVal = null;
		try {
			retVal = new ArrayList<Object>(aktDao.findByMetaData2(dateFrom, dateTo));
		} catch (IOException e) {
			System.out.println("Doslo je do greske prilikom pretrage. Pokusajte ponovo.");
		}
		return retVal;
	}
	
	
	
	
	@GET
	@Path("/pretraga/{keyword}")  //proveriti jos da li radi kako treba.. msm da ne
	@Produces(MediaType.APPLICATION_JSON)                                                                                              
	public List<Object> pretragaPoKljucnojReci(@PathParam("keyword")String keyword)
	{
		List<Object> retVal = null;
		try {
			retVal = new ArrayList<Object>(aktDao.findByKeyWord(keyword));
		} catch (IOException e) {
			System.out.println("Doslo je do greske prilikom pretrage. Pokusajte ponovo.");
		}
		return retVal;
	}
	
	
	@GET
	@Path("/html/{uri}")
	@Produces(MediaType.TEXT_HTML)
	public String getHTMLAkt(@PathParam("uri")String uri) {
		System.out.println(uri);
		try {
			return (String)aktDao.findById(uri, "akt");
		} catch (JAXBException | IOException e) {
			System.out.println("Greska prilikom prikaza akt html-a");
			return null;
		} 
	}
	
	
	@GET
	@Path("/uProceduri")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getAktiProcedura(){
		try {
			return aktDao.find();
		} catch (IOException | JAXBException e) {
			System.out.println("Doslo je do greske!");
			return null;
		}
	}
	
	@GET
	@Path("/glasanjeAkt/{id}/{za}/{uzdrani}/{protiv}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String glasanje(@PathParam("id")String id, @PathParam("za")String za,
			@PathParam("uzdrani")String uzdrzani, @PathParam("protiv")String protiv){
		try {
			return aktDao.vote(id, za, uzdrzani, protiv);
		} catch (IOException | JAXBException e) {
			System.out.println("Doslo je do greske!");
			return null;
		}
	}
}
