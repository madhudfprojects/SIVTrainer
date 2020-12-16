package com.det.skillinvillage.remote;
import com.det.skillinvillage.model.AddStudentDetailsRequest;
import com.det.skillinvillage.model.AddStudentDetailsResponse;
import com.det.skillinvillage.model.AutoSyncVersion;
import com.det.skillinvillage.model.Class_AssessmentData;
import com.det.skillinvillage.model.Class_Get_User_DocumentResponse;
import com.det.skillinvillage.model.Class_PostUpdateStudentAssessmentRequest;
import com.det.skillinvillage.model.Class_PostUpdateStudentAssessmentResponse;
import com.det.skillinvillage.model.Class_Post_UpdateStudentAssessmentSubmitRequest;
import com.det.skillinvillage.model.Class_Post_UpdateStudentAssessmentSubmitResponse;
import com.det.skillinvillage.model.Class_devicedetails;
import com.det.skillinvillage.model.Class_getUserDashboardResponse;
import com.det.skillinvillage.model.Class_getUserPaymentResponse;
import com.det.skillinvillage.model.Class_getVillageLatLong;
import com.det.skillinvillage.model.Class_getassessmentlistResponse;
import com.det.skillinvillage.model.Class_getdemo_Response;
import com.det.skillinvillage.model.Class_gethelp_Response;
import com.det.skillinvillage.model.Class_getuserlist;
import com.det.skillinvillage.model.GetAppVersion;
import com.det.skillinvillage.model.GetMobileMenuResponse;
import com.det.skillinvillage.model.GetPaymentPendingSummaryResponse;
import com.det.skillinvillage.model.GetPendingPaymentResponse;
import com.det.skillinvillage.model.GetScheduleLessonPlanResponse;
import com.det.skillinvillage.model.GetStudentPaymentResponse;
import com.det.skillinvillage.model.Location_Data;
import com.det.skillinvillage.model.NormalLogin_Response;
import com.det.skillinvillage.model.PostSavePaymentRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateRequest;
import com.det.skillinvillage.model.PostScheduleLessonUpdateResponse;
import com.det.skillinvillage.model.Post_Save_PaymentResponse;
import com.det.skillinvillage.model.Student;
import com.det.skillinvillage.model.ValidateSyncRequest;
import com.det.skillinvillage.model.ValidateSyncResponse;
import com.det.skillinvillage.util.Post_studData_Request;
import com.det.skillinvillage.util.StudentData_Response;
import com.det.skillinvillage.util.StudentInfoRest;
import com.det.skillinvillage.util.Subjects;
import com.det.skillinvillage.util.UserInfoRest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Interface_userservice {

    @POST("Authentication/Post_ValidateLogin")
    @FormUrlEncoded
    Call<NormalLogin_Response> getValidateLoginPostNew(@Field("User_Email") String userEmail);

    @POST("Authentication/Post_ValidateNormalLogin")
    @FormUrlEncoded
    Call<NormalLogin_Response> getNormalLoginPost(@Field("User_Email") String userEmail,@Field("User_Password") String userPassword);

    @GET("Authentication/Get_UserList")
    Call<Class_getuserlist> get_userlist();

    //http://mis.detedu.org:8089/api/Authentication/Get_User_Document?User_ID=12
    @GET("Authentication/Get_User_Document")
    Call<Class_Get_User_DocumentResponse> getdocumentview(@Query("User_ID") String UserId);


    @GET("Authentication/Get_User_Dashboard")
    Call<Class_getUserDashboardResponse> getdashboardDetails(@Query("User_ID") String UserId);



    @GET("Authentication/Get_User_Payment_Status")
    Call<Class_getUserPaymentResponse> getuserpaymentstatus(@Query("User_ID") String UserId);


    @GET("Authentication/Get_Village_LatLong")
    Call<Class_getVillageLatLong>getVillageLatLong(@Query("User_ID") String UserId);


//    @GET("Authentication/GetHelp")
//    Call<Class_gethelp_Response>GetHelp(@Query("User_ID") String UserId);
//
//
//    @GET("Authentication/GetDemo")
//    Call<Class_getdemo_Response>GetDemo(@Query("User_ID") String UserId);
//

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/GetHelp")
    Call<Class_gethelp_Response>GetHelp(@Query("User_ID") String User_ID);


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/GetDemo")
    Call<Class_getdemo_Response>GetDemo(@Query("User_ID") String User_ID);


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_Assessment_List")
    Call<Class_getassessmentlistResponse>GetAssesmentList(@Query("User_ID") String User_ID);


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_Assessment_Data")
    Call<Class_AssessmentData>GetAssesmentData(@Query("Assessment_ID") String Assessment_ID);


    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("Authentication/Post_UpdateStudentAssessment")
    Call<Class_PostUpdateStudentAssessmentResponse>PostUpdateStudentAssessment(@Body Class_PostUpdateStudentAssessmentRequest request);


    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("Authentication/Post_UpdateStudentAssessmentSubmit")
    Call<Class_Post_UpdateStudentAssessmentSubmitResponse>PostUpdateStudentAssessmentSubmit(@Body Class_Post_UpdateStudentAssessmentSubmitRequest request);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Authentication/Post_ActionDeviceDetails")
    Call<Class_devicedetails>Post_ActionDeviceDetails(@Body Class_devicedetails request);

    @GET("Authentication/Get_App_Version")
    Call<GetAppVersion> getAppVersion();

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_User_Location")
    Call<Location_Data> getLocationData(@Query("User_ID") String User_ID,@Query("Application_Type") String Application_type);


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_User_Data")
    Call<Student> getStudentData(@Query("User_ID") String User_ID);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Authentication/Post_ValidateSync")
    Call<ValidateSyncResponse> Post_ValidateSync(@Body ValidateSyncRequest request);

    @GET("Authentication/Get_Sync_Version")
    Call<AutoSyncVersion> getAutoSyncVersion(@Query("User_ID") String User_ID);

    @Headers("Content-Type: application/json")
    @GET("Authentication/Get_User_Data_Sync")
    Call<Student> getStudentDataReSync(@Query("User_ID") String User_ID);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Authentication/Post_ActionStudent")
    Call<AddStudentDetailsResponse> Post_ActionStudent(@Body AddStudentDetailsRequest request);




    @Headers("Content-Type: application/json")
    @GET("Authentication/Get_Student_Payment")
    Call<GetStudentPaymentResponse> getStudentPayment(@Query("Student_ID") String Student_ID);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Authentication/Post_Save_Payment")
    Call<Post_Save_PaymentResponse> PostSavePayment(@Body PostSavePaymentRequest request);


    @Headers("Content-Type: application/json")
    @GET("Authentication/Get_Payment_Pending_Summary")
    Call<GetPaymentPendingSummaryResponse>GetPaymentPendingSummary(@Query("User_ID") String User_ID);


    @Headers("Content-Type: application/json")
    @GET("Authentication/Get_Pending_Payment")
    Call<GetPendingPaymentResponse> GetPendingPayment(@Query("User_ID") String User_ID);




    //////////////////---------Schedule--------------/////////////////////////////////////////////
    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_User_Schedule")
    Call<UserInfoRest> get_User_Schedule(@Query("User_ID") String User_ID);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_User_Schedule_Students")
    Call<StudentInfoRest> get_User_Schedule_Students(@Query("Schedule_ID") String Schedule_ID);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Authentication/Post_ActionScheduleAttendance")
    Call<StudentData_Response> post_ActionScheduleAttendance(@Body Post_studData_Request request);

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_Schedule_Subject")
    Call<Subjects> get_Schedule_Subject(@Query("Schedule_ID") String Schedule_ID);


//http://mis.detedu.org:8090/api/Authentication/Get_Mobile_Menu?User_ID=12


    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_Mobile_Menu")
    Call<GetMobileMenuResponse> GetMobileMenu(@Query("User_ID") String User_ID);


   // http://mis.detedu.org:8090/api/Authentication/Get_Schedule_Lesson_Plan?Schedule_ID=56

    @Headers("Content-Type: application/json;charset=utf-8")
    @GET("Authentication/Get_Schedule_Lesson_Plan")
    Call<GetScheduleLessonPlanResponse>GetScheduleLessonPlan(@Query("Schedule_ID") String Schedule_ID);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Authentication/Post_Schedule_Lesson_Update")
    Call<PostScheduleLessonUpdateResponse> PostScheduleLessonUpdate(@Body PostScheduleLessonUpdateRequest request);

}

