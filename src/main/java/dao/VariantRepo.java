package dao;

import model.Variant;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VariantRepo extends PagingAndSortingRepository<Variant, Long> {
}
