package dao;

import model.SimpleUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao implements DataProcessable<SimpleUser> {

    @Override
    public void save(SimpleUser object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void update(SimpleUser object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void delete(SimpleUser object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public SimpleUser findById(Class<SimpleUser> typeclass, long id) {
        SimpleUser object = null;
        try(Session session = getSession()){
            object = session.get(typeclass, id);
        }
        return object;
    }

    public SimpleUser findByName(String login){
        SimpleUser object = null;
        try(Session session = getSession()){
            String queryString = "FROM SimpleUser WHERE login = '\" + login + \"'";
            Query<SimpleUser> query = session.createQuery(queryString);
            List<SimpleUser> lst = query.list();
            if(lst.isEmpty()) {
                return null;
            }
            System.out.println(SimpleUser.class);
            System.out.println(lst.get(0).getClass());
            if(SimpleUser.class == lst.get(0).getClass()) {
                object = query.list().get(0);
            }
        }
        return object;
    }
}
