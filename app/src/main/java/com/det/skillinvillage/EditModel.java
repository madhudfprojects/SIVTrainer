package com.det.skillinvillage;

public class EditModel {

    String student_id;
    String studentname;
    String student_marks_status;
    String AssementMarks;
    String MaxMarks;
    String Flag;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getMaxMarks() {
        return MaxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        MaxMarks = maxMarks;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getstudent_marks_status() {
        return student_marks_status;
    }

    public void setstudent_marks_status(String student_marks_status) {
        this.student_marks_status = student_marks_status;
    }

    public String getAssementMarks() {
        return AssementMarks;
    }

    public void setAssementMarks(String assementMarks) {
        AssementMarks = assementMarks;
    }
}
