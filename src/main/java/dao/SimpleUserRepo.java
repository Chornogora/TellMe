package dao;

import model.SimpleUser;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface SimpleUserRepo extends PagingAndSortingRepository<SimpleUser, Long> {
    SimpleUser findByLogin(String login);
}