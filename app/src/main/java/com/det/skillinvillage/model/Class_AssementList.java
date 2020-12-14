package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Class_AssementList {
    @SerializedName("Assessment_ID")
    @Expose
    private Integer assesmentID;
    @SerializedName("Institute_ID")
    @Expose
    private Integer instituteID;
    @SerializedName("Level_ID")
    @Expose
    private Integer levelID;
    @SerializedName("Assessment_Name")
    @Expose
    private String assesmentName;
    @SerializedName("Assessment_Date")
    @Expose
    private String assesmentDate;
    @SerializedName("Assessment_Status")
    @Expose
    private String assesmentStatus;
    @SerializedName("Level_Name")
    @Expose
    private String lavelName;
    @SerializedName("Institute_Name")
    @Expose
    private String instituteName;
    @SerializedName("Present_Count")
    @Expose
    private String presentCount;
    @SerializedName("Total_Count")
    @Expose
    private String totalCount;
    @SerializedName("Max_Marks")
    @Expose
    private String maxMarks;
    @SerializedName("Save")
    @Expose
    private String save;
    @SerializedName("Question_Name")
    @Expose
    private Object questionName;
    @SerializedName("Question_Link")
    @Expose
    private String questionLink;

    public Integer getAssesmentID() {
        return assesmentID;
    }

    public void setAssesmentID(Integer assesmentID) {
        this.assesmentID = assesmentID;
    }

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer instituteID) {
        this.instituteID = instituteID;
    }

    public Integer getLevelID() {
        return levelID;
    }

    public void setLevelID(Integer levelID) {
        this.levelID = levelID;
    }

    public String getAssesmentName() {
        return assesmentName;
    }

    public void setAssesmentName(String assesmentName) {
        this.assesmentName = assesmentName;
    }

    public String getAssesmentDate() {
        return assesmentDate;
    }

    public void setAssesmentDate(String assesmentDate) {
        this.assesmentDate = assesmentDate;
    }

    public String getAssesmentStatus() {
        return assesmentStatus;
    }

    public void setAssesmentStatus(String assesmentStatus) {
        this.assesmentStatus = assesmentStatus;
    }

    public String getLavelName() {
        return lavelName;
    }

    public void setLavelName(String lavelName) {
        this.lavelName = lavelName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(String presentCount) {
        this.presentCount = presentCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public Object getQuestionName() {
        return questionName;
    }

    public void setQuestionName(Object questionName) {
        this.questionName = questionName;
    }

    public String getQuestionLink() {
        return questionLink;
    }

    public void setQuestionLink(String questionLink) {
        this.questionLink = questionLink;
    }

}
