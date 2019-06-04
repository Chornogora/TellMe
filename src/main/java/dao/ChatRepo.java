package dao;

import model.Chat;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChatRepo extends PagingAndSortingRepository<Chat, Long> {
}