package dao;

import model.LexicalTheory;
import model.Test;
import model.Variant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.dialect.function.VarArgsSQLFunction;

public class TestDao implements DataProcessable<Test> {

    @Override
    public void save(Test object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void update(Test object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void delete(Test object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    public void addVariant(Variant vr, long id){
        Test object = null;
        try(Session session = getSession()){
            object = session.get(Test.class, id);
            vr.setTest(object);
            session.save(vr);
        }
    }

    @Override
    public Test findById(Class<Test> typeclass, long id) {
        Test object = null;
        try(Session session = getSession()){
            object = session.get(typeclass, id);
        }
        return object;
    }
}
