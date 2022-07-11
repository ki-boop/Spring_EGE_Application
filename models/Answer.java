package com.example.spring_test.models;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String answer;

    private boolean flag;

    private String description;

    @ManyToOne
    private Question question;

    public Answer(){}
    public Answer(Question question,String answer, boolean flag) {
        this.question=question;
        this.answer = answer;
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isFlag() {
        return flag;
    }

    public boolean getRightAnswer(String userAnswer){
        if(getAnswer().equals(userAnswer)){
            return this.isFlag();
        }
         return false;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
