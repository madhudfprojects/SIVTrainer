package com.det.skillinvillage;

/*<vmAssesmentStudent>
<Student_ID>1462</Student_ID>
<Student_Name>RAM S</Student_Name>
<Application_No>HB19TST002</Application_No>
<Assesment_Entry>30</Assesment_Entry>
</vmAssesmentStudent>*/

public class Class_StudentAssement
{

    String student_id;
    String studentname;
    String student_applNo;
    String AssementMarks;


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

    public String getStudent_applNo() {
        return student_applNo;
    }

    public void setStudent_applNo(String student_applNo) {
        this.student_applNo = student_applNo;
    }

    public String getAssementMarks() {
        return AssementMarks;
    }

    public void setAssementMarks(String assementMarks) {
        AssementMarks = assementMarks;
    }
}
