package dao;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless
@Local(AmandmanDaoLocal.class)
public class AmandmanDao extends GenericDao<Object, String> implements AktDaoLocal{

}