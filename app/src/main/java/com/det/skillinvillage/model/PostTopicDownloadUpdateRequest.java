package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostTopicDownloadUpdateRequest {
    @SerializedName("Created_By")
    @Expose
    private String CreatedBy;
    @SerializedName("TopicLevel_ID")
    @Expose
    private String TopicLevel_ID;

    @SerializedName("Document_Type")
    @Expose
    private String Document_Type;

    public String getDocument_Type() {
        return Document_Type;
    }

    public void setDocument_Type(String document_Type) {
        Document_Type = document_Type;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getTopicLevel_ID() {
        return TopicLevel_ID;
    }

    public void setTopicLevel_ID(String topicLevel_ID) {
        TopicLevel_ID = topicLevel_ID;
    }
}
