package com.example.spring_test.service;

import com.example.spring_test.models.*;
import com.example.spring_test.repository.*;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;



    @Autowired
    EmailService emailService;

    @Autowired
    ResultRepository resultRepository;

    public Long addNewTest(String username, String name, String description, String subject) {
        User u = userRepository.findByUsername(username);
        Test test = new Test(u, name, description,subject);
        u.addTest(test);
        testRepository.save(test);
        userRepository.save(u);
        return test.getId();
    }

    private static void moveFile(String src, String dest ) {
        Path result = null;
        try {
            result =  Files.move(Paths.get(src), Paths.get(dest));
        } catch (IOException e) {
            System.out.println("Exception while moving file: " + e.getMessage());
        }
        if(result != null) {
            System.out.println("File moved successfully.");
        }else{
            System.out.println("File movement failed.");
        }
    }



    public void addQuestion(Long idTest, String question, List<String> answers, Long trueAns[], String img_src, int cost,String type_answer) {
        Test test = testRepository.findById(idTest).get();
        moveFile("C:\\Users\\Kirill\\Downloads\\" + img_src, "D:\\IntelliJ\\Spring_Test\\src\\main\\resources\\static\\images\\" + img_src);
        Question quest = new Question();
        quest.setQuestion(question);
        quest.setTest(test);
        quest.setCost(cost);
        if(img_src.length()>0) quest.setImg_src("\\images\\" + img_src);
        quest.setType_answer(type_answer);
        questionRepository.save(quest);
        List<Answer> answerList = new ArrayList<Answer>();
        for (int i = 0; i < answers.size(); i++) {
            Answer tempAns = new Answer();
            for(int j = 0;j<trueAns.length;j++ ){
                if (i == trueAns[j]) {
                    tempAns = new Answer(quest, answers.get(i), true);
                } else {
                    tempAns = new Answer(quest, answers.get(i), false);
                }
            }
                answerList.add(tempAns);
                answerRepository.save(tempAns);
        }
        quest.setAnswer(answerList);
        test.addQuest(quest);
        questionRepository.save(quest);
        testRepository.save(test);

    }

    public void deleteQuest(Long id) {
        questionRepository.deleteForeignKey(id);
        if (questionRepository.findById(id) != null) questionRepository.deleteById(id);
    }

    public void deleteTest(Long id) {
        if (testRepository.findById(id) != null) {
            Test test = testRepository.findById(id).get();
            User user = test.getUser();
            for(int i=0; i<test.getResult().size();i++)
            {
                System.out.println(test.getResult().size());
                userRepository.deleteForeignKeyUsRes2(test.getResult().get(i).getId());
            }

            testRepository.deleteForeignKeyTestRes(id);
           // testRepository.deleteForeignKeyRes(id);
            testRepository.deleteForeignKey(id);
            testRepository.deleteById(id);
        }
    }

    public int getResult(Long testId, List<String> answers) {
        int result=0;
        System.out.println(answers);
        Test test = testRepository.findById(testId).get();
        List<String> trueAnswer = new ArrayList<>();
        for(int k= 0; k<test.getQuestion().size();k++){
            for (int l =0; l<test.getQuestion().get(k).getAnswer().size();l++)
            if(test.getQuestion().get(k).getAnswer().get(l).isFlag()) trueAnswer.add(test.getQuestion().get(k).getAnswer().get(l).getAnswer());
        }
        System.out.println(trueAnswer);
        for(int g =0; g<answers.size();g++){
            if(trueAnswer.get(g).equals(answers.get(g))) result+=test.getQuestion().get(g).getAnswerCost();
        }
     /*   for (int i = 0; i < answers.size(); i++) {
                List<Answer> ans = test.getQuestion().get(i).getAnswer();
            System.out.println(ans.get(i).getAnswer());
                for(int j = 0;j<ans.size();j++){
                    if (ans.get(j).getRightAnswer(answers.get(i)) == true) result+=ans.get(j).getQuestion().getAnswerCost();

        }*/

        return result;
    }
    public void setPublic(Long id){
        Test test = testRepository.findById(id).get();
        test.setPublish(true);
        testRepository.save(test);
    }
    public boolean isOwner(Long testId, String username){
        Test test = testRepository.findById(testId).get();
        if (test.getUser().getUsername().equals(username)) return true;
        else return false;
    }
    public boolean isOwner(String username, Long questId){
        Test test = questionRepository.findById(questId).get().getTest();
        if (test.getUser().getUsername().equals(username)) return true;
        else return false;
    }
    public void newResult(String username, Long testId, Long resultPoints){
        User user = userRepository.findByUsername(username);
        Test test = testRepository.findById(testId).get();
        String temp ="";
        Result result = new Result(resultPoints,user,test);
        resultRepository.save(result);
       user.addResult(result);
       test.addResult(result);
        userRepository.save(user);
        testRepository.save(test);

    }
}
