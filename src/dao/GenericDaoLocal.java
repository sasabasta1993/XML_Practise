package dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBException;

public interface GenericDaoLocal<T, ID extends Serializable> {
	
	public List<Object> findByKeyWord(String keyword) throws IOException;
	
	public T findById(String id, String tip) throws JAXBException, IOException;
	
	public T persist(T entity, String id) throws JAXBException, IOException;
	
	public List<T> find() throws IOException, JAXBException;
	
	public String vote(String id, String za, String uzdrzani, String protiv) throws IOException, JAXBException;
	
	public String voteAmandman(String id, String za, String uzdrzani, String protiv) throws IOException, JAXBException;
	
	public List<Object> findByMetaData(String dateFrom, String dateTo) throws IOException;

	public List<Object> findByMetaData2(String dateFrom, String dateTo) throws IOException;
	
	public List<T> findProposed(String name) throws IOException, JAXBException;

} 