package util;

public class AmandmanGetter {
	
	/**
	 *  Vraca elemente iz liste koji odgovaraju amandmanu vezanom za trazeni akt.
	 *  
	 *  @author Marko
	 */
	
	private String nazivAmandmana;
	private String kontekst;
	private String documentURI;
	
	public AmandmanGetter(String nazivAmandmana, String kontekst, String documentURI){
		this.nazivAmandmana = nazivAmandmana;
		this.kontekst = kontekst;
		this.documentURI = documentURI;
	}

	public String getNazivAmandmana() {
		return nazivAmandmana;
	}

	public void setNazivAmandmana(String nazivAmandmana) {
		this.nazivAmandmana = nazivAmandmana;
	}

	public String getKontekst() {
		return kontekst;
	}

	public void setKontekst(String kontekst) {
		this.kontekst = kontekst;
	}

	public String getDocumentURI() {
		return documentURI;
	}

	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}

}
