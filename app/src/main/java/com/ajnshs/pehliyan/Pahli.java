package com.ajnshs.pehliyan;


public class Pahli {
    private int quesNum;
    private String ques;
    private String ans;

    public Pahli(int quesNum, String ques, String ans) {
        this.quesNum = quesNum;
        this.ques = ques;
        this.ans = ans;
    }

    public int getQuesNum() {
        return quesNum;
    }

    public String getQues() {
        return ques;
    }

    public String getAns() {
        return ans;
    }
}
