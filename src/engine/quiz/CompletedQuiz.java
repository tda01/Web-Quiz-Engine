package engine.quiz;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "CompletedQuizzes")
public class CompletedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long key;
    private Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String completedByUser;
    private LocalDateTime completedAt;

    public CompletedQuiz() {
    }

    public CompletedQuiz(Long id, String completedByUser, LocalDateTime completedAt) {
        this.completedByUser = completedByUser;
        this.completedAt = completedAt;
    }


    public String getCompletedByUser() {
        return completedByUser;
    }

    public void setCompletedByUser(String completedByUser) {
        this.completedByUser = completedByUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "CompletedQuiz{" +
                "id=" + id +
                ", completedAt=" + completedAt +
                '}';
    }
}
