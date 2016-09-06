package dao;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(AktDaoLocal.class)
public class AktDao extends GenericDao<Object, String> implements AktDaoLocal{

}
