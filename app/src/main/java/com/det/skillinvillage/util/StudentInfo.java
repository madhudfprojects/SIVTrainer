package com.det.skillinvillage.util;

public class StudentInfo {

	//private variables
	int _id;
	String studId;
	String Studentname;
	String StudEmail;
	String Pre_Ab;
	String Attendance_Status;
	String Application_No;
	String LearningOption;



	// Empty constructor
	public StudentInfo(){

	}
	// constructor
	public StudentInfo(int id, String name, String Studentname,String StudEmail){
		this._id = id;
		this.studId = studId;
		this.Studentname = Studentname;
		this.StudEmail = StudEmail;


	}

	public String getLearningOption() {
		return LearningOption;
	}

	public void setLearningOption(String learningOption) {
		LearningOption = learningOption;
	}

	public String getApplication_No() {
		return Application_No;
	}

	public void setApplication_No(String application_No) {
		Application_No = application_No;
	}

	public String getAttendance_Status() {
		return Attendance_Status;
	}

	public void setAttendance_Status(String attendance_Status) {
		Attendance_Status = attendance_Status;
	}

	// constructor
	public StudentInfo(String studId, String Studentname,String StudEmail){
		this.studId = studId;
		this.Studentname = Studentname;
		this.StudEmail = StudEmail;

	}

	public String getPre_Ab() {
		return Pre_Ab;
	}

	public void setPre_Ab(String pre_Ab) {
		Pre_Ab = pre_Ab;
	}

	// getting ID
	public int getID(){
		return this._id;
	}

	// setting id
	public void setID(int id){
		this._id = id;
	}

	// getting lead_id
	public String getstudId(){
		return this.studId;
	}

	// setting lead_id
	public void setstudId(String studId){
		this.studId = studId;
	}

	// getting phone number
	public String getStudentname(){
		return this.Studentname;
	}

	// setting phone number
	public void setStudentname(String Studentname){
		this.Studentname = Studentname;
	}
	public String getStudEmail(){
		return this.StudEmail;
	}

	// setting phone number
	public void setStudEmail(String StudEmail){
		this.StudEmail = StudEmail;
	}


}
