package com.exam.sbb.answer;

import com.exam.sbb.question.Question;
import com.exam.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    private Set<SiteUser> voter;

    @ManyToOne
    //@JoinColumn(name = "question_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Question question;
}