package com.det.skillinvillage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
{
    "Status": true,
    "Message": "Success",
    "lst": [
        {
            "Student_ID": 6341,
            "Application_No": "HB20TST001",
            "Sandbox_ID": 1,
            "Academic_ID": 2020,
            "Cluster_ID": 1,
            "Institute_ID": 128,
            "School_ID": 131,
            "Level_ID": 861,
            "Student_Name": "RISHAB EDIT",
            "Gender": "Boy",
            "Birth_Date": "06-09-2000 0:00:00",
            "Education": "4th Standard",
            "Mobile": "9663636339",
            "Father_Name": "TEST",
            "Mother_Name": "TEST",
            "Student_Aadhar": "2222222222",
            "Student_Status": "Admission",
            "Admission_Fee": null,
            "Created_Date": "22-12-2020 12:05:39",
            "Created_By": "12",
            "File_Name": null,
            "Receipt_Manual": null,
            "Learning_Mode": "39",
            "Marks": null,
            "Temp_ID": "edittemp160827258218418122020",
            "Learning_ID": null,
            "State_ID": "29",
            "State_Name": null,
            "District_ID": "536",
            "District_Name": null,
            "Taluka_ID": "0",
            "Taluka_Name": null,
            "Village_ID": "602358",
            "Village_Name": null,
            "Address": "TestEditaddress",
            "Application_Type": null,
            "Division_ID": "0",
            "Division_Name": null,
            "StudentLevel_ID": null
        }
    ]
}
 */
public class Class_Response_AddStudentDetailsList {
    @SerializedName("Student_ID")
    @Expose
    private Integer studentID;
    @SerializedName("Application_No")
    @Expose
    private String applicationNo;
    @SerializedName("Sandbox_ID")
    @Expose
    private Integer sandboxID;
    @SerializedName("Academic_ID")
    @Expose
    private Integer academicID;
    @SerializedName("Cluster_ID")
    @Expose
    private Integer clusterID;
    @SerializedName("Institute_ID")
    @Expose
    private Integer instituteID;
    @SerializedName("School_ID")
    @Expose
    private Integer schoolID;
    @SerializedName("Level_ID")
    @Expose
    private Integer levelID;
    @SerializedName("Student_Name")
    @Expose
    private String studentName;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Birth_Date")
    @Expose
    private String birthDate;
    @SerializedName("Education")
    @Expose
    private String education;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Father_Name")
    @Expose
    private String fatherName;
    @SerializedName("Mother_Name")
    @Expose
    private String motherName;
    @SerializedName("Student_Aadhar")
    @Expose
    private String studentAadhar;
    @SerializedName("Student_Status")
    @Expose
    private String studentStatus;
    @SerializedName("Admission_Fee")
    @Expose
    private Object admissionFee;
    @SerializedName("Created_Date")
    @Expose
    private String createdDate;
    @SerializedName("Created_By")
    @Expose
    private String createdBy;
    @SerializedName("File_Name")
    @Expose
    private Object fileName;
    @SerializedName("Receipt_Manual")
    @Expose
    private Object receiptManual;
    @SerializedName("Learning_Mode")
    @Expose
    private String learningMode;
    @SerializedName("Marks")
    @Expose
    private Object marks;

    @SerializedName("Temp_ID")
    @Expose
    private Object Temp_ID;


    @SerializedName("State_ID")
    @Expose
    private String State_ID;

    @SerializedName("District_ID")
    @Expose
    private String District_ID;

    @SerializedName("Taluka_ID")
    @Expose
    private String Taluk_ID;

    @SerializedName("Village_ID")
    @Expose
    private String Village_ID;

    @SerializedName("Division_ID")
    @Expose
    private String Division_ID;

    @SerializedName("Division_Name")
    @Expose
    private String Division_Name;

//added on 17th dec 2020 since it was not coming before
//                "Address": "test",
//                        "Application_Type": null,
//                        "Division_ID": "0",
//                        "Division_Name": null

    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Application_Type")
    @Expose
    private String Application_Type;


    @SerializedName("State_Name")
    @Expose
    private String State_Name;

    @SerializedName("District_Name")
    @Expose
    private String District_Name;

    @SerializedName("Taluka_Name")
    @Expose
    private String Taluka_Name;

    @SerializedName("Village_Name")
    @Expose
    private String Village_Name;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getApplication_Type() {
        return Application_Type;
    }

    public void setApplication_Type(String application_Type) {
        Application_Type = application_Type;
    }

    public String getState_Name() {
        return State_Name;
    }

    public void setState_Name(String state_Name) {
        State_Name = state_Name;
    }

    public String getDistrict_Name() {
        return District_Name;
    }

    public void setDistrict_Name(String district_Name) {
        District_Name = district_Name;
    }

    public String getTaluka_Name() {
        return Taluka_Name;
    }

    public void setTaluka_Name(String taluka_Name) {
        Taluka_Name = taluka_Name;
    }

    public String getVillage_Name() {
        return Village_Name;
    }

    public void setVillage_Name(String village_Name) {
        Village_Name = village_Name;
    }

    public String getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(String division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision_Name() {
        return Division_Name;
    }

    public void setDivision_Name(String division_Name) {
        Division_Name = division_Name;
    }

    public String getState_ID() {
        return State_ID;
    }

    public void setState_ID(String state_ID) {
        State_ID = state_ID;
    }

    public String getDistrict_ID() {
        return District_ID;
    }

    public void setDistrict_ID(String district_ID) {
        District_ID = district_ID;
    }

    public String getTaluk_ID() {
        return Taluk_ID;
    }

    public void setTaluk_ID(String taluk_ID) {
        Taluk_ID = taluk_ID;
    }

    public String getVillage_ID() {
        return Village_ID;
    }

    public void setVillage_ID(String village_ID) {
        Village_ID = village_ID;
    }

    public Object getTemp_ID() {
        return Temp_ID;
    }

    public void setTemp_ID(Object temp_ID) {
        Temp_ID = temp_ID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Integer getSandboxID() {
        return sandboxID;
    }

    public void setSandboxID(Integer sandboxID) {
        this.sandboxID = sandboxID;
    }

    public Integer getAcademicID() {
        return academicID;
    }

    public void setAcademicID(Integer academicID) {
        this.academicID = academicID;
    }

    public Integer getClusterID() {
        return clusterID;
    }

    public void setClusterID(Integer clusterID) {
        this.clusterID = clusterID;
    }

    public Integer getInstituteID() {
        return instituteID;
    }

    public void setInstituteID(Integer instituteID) {
        this.instituteID = instituteID;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public Integer getLevelID() {
        return levelID;
    }

    public void setLevelID(Integer levelID) {
        this.levelID = levelID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getStudentAadhar() {
        return studentAadhar;
    }

    public void setStudentAadhar(String studentAadhar) {
        this.studentAadhar = studentAadhar;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Object getAdmissionFee() {
        return admissionFee;
    }

    public void setAdmissionFee(Object admissionFee) {
        this.admissionFee = admissionFee;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getFileName() {
        return fileName;
    }

    public void setFileName(Object fileName) {
        this.fileName = fileName;
    }

    public Object getReceiptManual() {
        return receiptManual;
    }

    public void setReceiptManual(Object receiptManual) {
        this.receiptManual = receiptManual;
    }

    public String getLearningMode() {
        return learningMode;
    }

    public void setLearningMode(String learningMode) {
        this.learningMode = learningMode;
    }

    public Object getMarks() {
        return marks;
    }

    public void setMarks(Object marks) {
        this.marks = marks;
    }

}
