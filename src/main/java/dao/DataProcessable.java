package dao;

import org.hibernate.Session;

public interface DataProcessable<T> {
    public void save(T object);

    public void update(T object);

    public void delete(T object);

    public T findById(Class<T> typeclass, long id);

    default Session getSession(){
        return HibernateSessionFactory.getSessionFactory().openSession();
    }
}
