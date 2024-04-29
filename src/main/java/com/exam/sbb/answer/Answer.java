package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Question question;
}