package dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import entitymanager.EntityManager;

public abstract class GenericDao <T, ID extends Serializable> implements GenericDaoLocal<T, ID> {
	
	protected EntityManager<T,ID> em; 
	
	public GenericDao()
	{
		super();
		em = new EntityManager<T,ID>();
	}
	
	@Override
	public List<Object> findByKeyWord(String keyword) throws IOException
	{
		return em.findByKeyWord(keyword);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(String id, String tip) throws JAXBException, IOException {	
		return (T)em.findById(id, tip);
	}
	
	@Override
	public T persist(T entity, String id) throws JAXBException, IOException {
		try {
			em.persist(entity, id);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find() throws IOException, JAXBException {
		return (List<T>)em.find();
	}
	
	@Override
	public String vote(String id, String za, String uzdrzani, String protiv) throws IOException, JAXBException{
		return (String)em.vote(id, za, uzdrzani, protiv);
	}

	@Override
	
	public List<Object> findByMetaData(String dateFrom, String dateTo) throws IOException{
		return em.findByMetaData(dateFrom, dateTo);
	}
	
	@Override
	public List<Object> findByMetaData2(String dateFrom, String dateTo) throws IOException{
		
		return em.findByMetaData2(dateFrom, dateTo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findProposed(String name) throws IOException, JAXBException {
		return (List<T>)em.findProposed(name);
	}

	
} 