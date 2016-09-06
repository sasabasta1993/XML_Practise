package rest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.korisnici.Korisnik;
import model.korisnici.Korisnici;
import dao.KorisnikDaoLocal;


@Path("/korisnik")
public class KorisnikService {

//	private static Logger log = Logger.getLogger(Korisnik.class);	
	@EJB
	KorisnikDaoLocal korisnikDao;
	
	@GET
	@Path("/login/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Korisnik loginUser(@PathParam("username") String username, @PathParam("password") String password) {
		
		Korisnici korisnici = korisnikDao.getUsers();
		Korisnik retVal = null;
		for(Korisnik k:korisnici.getKorisnik())
		{
			if(k.getKorisnickoIme().equals(username) && k.getLozinka().equals(password)){
				retVal = k;
				System.out.println("uspesno ulogovan: " + retVal);
			}
		}
			
		if (retVal == null){
			System.out.println("ne postoji takav korisnik");
		}
		
		return retVal;
		
	}
    
}
