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

/*
public string Refer_Status { get; set; }
       public string Refer_Yes { get; set; }
       public string Refer_No { get; set; }
       public string Refer_Others { get; set; }
       public string Refer_No_Others { get; set; }
       public string Prepared { get; set; }
       public string Prepared_Other { get; set; }
       public string LP_NextWeek { get; set; }
       public string TLM { get; set; }

 */
    @SerializedName("Refer_Status")
    @Expose
    private String Refer_Status;

    @SerializedName("Refer_Yes")
    @Expose
    private String Refer_Yes;

    @SerializedName("Refer_No")
    @Expose
    private String Refer_No;

    @SerializedName("Refer_No_Others")
    @Expose
    private String Refer_No_Others;


    @SerializedName("Refer_Others")
    @Expose
    private String Refer_Others;

    @SerializedName("Prepared")
    @Expose
    private String Prepared;

    @SerializedName("Topic_ID")
    @Expose
    private String Topic_ID;

    @SerializedName("LP_NextWeek")
    @Expose
    private String LP_NextWeek;

    @SerializedName("TLM")
    @Expose
    private String TLM;

    public String getTopic_ID() {
        return Topic_ID;
    }

    public void setTopic_ID(String topic_ID) {
        Topic_ID = topic_ID;
    }

    public String getRefer_No_Others() {
        return Refer_No_Others;
    }

    public void setRefer_No_Others(String refer_No_Others) {
        Refer_No_Others = refer_No_Others;
    }

    public String getRefer_Status() {
        return Refer_Status;
    }

    public void setRefer_Status(String refer_Status) {
        Refer_Status = refer_Status;
    }

    public String getRefer_Yes() {
        return Refer_Yes;
    }

    public void setRefer_Yes(String refer_Yes) {
        Refer_Yes = refer_Yes;
    }

    public String getRefer_No() {
        return Refer_No;
    }

    public void setRefer_No(String refer_No) {
        Refer_No = refer_No;
    }

    public String getRefer_Others() {
        return Refer_Others;
    }

    public void setRefer_Others(String refer_Others) {
        Refer_Others = refer_Others;
    }

    public String getPrepared() {
        return Prepared;
    }

    public void setPrepared(String prepared) {
        Prepared = prepared;
    }


    public String getLP_NextWeek() {
        return LP_NextWeek;
    }

    public void setLP_NextWeek(String LP_NextWeek) {
        this.LP_NextWeek = LP_NextWeek;
    }

    public String getTLM() {
        return TLM;
    }

    public void setTLM(String TLM) {
        this.TLM = TLM;
    }

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
