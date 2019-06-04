package dao;

import model.Administrator;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SimpleAdministratorRepo extends PagingAndSortingRepository<Administrator, Long> {
    Administrator findByLogin(String login);
}
