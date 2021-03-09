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
    private Object userID;
    @SerializedName("Document_Type")
    @Expose
    private Object documentType;
    @SerializedName("Refer_Status")
    @Expose
    private String referStatus;
    @SerializedName("Refer_Yes")
    @Expose
    private String referYes;
    @SerializedName("Refer_No")
    @Expose
    private String referNo;
    @SerializedName("Refer_Others")
    @Expose
    private String referOthers;
    @SerializedName("Refer_No_Others")
    @Expose
    private String referNoOthers;
    @SerializedName("Prepared")
    @Expose
    private String prepared;
    @SerializedName("LP_NextWeek")
    @Expose
    private String lPNextWeek;
    @SerializedName("TLM")
    @Expose
    private String tLM;

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

    public Object getUserID() {
        return userID;
    }

    public void setUserID(Object userID) {
        this.userID = userID;
    }

    public Object getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Object documentType) {
        this.documentType = documentType;
    }

    public String getReferStatus() {
        return referStatus;
    }

    public void setReferStatus(String referStatus) {
        this.referStatus = referStatus;
    }

    public String getReferYes() {
        return referYes;
    }

    public void setReferYes(String referYes) {
        this.referYes = referYes;
    }

    public String getReferNo() {
        return referNo;
    }

    public void setReferNo(String referNo) {
        this.referNo = referNo;
    }

    public String getReferOthers() {
        return referOthers;
    }

    public void setReferOthers(String referOthers) {
        this.referOthers = referOthers;
    }

    public String getReferNoOthers() {
        return referNoOthers;
    }

    public void setReferNoOthers(String referNoOthers) {
        this.referNoOthers = referNoOthers;
    }

    public String getPrepared() {
        return prepared;
    }

    public void setPrepared(String prepared) {
        this.prepared = prepared;
    }

    public String getLPNextWeek() {
        return lPNextWeek;
    }

    public void setLPNextWeek(String lPNextWeek) {
        this.lPNextWeek = lPNextWeek;
    }

    public String getTLM() {
        return tLM;
    }

    public void setTLM(String tLM) {
        this.tLM = tLM;
    }

    @Override
    public String toString() {
        return questionName;
    }
}
