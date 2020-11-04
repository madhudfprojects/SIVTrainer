package com.det.skillinvillage;

public class Class_AssessmentModel {


    private String editTextValue;
    private String Assement_StudentName;
    private String Assement_StudentID;
    private String AssementModel_status;
    private String AssementModel_Flag;
    private String AssementModel_Marks;

    public String getAssementModel_Flag() {
        return AssementModel_Flag;
    }

    public String getAssementModel_Marks() {
        return AssementModel_Marks;
    }

    public void setAssementModel_Marks(String assementModel_Marks) {
        AssementModel_Marks = assementModel_Marks;
    }

    public void setAssementModel_Flag(String assementModel_Flag) {
        AssementModel_Flag = assementModel_Flag;
    }

    public String getAssementModel_status() {
        return AssementModel_status;
    }

    public void setAssementModel_status(String assementModel_status) {
        AssementModel_status = assementModel_status;
    }

    public String getEditTextValue() {
        return editTextValue;
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }


    public String getAssement_StudentID() {
        return Assement_StudentID;
    }

    public void setAssement_StudentID(String assement_StudentID) {
        Assement_StudentID = assement_StudentID;
    }
    public String getAssement_StudentName() {
        return Assement_StudentName;
    }

    public void setAssement_StudentName(String assement_StudentName) {
        Assement_StudentName = assement_StudentName;
    }
    @Override
    public String toString() {
        return Assement_StudentName;
    }

}
