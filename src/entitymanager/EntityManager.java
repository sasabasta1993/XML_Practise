package entitymanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import model.akt.Akt;
import model.amandman.Amandman;
import model.korisnici.Korisnici;
import util.AmandmanGetter;
import util.Transformations;
import util.SearchResultsUtil;
import util.StringConstants;
import util.Util;
import util.Util.ConnectionProperties;
import util.CollectionConstants;
import util.OperationType;
import util.SearchResultNameId;

import com.fasterxml.jackson.databind.JsonNode;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.marklogic.client.semantics.SPARQLMimeTypes;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;
import com.marklogic.client.eval.EvalResult;

import convertors.XMLMarshall;

public class EntityManager<T, ID extends Serializable> {

	private static DatabaseClient client;
	private static ConnectionProperties props;
	
	public EntityManager()
	{
		super();
		try {
			props = Util.loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (props.database.equals("")) {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Korisnici getKorisnici() throws JAXBException
	{
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		JAXBContext context = JAXBContext.newInstance("model.korisnici");
		JAXBHandle content = new JAXBHandle(context);
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		xmlManager.read("korisnici.xml", metadata, content);
		Korisnici doc = (Korisnici)content.get();
		return doc;
	}
	
	public List<Object> findByKeyWord(String keyword) throws IOException
	//proveriti, srediti da radi kako treba
	{
		List<Object> pronadjeni = new ArrayList<Object>();
		QueryManager queryManager = client.newQueryManager();
		
		// A StringQueryDefinition represents the criteria associated with a simple string query.
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		
		// postavljam kriterijum pretrage
		queryDefinition.setCriteria(keyword);
		queryDefinition.setCollections(CollectionConstants.akti);
		
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle()); //vraca set rezultata sa servera
		
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();

		MatchDocumentSummary result;
		
		EvalResultIterator response = null;
		
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			
			ServerEvaluationCall invoker = client.newServerEval();
			String query = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";"
						   + "let $x := fn:doc(\"###\")/sk:Akt/sk:Naslov return fn:string($x)";
			query = query.replace("###",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			String name = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					System.out.println(rs.getString());
					name = rs.getString();
				}
			}
			
			invoker = client.newServerEval();
			query = StringConstants.getExecutable(OperationType.listCollections);
			query = query.replace("replace1",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			String collection = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					if(!rs.getString().equals(CollectionConstants.amandmani))
					{
						collection = rs.getString();
						collection = collection.replace("_", " ");
						collection = collection.toUpperCase();
					}
				}
			}
			pronadjeni.add(new SearchResultsUtil(name,result.getUri(),collection));
		}
		
		return pronadjeni;
	}
	
	@SuppressWarnings("rawtypes")
	public Object findById(String id, String tipDokumenta) throws IOException, JAXBException, UnsupportedEncodingException
	{
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		JAXBContext context = JAXBContext.newInstance("model."+tipDokumenta);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		
		JAXBHandle content = new JAXBHandle(context);
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		xmlManager.read(id, metadata, content);
		
		Object doc = (Object)content.get();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(doc, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		String retVal = Transformations.docToHTMLStream(is, tipDokumenta);
		//is.close();
		
		return retVal;
	}
	
	public void persist(T entity, String id) throws IOException, SAXException, TransformerException
	{
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		InputStreamHandle handle = new InputStreamHandle(XMLMarshall.objectoToXML(entity));
		
		if(entity instanceof Akt)
		{
			DocumentMetadataHandle metadata = new DocumentMetadataHandle();
			metadata.getCollections().add(CollectionConstants.aktProcedura);
			metadata.getCollections().add(CollectionConstants.akti);
			
			//srediti jos ovaj try/catch blok, brljavi oko id-a
			try{
				DocumentDescriptor dsc = xmlManager.exists(id);
				System.out.println(dsc.getUri());
				System.out.println("Vec postoji akt za zadatim ID-em!");
			}catch(Exception e)
			{
				System.out.println("3");
				xmlManager.write(id ,metadata ,handle);

				System.out.println("Akt je sacuvan u bazu podataka.");
				XMLMarshall.objectToFile(entity);

			}
		}else if(entity instanceof Amandman){
			
			DocumentMetadataHandle metadata = new DocumentMetadataHandle();
			metadata.getCollections().add(CollectionConstants.amandmanProcedura);
			metadata.getCollections().add(CollectionConstants.amandmani);
			DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml");
			DocumentDescriptor desc = xmlManager.create(template, metadata, handle);
			
		}
		
		
		
	}
	//metoda koja vraca akte u zavisnosti da li su u proceduri ili usvojeni
	//edit: trenutno radi samo za aktove u proceduri
	public List<Object> find() {
		
		List<Object> proc = new ArrayList<Object>();

		QueryManager queryManager = client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		queryDefinition.setCollections(CollectionConstants.aktProcedura);
		
		// Set the criteria
		String criteria = "";
		queryDefinition.setCriteria(criteria);
		
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();
		System.out.println(matches.length);
		MatchDocumentSummary result;
		
		EvalResultIterator response = null;
		
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			
			//detaljnije o SEC na: https://docs.marklogic.com/javadoc/client/com/marklogic/client/eval/ServerEvaluationCall.html
			ServerEvaluationCall invoker = client.newServerEval();
			
			/*	ovako bi pisao u konzoli marklogic-a:
			 * 
			 *  xquery version "1.0-ml";
			 *	declare namespace sk="http://www.ftn.uns.ac.rs/skupstina";
			 *	let $x := fn:doc("primerID")/sk:Akt/sk:Naslov
			 *	return
			 *	fn:string($x);
			 *
			 *	vracam string (x) od naslova akta sa zadatim ID-em, 
			 */
			
			String query = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";"
						   + "let $x := fn:doc(\"###\")/sk:Akt/sk:Naslov return fn:string($x)";
			query = query.replace("###",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			String name = "";
			//kupim naslove
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					name = rs.getString();
				}
			}
			
			invoker = client.newServerEval();
			query = StringConstants.getExecutable(OperationType.listCollections);
			query = query.replace("replace1",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			String collection = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					
					collection = rs.getString();
					collection = collection.replace("_", " ");
					collection = collection.toUpperCase();
				}
			}
			proc.add(new SearchResultsUtil(name, result.getUri(), collection));
		}
		
		return proc;
		
	}
	
	
	
	public List<Object> findByMetaData2(String dateFrom, String dateTo) throws IOException{
		
		List<Object> proc = new ArrayList<Object>();

		
		
		//EvalResultIterator response = null;
		
		
		ServerEvaluationCall invoker = client.newServerEval();
		
		// Read the file contents into a string object
		String query = readFile("../webapps/xws_xmldb/data/xquery/findByDate.sqy", StandardCharsets.UTF_8);
		query = query.replace("dateFromParam",dateFrom);
		query = query.replace("dateToParam",dateTo);
		
		
		System.out.println(query);
		// Invoke the query
		invoker.xquery(query);
		
		// Interpret the results
		EvalResultIterator response = invoker.eval();

		System.out.print("[INFO] Response: ");
		ArrayList<String> lista=new ArrayList<String>();
		if (response.hasNext()) {

			for (EvalResult result :response) {
			
						lista.add(result.getString());
				
				System.out.println("\n" + result.getString());
			}
			
			
			for(int i=0;i<lista.size();i=i+2){
				
				proc.add(new SearchResultNameId(lista.get(i+1),lista.get(i)));
				
			}
		} else { 		
			System.out.println("your query returned an empty sequence.");
		}
		
		// Release the client
		//client.release();
		
		System.out.println("[INFO] End.");
		
			

		return proc;
		
	}
	
	/*
	 * Funkcija za pretrazivanje akata po metapodacima (datum)
	 * */
	public List<Object> findByMetaData(String dateFrom, String dateTo) throws IOException
	{
		System.out.println("findByMetaData");
		List<Object> arl = new ArrayList<Object>();
		System.out.println("1");
		String query = "PREFIX xsi: <http://www.w3.org/2001/XMLSchema#>"
				+ "SELECT * FROM <grafovi> WHERE { "
				+ "?s <http://www.ftn.uns.ac.rs/skupstina/predpredlozen> ?date ."
				+ "FILTER ( ?date >= \""+dateFrom+"\"^^xsi:date && ?date < \""+dateTo+"\"^^xsi:date)}";
		
		SPARQLQueryManager sparqlQueryManager = client.newSPARQLQueryManager();
		SPARQLQueryDefinition sqlQuery = sparqlQueryManager.newQueryDefinition(query);
		System.out.println("2");
		JacksonHandle resultsHandle = new JacksonHandle();
		resultsHandle.setMimetype(SPARQLMimeTypes.SPARQL_JSON);
		resultsHandle = sparqlQueryManager.executeSelect(sqlQuery, resultsHandle);
		EvalResultIterator response = null;
		JsonNode tuples = resultsHandle.get().path("results").path("bindings");
		System.out.println(tuples.toString());
		for ( JsonNode row : tuples ) {
			
			String subject = row.path("s").path("value").asText();
			String[] uri = subject.split("/");
			System.out.println(uri[uri.length-1]+"zakonzakonzakon");
			
			ServerEvaluationCall invoker = client.newServerEval();
			String xQuery = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";"
						   + "let $x := fn:doc(\"###\")/sk:Akt/sk:Naslov return fn:string($x)";
			xQuery = xQuery.replace("###",uri[uri.length-1]);
			invoker.xquery(xQuery);
			response = invoker.eval();
			String name = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					System.out.println(rs.getString());
					name = rs.getString();
				}
			}
			invoker = client.newServerEval();
			query = StringConstants.getExecutable(OperationType.listCollections);
			query = query.replace("replace1",uri[uri.length-1]);
			invoker.xquery(query);
			response = invoker.eval();
			String collection = "";
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					if(!rs.getString().equals(CollectionConstants.amandmani))
					{
						collection = rs.getString();
						collection = collection.replace("_", " ");
						collection = collection.toUpperCase();
					}
				}
			}
			arl.add(new SearchResultsUtil(name,uri[uri.length-1],collection));
		}
		
		return arl;
	}
	
	
	public String vote(String id, String za, String uzdrzani, String protiv){
		int glasZa = Integer.parseInt(za);
		int glasUzdrzan = Integer.parseInt(uzdrzani);
		int glasProtiv = Integer.parseInt(protiv);
		
		String retVal = null;
		
		System.out.println(id + " " + glasZa + " " + glasUzdrzan + " " + glasProtiv );
		
		if ((glasUzdrzan + glasProtiv ) >= glasZa){
			retVal = "Akt nije usvojen. Nije bilo vecinske podrske.";
			return retVal;
		} else {
			
			QueryManager queryManager = client.newQueryManager();
			StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
			queryDefinition.setCollections(CollectionConstants.aktProcedura);
			
			String criteria = "";
			queryDefinition.setCriteria(criteria);
			SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
			
			MatchDocumentSummary matches[] = results.getMatchResults();
			MatchDocumentSummary result;
			EvalResultIterator response = null;
			
			for (int i = 0; i < matches.length; i++) {
				//sve ovo ima detaljno objasnjeno u metodi find (tacno tri skrola na gore) :)
				result = matches[i];
				ServerEvaluationCall invoker = client.newServerEval();
				
				if (result.getUri().toString().equals(id)){
					
					String query = "declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";"
							+"xdmp:document-set-collections(\"###\", (\"akti\", \"prihvacen_akt\"))";
					query = query.replace("###",result.getUri());
					invoker.xquery(query);
					response = invoker.eval();
					retVal = "Akt je prihvacen, prostim vecinskim glasanjem."
							+ " Akt je sada prihvacen u nacelu. Bice prihvacen u celosti nakon"
							+ " glasanja za pojedinacne amandmane koji se odnose na ovaj akt.";
					break;
				} else{
					retVal = "Trazeni akt nije stavljen u proceduru.";
				}
			}
		}
		return retVal;
	}
	
	/**
	 * Convenience method for reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	//metoda koja vraca sve predlozene amandmane za trazeni akt
	public List<Object> findProposed(String name) {
		
		List<Object> listaAmandmana = new ArrayList<Object>();

		QueryManager queryManager = client.newQueryManager();
		
		// Query definition is used to specify Google-style query string
		StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
		queryDefinition.setCollections(CollectionConstants.amandmanProcedura);
		
		// Set the criteria
		String criteria = "";
		queryDefinition.setCriteria(criteria);
		
		SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
		
		// Serialize search results to the standard output
		MatchDocumentSummary matches[] = results.getMatchResults();
		System.out.println(matches.length);
		MatchDocumentSummary result;
		
		EvalResultIterator response = null;
		
		for (int i = 0; i < matches.length; i++) {
			result = matches[i];
			
			//detaljnije o SEC na: https://docs.marklogic.com/javadoc/client/com/marklogic/client/eval/ServerEvaluationCall.html
			ServerEvaluationCall invoker = client.newServerEval();
			
			/*	ovako bi pisao u konzoli marklogic-a:
			 * 
			 *	declare namespace sk="http://www.ftn.uns.ac.rs/skupstina";
			 *	let $x := fn:doc("primerID")/am:Amandman/am:Kontekst/@Referentni_zakon
			 *  let $y := fn:doc("9827906689336955896.xml")/am:Amandman/@Naziv
 			 *  let $z := fn:doc("9827906689336955896.xml")/am:Amandman/am:Kontekst
			 *	return (fn:string($x), fn:string ($y), fn:string ($z))
			 */
			
			String query = "declare namespace am = \"http://www.ftn.uns.ac.rs/amandman\";"
						   + "let $x := fn:doc(\"###\")/am:Amandman/am:Kontekst/@Referentni_zakon"
						   + " let $y := fn:doc(\"###\")/am:Amandman/@Naziv"
						   + " let $z := fn:doc(\"###\")/am:Amandman/am:Kontekst"
						   + " return (fn:string($x), fn:string ($y), fn:string ($z))";
			

			query = query.replace("###",result.getUri());
			invoker.xquery(query);
			response = invoker.eval();
			
			ArrayList<String> podaci = new ArrayList<String>();
			ArrayList<String> sviIzKolekcije = new ArrayList<String>();
			
			if (response.hasNext()) {
				for (EvalResult rs : response) {
					String a = rs.getString();
					System.out.println(a);
					sviIzKolekcije.add(rs.getString());
				}
				for(int j=0; j<sviIzKolekcije.size(); j=j+3){
					if((sviIzKolekcije.get(j)).equals(name)){
						
						listaAmandmana.add(new AmandmanGetter(sviIzKolekcije.get(j+1), 
									sviIzKolekcije.get(j+2), result.getUri()));
					}
				}
			}
			listaAmandmana.add(podaci);
		}
		
		return listaAmandmana;
		
	}
	
	public String voteAmandman(String id, String za, String uzdrzani, String protiv){
		int glasZa = Integer.parseInt(za);
		int glasUzdrzan = Integer.parseInt(uzdrzani);
		int glasProtiv = Integer.parseInt(protiv);
		
		String retVal = null;
		
		System.out.println(id + " " + glasZa + " " + glasUzdrzan + " " + glasProtiv );
		
		if ((glasUzdrzan + glasProtiv ) >= glasZa){
			retVal = "Amandman nije usvojen. Nije bilo vecinske podrske.";
			return retVal;
		} else {
			
			QueryManager queryManager = client.newQueryManager();
			StringQueryDefinition queryDefinition = queryManager.newStringDefinition();
			queryDefinition.setCollections(CollectionConstants.amandmanProcedura);
			
			String criteria = "";
			queryDefinition.setCriteria(criteria);
			SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
			
			MatchDocumentSummary matches[] = results.getMatchResults();
			MatchDocumentSummary result;
			EvalResultIterator response = null;
			
			for (int i = 0; i < matches.length; i++) {
				
				result = matches[i];
				ServerEvaluationCall invoker = client.newServerEval();
				
				if (result.getUri().toString().equals(id)){
					
					String query = "declare namespace am = \"http://www.ftn.uns.ac.rs/amandman\";"
							+"xdmp:document-set-collections(\"###\", (\"amandmani\", \"prihvacen_amandman\"))";
					query = query.replace("###",result.getUri());
					invoker.xquery(query);
					response = invoker.eval();
					retVal = "Amandman je prihvacen, prostim vecinskim glasanjem. "
							+ " Akt na koji se amandman odnosi je sada azuriran. Cao zdravo :D";
					break;
				} else{
					retVal = "Trazeni Amandman nije predlozen.";
				}
			}
		}
		return retVal;
	}
	
	public void remove(String uri) throws IOException
	{
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		try{
			xmlManager.delete(uri);
			System.out.println("Dokument je uspesno uklonjen iz baze");
		} catch(Exception e){
			System.out.println("Ne postoji takav dokument u bazi.");
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public void primeniAmandmanNaAkt(String amandmanUri, String idZakona) throws IOException, JAXBException, XMLStreamException {
		
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();
		JAXBContext context = JAXBContext.newInstance("model.amandman");
		JAXBHandle content = new JAXBHandle(context);
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		xmlManager.read(amandmanUri, metadata, content);
		Amandman doc = (Amandman)content.get();
		String aa = doc.getKontekst().getValue().toString();
		System.out.println(aa);
		
		String query = "";
				
		
		if(doc.getOperacija().equals("deleteNode")){
			query = "xquery version \"1.0-ml\";" + 
					"declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";" +
					"xdmp:node-delete(doc(\"zakon\")odnosiSeNa)";
	
			query = query.replace("zakon", idZakona);
			query = query.replace("odnosiSeNa",doc.getKontekst().getValue());
			//query = query.replace("amandman", XMLMarshall.amandmanConversion(doc));
			
			
			ServerEvaluationCall invoker = client.newServerEval();
			invoker.xquery(query);
			
			EvalResultIterator response = invoker.eval();
			// menjanje amandmanove kolekcije nakon sto se izvrsio
			metadata.getCollections().clear();
			metadata.getCollections().add(CollectionConstants.amandmanPrihvacen);
			metadata.getCollections().add(CollectionConstants.amandmani);
			InputStreamHandle handle = new InputStreamHandle(XMLMarshall.objectoToXML(doc));
			xmlManager.write(amandmanUri ,metadata ,handle);
		}
		
		switch(doc.getOperacija())
		{

		case "insertAfter":
			//info: https://docs.marklogic.com/xdmp:node-insert-after
			query = "xquery version \"1.0-ml\";" + 
					"declare namespace sk = \"http://www.ftn.uns.ac.rs/skupstina\";" +
					"xdmp:node-insert-after(doc(\"zakon\")odnosiSeNa,amandman)";
	
			query = query.replace("zakon", idZakona);
			query = query.replace("odnosiSeNa",doc.getKontekst().getValue());
			query = query.replace("amandman", XMLMarshall.amandmanConversion(doc));
			
			break;
		
		case "deleteNode":
			query = StringConstants.getExecutable(OperationType.deleteNode);
			query = query.replace("replace1", doc.getKontekst().getReferentniZakon());
			query = query.replace("replace2", doc.getKontekst().getValue() );
			break;
		case "insertBefore":
			query = StringConstants.getExecutable(OperationType.insertBefore);
			query = query.replace("replace1", doc.getKontekst().getReferentniZakon());
			query = query.replace("replace2",doc.getKontekst().getValue() );
			query = query.replace("replace3", XMLMarshall.amandmanConversion(doc));
			break;
		case "insertChild":
			query = StringConstants.getExecutable(OperationType.insertChild);
			query = query.replace("replace1", doc.getKontekst().getReferentniZakon());
			query = query.replace("replace2",doc.getKontekst().getValue() );
			query = query.replace("replace3", XMLMarshall.amandmanConversion(doc));
			break;
		case "replaceNode":
			query = StringConstants.getExecutable(OperationType.replaceNode);
			query = query.replace("replace1", doc.getKontekst().getReferentniZakon());
			query = query.replace("replace2", doc.getKontekst().getValue());
			query = query.replace("replace3", XMLMarshall.amandmanConversion(doc));
			break;
		}
		/*
		ServerEvaluationCall invoker = client.newServerEval();
		invoker.xquery(query);
		
		EvalResultIterator response = invoker.eval();
		// menjanje amandmanove kolekcije nakon sto se izvrsio
		metadata.getCollections().clear();
		metadata.getCollections().add(CollectionConstants.amandmanPrihvacen);
		metadata.getCollections().add(CollectionConstants.amandmani);
		InputStreamHandle handle = new InputStreamHandle(XMLMarshall.objectoToXML(doc));
		xmlManager.write(amandmanUri ,metadata ,handle);
		*/
	}

}
