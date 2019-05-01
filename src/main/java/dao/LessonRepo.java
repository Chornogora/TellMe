package dao;

import model.Lesson;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LessonRepo extends PagingAndSortingRepository<Lesson, Long> {
    Lesson findByName(String name);
}