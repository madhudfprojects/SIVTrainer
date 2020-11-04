package com.det.skillinvillage;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebserviceRegister {
	/*<InsertCandidateBySingle xmlns="http://detmis.cloudapp.net/">
    <studentname>string</studentname>
    <LastName>string</LastName>
    <mobilenumber>long</mobilenumber>
    <nameofthecollege>string</nameofthecollege>
    <qualification>string</qualification>
    <FellowshipId>int</FellowshipId>
    <referencename>string</referencename>
    <FeeStatus>string</FeeStatus>
    <submitemailid>string</submitemailid>
    <latitude>string</latitude>
    <longitude>string</longitude>
    <signatureimage>base64Binary</signatureimage>
	*/
	
	/*public static boolean register(String Student_Name,String Last_Name,String Mobile_Number,
		      String Name_Of_The_College,String Qualification,String Fellowship,String Reference,
		      String FeeStatus,String submitteremailid,String Slatitude,String Slongitude,byte[] signatureimage,String genders,
			 String str_father,String str_yearcode)*/


    public static boolean register(String Student_Name, String Last_Name, String Mobile_Number,
                                   String Name_Of_The_College, String Qualification, String Fellowship, String Reference,
                                   String FeeStatus, String submitteremailid, String Slatitude, String Slongitude, byte[] signatureimage, String genders,
                                   String str_father, String str_yearcode, String str_backlog, String str_habit, String str_attitude, String reportmanager_id, String str_receiptno) {

        String registerresponse = "flag";
  
  /*String URL = "http://dfindia.cloudapp.net/DETMIS/DETServices.asmx?wsdl";//"Login.asmx?WSDL";
  String METHOD_NAMEREG = "InsertCandidate";//"NewAppReleseDetails";
  String NamespaceR="http://dfinida.cloudapp.net/", SOAPACTIONREG="http://dfinida.cloudapp.net/InsertCandidate";*/
        //new http://detmis.cloudapp.net/
  
 /* String URL ="http://detmis.cloudapp.net/DETServices.asmx?wsdl";
  String METHOD_NAMEREG = "InsertCandidate";
  String NamespaceR="http://detmis.cloudapp.net/", SOAPACTIONREG="http://detmis.cloudapp.net/InsertCandidate";
		*/
//http://detmis.cloudapp.net/DETServices.asmx?op=InsertCandidate
        //new link and methodname with image uploading

        //http://mis.detedu.org/DETServices.asmx?WSDL

        String SURL = "http://mis.detedu.org/DETServices.asmx?WSDL";

        //String SURL = "http://mis.detedu.org:8085/DETServices.asmx?WSDL";

        //  	String SURL =    "http://192.168.1.133:6346/DETServices.asmx?WSDL";

 	   /*String USOAP_ACTION = "http://detmis.cloudapp.net/InsertCandidateBySingle";
     	 String UMETHOD_NAME = "InsertCandidateBySingle";
     	 String NAMESPACE = "http://detmis.cloudapp.net/";*/


		/*String USOAP_ACTION = "http://detmis.cloudapp.net/CreateCandidateApplication";
		String UMETHOD_NAME = "CreateCandidateApplication";
		String NAMESPACE = "http://detmis.cloudapp.net/";*/


        //Application_Insert_APP
        //Application_Insert_APP_With_Sandbox

        String USOAP_ACTION = "http://mis.detedu.org/Application_Insert_APP_With_Sandbox";
        String UMETHOD_NAME = "Application_Insert_APP_With_Sandbox";
        String NAMESPACE = "http://mis.detedu.org/";


//SOAPAction: "http://detmis.cloudapp.net/CreateCandidateApplication" //Dec19,2017
//http://detmis.cloudapp.net/DETServices.asmx?op=CreateCandidateApplication
        //String str_father,String str_yearcode
        try {


            long MobilenumberL = Long.valueOf(Mobile_Number);

            int Fellowshipinteger = Integer.parseInt(Fellowship);

            int integer_yearcode = Integer.parseInt(str_yearcode);

            int integer_reportmanager = Integer.parseInt(reportmanager_id);


            SoapObject request = new SoapObject(NAMESPACE, UMETHOD_NAME);

            request.addProperty("studentname", Student_Name);
            request.addProperty("LastName", Last_Name);
            request.addProperty("mobilenumber", MobilenumberL);
            request.addProperty("nameofthecollege", Name_Of_The_College);
            request.addProperty("qualification", Qualification);
            request.addProperty("FellowshipId", Fellowshipinteger);
            request.addProperty("referencename", Reference);
            request.addProperty("FeeStatus", FeeStatus);
            request.addProperty("submitemailid", submitteremailid);
            //request.addProperty("submitemailid", "shubham.dsep@detedu.org");
            request.addProperty("latitude", Slatitude);
            request.addProperty("longitude", Slongitude);
            request.addProperty("signatureimage", signatureimage);
           // request.addProperty("Branch", Activity_Register.centers);
            request.addProperty("Gender", genders);
            request.addProperty("FatherName", str_father); //<FatherName>string</FatherName>
            request.addProperty("iYear", integer_yearcode);  //<iYear>int</iYear>
            request.addProperty("Habit", str_habit);  //<Habit>string</Habit> //str_habit
            request.addProperty("Backlog", str_backlog); //<Backlog>string</Backlog> //str_backlog
            request.addProperty("Attitude", str_attitude);  //<Attitude>string</Attitude> //str_attitude
            request.addProperty("rep_Id", integer_reportmanager); // <rep_Id>int</rep_Id>
            request.addProperty("receipt_no", str_receiptno); //<receipt_no>string</receipt_no>


            // Log.i("TAG","signatureimage1="+signatureimage);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);
            envelope.dotNet = true;
            // Set output SOAP object
            Log.e("insert details", request.toString());
            envelope.setOutputSoapObject(request);


            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SURL);


            try {
                androidHttpTransport.call(USOAP_ACTION, envelope);

    	 			/*SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
    	 			Log.i("Register response:",response.toString());*/

                SoapObject response = (SoapObject) envelope.getResponse();

                response.getProperty("Status").toString();


                Log.e("appsubResponse", response.getProperty(0).toString());

					/*<Status>string</Status>
        <Application>string</Application>*/
                registerresponse = response.getProperty("Status").toString();

            } catch (Throwable t) {
                Log.e("request fail tag", "request fail catched" + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Receiver Error", "> " + t.getMessage());

        }

        if (registerresponse.equals("Records already exists")) {
            //Activity_Register.recordalreadypresent = true;
        }

        if (registerresponse.equalsIgnoreCase("Duplicate Receipt No")) {
           // Activity_Register.duplicat_receiptno = true;
        }


        if (registerresponse.equals("successfully inserted 1 Records.")) {
            return true;
        } else if (registerresponse.equals("Records already exists")) {
            return true;
        } else return registerresponse.equals("Duplicate Receipt No");

    }// end of method

//}// end of class
    
     	 
     	 
     	 
     
    
	
	
	
	
	
 /*    
     	 
  //http://detmis.cloudapp.net/DETServices.asmx?op=InsertCandidate
  
  try{
  
 <InsertCandidate xmlns="http://dfinida.cloudapp.net/">
<Candidate_Name>string</Candidate_Name>
<LastName>string</LastName>
<email>string</email>
<Mobile>string</Mobile>
<College_Name>string</College_Name>
<Qualification>string</Qualification>
<Location>string</Location>
<Fellowshipid>long</Fellowshipid>
<referenceName>string</referenceName>
<FeesStatus>string</FeesStatus>
<lat>string</lat>
<lon>string</lon>
               </InsertCandidate>

  long FellowshipL = Long.valueOf(Fellowship);
    
  SoapObject request = new SoapObject(NamespaceR, METHOD_NAMEREG);
  
  request.addProperty("Candidate_Name", Student_Name);
  request.addProperty("LastName", Last_Name);
  request.addProperty("email", Email_ID);
  request.addProperty("Mobile", Mobile_Number);
  request.addProperty("College_Name", Name_Of_The_College);
  request.addProperty("Qualification", Qualification);
  request.addProperty("Location", Location);
  request.addProperty("Fellowshipid", FellowshipL);
  request.addProperty("referenceName", Reference);
  request.addProperty("FeesStatus", FeeStatus);
  
  request.addProperty("lat",Slatitude);
  request.addProperty("lon",Slongitude);
//  request.addProperty("to", 9);
  
  SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
  envelope.dotNet = true;
  //Set output SOAP object  
  envelope.setOutputSoapObject(request);
  //Create HTTP call object
  HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
 
  try 
  {  
        
    androidHttpTransport.call(SOAPACTIONREG, envelope);
  //  Log.i(TAG, "GetAllLoginDetails is running");
//    result1 = (Vector<SoapObject>) envelope.getResponse();
    SoapPrimitive  response = (SoapPrimitive ) envelope.getResponse();
    Log.i("string value at response",response.toString());
    
    registerresponse=response.toString();
    
}
  catch (Throwable t) {
      //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
      //    Toast.LENGTH_LONG).show();
      Log.e("request fail", "> " + t.getMessage());
    }
	}catch (Throwable t) {
	      Log.e("UnRegister Receiver Error", "> " + t.getMessage());
	       
	    }
  
  if(registerresponse.equals("Record Saved."))
  {return true;}
  else
  {return false;}
 }


}
*/

    //Added by shivaleela on 4th JUly 2019
    public static boolean register_new(int sandboxid, int academicid,
                                       int clusterid, int instituteid,
                                       int levelid, int schoolid, String imgfile, String studentname,
                                       String gender, String birthdate, String education,String marks,
                                       String mobileno, String fathername, String mothername,
                                       String aadar, String application_status,
                                       String admissionfee, String created_date,
                                       String created_by,String receiptmanual,String Learning_Mode) {
//String receiptmanual
        String registerresponse_new = "";
        //boolean checkBooleanReturnValue=false;
        String SURL = "http://mis.detedu.org:8089/SIVService.asmx?WSDL";
        String USOAP_ACTION = "http://mis.detedu.org:8089/SaveStudentData";
        String UMETHOD_NAME = "SaveStudentData";
        String NAMESPACE = "http://mis.detedu.org:8089/";

        try {


//			long MobilenumberL = Long.valueOf(Mobile_Number);
//
//			int Fellowshipinteger = Integer.parseInt(Fellowship);
//
//			int integer_yearcode = Integer.parseInt(str_yearcode);
//
//			int integer_reportmanager = Integer.parseInt(reportmanager_id);

            SoapObject request = new SoapObject(NAMESPACE, UMETHOD_NAME);
            //Log.e("imgfile", imgfile);

            request.addProperty("Sandbox_ID", sandboxid);
            request.addProperty("Academic_ID", academicid);
            request.addProperty("Cluster_ID", clusterid);
            request.addProperty("Institute_ID", instituteid);
            request.addProperty("Level_ID", levelid);
            request.addProperty("School_ID", schoolid);
//            request.addProperty("File_Name", imgfile);
            request.addProperty("Student_Name", studentname);
            request.addProperty("Gender", gender);
            request.addProperty("Birth_Date", birthdate);
            request.addProperty("Education", education);
            request.addProperty("Marks", marks);
            request.addProperty("Mobile", mobileno);
            request.addProperty("Father_Name", fathername);
            request.addProperty("Mother_Name", mothername);
            request.addProperty("Student_Aadhar", aadar);
            request.addProperty("Student_Status", application_status);
            if (application_status.equals("Admission")) {
                Log.e("application_status", "Admission");
                request.addProperty("Admission_Fee", admissionfee);
            }else{
                Log.e("application_status", "Applicant");

                request.addProperty("Admission_Fee", "");

            }
            request.addProperty("Created_Date", created_date);  //<iYear>int</iYear>
            request.addProperty("Created_By", created_by);  //<Habit>string</Habit> //str_habit
            request.addProperty("File_Name", imgfile);
            request.addProperty("Receipt_Manual", receiptmanual);
            request.addProperty("Learning_Mode",Learning_Mode);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);
            envelope.dotNet = true;
            // Set output SOAP object
            Log.e("insert details1", request.toString());
            envelope.setOutputSoapObject(request);


            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SURL);


            try {
                androidHttpTransport.call(USOAP_ACTION, envelope);


                SoapObject response_new = (SoapObject) envelope.getResponse();


                Log.e("appsubResponse", response_new.toString());


                Log.e("status", response_new.getProperty(0).toString());

                if (response_new.getProperty(0).toString().contains("Student_Status")) {
                    if (response_new.getProperty(0).toString().contains("Student_Status=Applicant")) {


                        Log.e("Student_Status", "Applicant");
                        return true;
                    } else if (response_new.getProperty(0).toString().contains("Student_Status=Admission")) {

                        Log.e("Student_Status", "Admission");
                        return true;

                    }else if (response_new.getProperty(0).toString().contains("Student_Status=Error")) {

                      //  Toast.makeText(WebserviceRegister.this,"")
                        Log.e("Student_Status", "Error");
                        return false;

                    }


                }

            } catch (Throwable t) {
                Log.e("request fail tag", "request fail catched" + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("Receiver Error", "> " + t.getMessage());

        }


        return false;

    }// end of method


}
