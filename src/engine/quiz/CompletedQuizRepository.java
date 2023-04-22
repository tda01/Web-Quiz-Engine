package engine.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;


public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {

    Page<CompletedQuiz> getCompletedQuizByCompletedByUser(String username, Pageable pageable);

}
