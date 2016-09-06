package util;

public class SearchResultNameId {

	private String documentName;
	private String documentURI;
	
	
	public SearchResultNameId(String nameP,String idP){
		this.documentName=nameP;
		this.documentURI=idP;
	}


	public String getDocumentName() {
		return documentName;
	}


	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


	public String getDocumentURI() {
		return documentURI;
	}


	public void setDocumentURI(String documentURI) {
		this.documentURI = documentURI;
	}



}
