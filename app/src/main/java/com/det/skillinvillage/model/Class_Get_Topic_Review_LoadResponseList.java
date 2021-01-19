package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_Get_Topic_Review_LoadResponseList {
    @SerializedName("Topic_ID")
    @Expose
    private String topicID;
    @SerializedName("TopicLevel_ID")
    @Expose
    private String topicLevelID;
    @SerializedName("Topic_Name")
    @Expose
    private String topicName;
    @SerializedName("Subject_Name")
    @Expose
    private String subjectName;
    @SerializedName("Question_ID")
    @Expose
    private String questionID;
    @SerializedName("Question_Name")
    @Expose
    private String questionName;
    @SerializedName("Question_Answer")
    @Expose
    private String questionAnswer;
    @SerializedName("User_ID")
    @Expose
    private String userID;

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getTopicLevelID() {
        return topicLevelID;
    }

    public void setTopicLevelID(String topicLevelID) {
        this.topicLevelID = topicLevelID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return questionName;
    }
}
