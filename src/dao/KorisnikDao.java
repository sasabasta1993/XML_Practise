package dao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;

import model.korisnici.Korisnici;


@Stateless
@Local(KorisnikDaoLocal.class)
public class KorisnikDao extends GenericDao<Object, String> implements KorisnikDaoLocal{
	
	@Override
	public Korisnici getUsers() {
		try {
			return em.getKorisnici();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
/*
    public static final String COLLECTION = "/example";
    public static final int PAGE_SIZE = 10;
    private static final String prefix = "..\\data\\xquery\\";
  
	private  DatabaseClient client;	
    
    private String getDocId(Long Id) {
        return String.format("/korisnici/%d.xml", Id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public void add(Korisnik korisnik) throws FileNotFoundException,
			IOException, JAXBException {
		
		
		ConnectionProperties props = Util.loadProperties();
		
		System.out.println("[INFO] " + XMLWriterExample1.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
			System.out.println("klijent uspesan");
		}

		XMLDocumentManager xmlManager = client.newXMLDocumentManager();        
		JAXBHandle contentHandle = getKorisnikHandle();
		
        contentHandle.set(korisnik);
        xmlManager.write("trala",contentHandle);
	
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
		
	}
    
    @Override
    public boolean registration(Korisnik sentUser) throws IOException{
    	
    	ConnectionProperties props = Util.loadProperties();
    	client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
    	
    	String filePath = prefix + "retrieve-from-collection.xqy";
    	ServerEvaluationCall invoker = client.newServerEval();
    	String query = readFile(filePath, StandardCharsets.UTF_8);
    	invoker.xquery(query);
    	EvalResultIterator response = invoker.eval();
    	
    	if (response.hasNext()) {

			for (EvalResult result : response) {
				System.out.println("\n" + result.getString());
			}
			client.release();
			return true;
		} else { 		
			System.out.println("your query returned an empty sequence.");
			client.release();
			return false;
		}
    	
    }
    
	 
    private JAXBHandle getKorisnikHandle() {
	        try {
	            JAXBContext context = JAXBContext.newInstance(Korisnik.class);
	            return new JAXBHandle(context);
	        } catch (JAXBException e) {
	            throw new RuntimeException("Unable to create product JAXB context", e);
	        }
	    }
    
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
    
*/
}
