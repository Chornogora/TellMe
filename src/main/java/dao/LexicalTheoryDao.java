package dao;

import model.LexicalTheory;
import model.Word;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LexicalTheoryDao implements DataProcessable<LexicalTheory> {
    @Override
    public void save(LexicalTheory object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void update(LexicalTheory object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    @Override
    public void delete(LexicalTheory object) {
        try(Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        }
    }

    public void addWord(Word word, long id){
        LexicalTheory object = null;
        try(Session session = getSession()){
            object = session.get(LexicalTheory.class, id);
            word.setTheory(object);
            session.update(word);
        }
    }

    @Override
    public LexicalTheory findById(Class<LexicalTheory> typeclass, long id) {
        LexicalTheory object = null;
        try(Session session = getSession()){
            object = session.get(typeclass, id);
        }
        return object;
    }
}
