package engine.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, CompletedQuizRepository completedQuizRepository) {
        this.quizRepository = quizRepository;
        this.completedQuizRepository  = completedQuizRepository;
    }

    public Page<Quiz> getQuizzes(Integer page) {
        return quizRepository.findAll(PageRequest.of(page, 10));
    }

    public Optional<Quiz> getQuiz(Long id) {

        Optional<Quiz> quiz = quizRepository.findById(id);

        if (quiz.isPresent()) {
            return quiz;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "quiz not found");
        }

    }

    public Page<CompletedQuiz> getCompletedQuizzes(Integer page, UserDetails details) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        String user = details.getUsername();

        return completedQuizRepository.getCompletedQuizByCompletedByUser(user, pageable);
    }


    public Quiz addQuiz(Quiz quiz, UserDetails details) {
        quiz.setCreatorUser(details.getUsername());
        quizRepository.save(quiz);
        return quiz;
    }

    public Answer getAnswer(Long id, AnswerBody answer, UserDetails details) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        if (!quiz.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }

        List<Integer> correctAnswers = quiz.get().getAnswer();
        List<Integer> userAnswers = answer.getAnswer();

        if (correctAnswers.size() != userAnswers.size()) {
            return new Answer(false, "Incorrect answer! The number of answers is incorrect.");
        }

        boolean allAnswersCorrect = true;
        for (int i = 0; i < correctAnswers.size(); i++) {
            if (!correctAnswers.contains(userAnswers.get(i))) {
                allAnswersCorrect = false;
                break;
            }
        }

        if (allAnswersCorrect) {
            CompletedQuiz completedQuiz = new CompletedQuiz();
            completedQuiz.setId(id);
            completedQuiz.setCompletedByUser(details.getUsername());
            completedQuiz.setCompletedAt(LocalDateTime.now());
            completedQuizRepository.save(completedQuiz);
            return new Answer(true, "Congratulations! You are right!");
        } else {
            return new Answer(false, "Incorrect answer! Try again!");
        }

    }

    public void deleteQuiz(Long id, UserDetails details) {
        Optional<Quiz> quiz = quizRepository.findById(id);

        if (!quiz.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }

        Quiz quizToDelete = quiz.get();
        String quizOwner = quizToDelete.getCreatorUser();

        if (details.getUsername().equals(quizOwner)) {
            quizRepository.delete(quizToDelete);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the creator of this quiz");
        }



    }



}





