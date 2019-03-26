package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;

public class Dao<T> {

    public void save(T object){
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    public void update(T object){
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
        }
    }

    public void delete(T object){
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
        }
    }

    public T findById(Class<T> typeclass, long id){
        T object = null;
        try(Session session = getSession()){
            object = session.get(typeclass, id);
        }
        return object;
    }

    private Session getSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }
}