package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostTopicReviewUpdateRequest {
    @SerializedName("User_ID")
    @Expose
    private String User_ID;
    @SerializedName("TopicLevel_ID")
    @Expose
    private String TopicLevel_ID;
    @SerializedName("Question_ID")
    @Expose
    private String Question_ID;
    @SerializedName("Question_Answer")
    @Expose
    private String Question_Answer;

    @SerializedName("Document_Type")
    @Expose
    private String Document_Type;

    public String getDocument_Type() {
        return Document_Type;
    }

    public void setDocument_Type(String document_Type) {
        Document_Type = document_Type;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getTopicLevel_ID() {
        return TopicLevel_ID;
    }

    public void setTopicLevel_ID(String topicLevel_ID) {
        TopicLevel_ID = topicLevel_ID;
    }

    public String getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(String question_ID) {
        Question_ID = question_ID;
    }

    public String getQuestion_Answer() {
        return Question_Answer;
    }

    public void setQuestion_Answer(String question_Answer) {
        Question_Answer = question_Answer;
    }
}
