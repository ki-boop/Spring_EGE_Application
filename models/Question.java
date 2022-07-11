package com.example.spring_test.models;

import javax.persistence.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answer;

    private int cost;

    private String type_answer;


    private String img_src;



    @ManyToOne
    private Test test;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }
    public int getCost() {
        return cost;
    }
    public String getType_answer() {
        return type_answer;
    }
    public void setType_answer(String type_answer) {
        this.type_answer = type_answer;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getAnswerCost(){
        int countRightAnswers=0;
        for(Answer a : getAnswer()){
            if(a.isFlag()) countRightAnswers++;
        }
        return getCost()/countRightAnswers;
    }

    public List<String> getTrueAnswers(){
        List <String> list = new ArrayList<>();
        for(Answer a : getAnswer()){
            if(a.isFlag()) list.add(a.getAnswer());
        }

        return list;
    }
}
