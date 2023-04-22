package engine.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public  QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        return quizService.getQuizzes(page);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Optional<Quiz> getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping(path = "api/quizzes/completed")
    public Page<CompletedQuiz> getCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page, @AuthenticationPrincipal UserDetails details) {
        return quizService.getCompletedQuizzes(page, details);
    }

    @PostMapping(path = "/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal UserDetails details) {
        return quizService.addQuiz(quiz, details);
    }
//
    @PostMapping(path = "/api/quizzes/{id}/solve")
    public Answer getAnswer(@PathVariable Long id, @RequestBody AnswerBody answer, @AuthenticationPrincipal UserDetails details) {
        return quizService.getAnswer(id, answer, details);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public void deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal UserDetails details) {
        quizService.deleteQuiz(id, details);
    }

}
