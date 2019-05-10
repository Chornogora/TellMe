package dao;

import model.Word;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WordRepo extends PagingAndSortingRepository<Word, Long> {
}
