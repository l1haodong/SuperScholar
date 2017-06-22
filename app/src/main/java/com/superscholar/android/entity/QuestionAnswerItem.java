package com.superscholar.android.entity;

/**
 * Created by Administrator on 2017/4/2.
 */

public class QuestionAnswerItem {
    private String question;
    private String answer;

    public QuestionAnswerItem(){}

    public QuestionAnswerItem(String question,String answer){
        this.question=question;
        this.answer=answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
