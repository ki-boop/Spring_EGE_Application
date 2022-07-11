package com.example.spring_test.models;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.List;

@Entity
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private boolean publish;

    private String subject;

    private boolean type_check;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> question;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Result> result;

    public Test(User user,String name, String description,String subject) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.publish=false;
        this.subject = null;
    }
    public Test(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addQuest(Question question){
        this.question.add(question);
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
    public boolean isType_check() {
        return type_check;
    }

    public void setType_check(boolean type_check) {
        this.type_check = type_check;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public void addResult(Result result){
        this.result.add(result);
    }

    public int countAllPoints(){
        int res=0;
        for (Question q : getQuestion()){
            res+=q.getCost();
        }
        return res;
    }

}