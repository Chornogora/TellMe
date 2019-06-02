package dao;

import model.Progress;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProgressRepo extends PagingAndSortingRepository<Progress, Long> {
    List<Progress> findByUser_id(Long userId);
}
