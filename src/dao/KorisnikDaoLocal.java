package dao;

import model.korisnici.Korisnici;


public interface KorisnikDaoLocal extends GenericDaoLocal<Object, String> {
	
	public Korisnici getUsers();
	
}
