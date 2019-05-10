package dao;

import model.Test;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TestRepo extends PagingAndSortingRepository<Test, Long> {
}