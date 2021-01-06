package com.det.skillinvillage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.view.View.GONE;

public class Doc_LessonPlanDownloadFragment extends Fragment {

  private static final String TAG = "Download PDF";

  private PageViewModel pageViewModel;

  private static RecyclerView.Adapter adapter_recycler;
  RecyclerView myrecyclerview_view;
  LinearLayoutManager MyLayoutManager;

  private static RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private static RecyclerView recyclerView;

  private static ProgressBar progressBar;
  static File outputFile = null;
  public static String docName;
  public static String docName1;
  public static String docName2;
  public static URL downloadUrl;

  ConnectionDetector internetDectector;
  Boolean isInternetPresent = false;

  String str_Document_ID,str_Document_Date,str_Document_Time,str_Document_Name,wordDocPath,str_Document_Type,str_Document_Status;

  public Doc_LessonPlanDownloadFragment() {
    // Required empty public constructor
  }

  /**
   * @return A new instance of fragment QunPaperDocDownloadFragment.
   */
  public static Doc_LessonPlanDownloadFragment newInstance() {
    return new Doc_LessonPlanDownloadFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
    pageViewModel.setIndex(TAG);


  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.fragment_downloadpdf, container, false);
    //final TextView textView = root.findViewById(R.id.section_label);
    progressBar = root.findViewById(R.id.progressBar);
    TextView no_internet= root.findViewById(R.id.no_internet);
    internetDectector = new ConnectionDetector(getContext());
    isInternetPresent = internetDectector.isConnectingToInternet();

    if(isInternetPresent) {
      no_internet.setVisibility(GONE);
      recyclerView = root.findViewById(R.id.my_recycler_view);

      recyclerView.setHasFixedSize(true);

      layoutManager = new LinearLayoutManager(getActivity());
      recyclerView.setLayoutManager(layoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());

    /*  for (int i = 0; i < DocView_Module.listview_arr.size(); i++) {
        Log.e("tag", "DocView_Module ID" + DocView_Module.listview_arr.get(i).Document_ID);
        Log.e("tag", "DocView_Module Document_Name" + DocView_Module.listview_arr.get(i).Document_Name);
        Log.e("tag", "DocView_Module Document_Path" + DocView_Module.listview_arr.get(i).Document_Path);
        Log.e("tag", "DocView_Module Document_Time" + DocView_Module.listview_arr.get(i).Document_Time);
        Log.e("tag", "DocView_Module Document_Type" + DocView_Module.listview_arr.get(i).Document_Type);
*/
        /*str_Document_ID=DocView_Module.listview_arr.get(i).Document_ID;
        str_Document_Name=DocView_Module.listview_arr.get(i).Document_Name;
        str_Document_Date=DocView_Module.listview_arr.get(i).Document_Date;
        str_Document_Time=DocView_Module.listview_arr.get(i).Document_Time;
        str_Document_Status=DocView_Module.listview_arr.get(i).Document_Status;
        str_Document_Type=DocView_Module.listview_arr.get(i).Document_Type;
        wordDocPath=DocView_Module.listview_arr.get(i).Document_Path;

        if(str_Document_Type.equalsIgnoreCase("Question Paper")) {
          QunPaperDoc_Module.listview_arr.add(new QunPaperDoc_Module(str_Document_ID, str_Document_Date, str_Document_Time, str_Document_Name, wordDocPath, str_Document_Type, str_Document_Status));
        }
        else if(str_Document_Type.equalsIgnoreCase("Lesson Plan")) {
          LessionPlanDoc_Module.listview_arr.add(new LessionPlanDoc_Module(str_Document_ID, str_Document_Date, str_Document_Time, str_Document_Name, wordDocPath, str_Document_Type, str_Document_Status));
        }*/

      //}

      Log.e("tag","LessionPlanDoc_Module.listview_arr.size="+LessionPlanDoc_Module.listview_arr.size());
      adapter = new Doc_DownloadLessonAdapter(getActivity(), LessionPlanDoc_Module.listview_arr);
      recyclerView.setAdapter(adapter);
    }else
    {
      Toast.makeText(getContext(),"No Internet", Toast.LENGTH_SHORT).show();
      no_internet.setVisibility(View.VISIBLE);
      no_internet.setText("There is No Internet Connection");
    }


    /*pageViewModel.getText().observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });*/
    return root;
  }

  public static class LoadDocument extends AsyncTask<String, Void, Void> {
    //    private ProgressDialog progressDialog;
    private Context context;



    private String downloadFileName = "";
    private static final String TAG = "Download Task";
    File apkStorage = null;



    LoadDocument (Context context){
      this.context = context;
      //      progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {

      progressBar.setVisibility(View.VISIBLE);
//            progressDialog=new ProgressDialog(context);

    /*        progressDialog.setMessage("Downloading...");
            progressDialog.show();*/

           /* progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
    }

    @Override
    protected Void doInBackground(String... params) {
          /*  ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;
            try {
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

          try {


            String doc_path = params[0];
            docName1 = params[1];
            try {
              downloadUrl = new URL(doc_path);
            } catch (MalformedURLException e) {
              e.printStackTrace();
             // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

            }
            HttpURLConnection c = null;//Open Url Connection
            try {
              c = (HttpURLConnection) downloadUrl.openConnection();
            } catch (IOException e) {
              e.printStackTrace();
             // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

            }
            try {
              c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
            } catch (ProtocolException e) {
              e.printStackTrace();
              //Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

            }
            try {
              c.connect();//connect the URL Connection
            } catch (IOException e) {
              e.printStackTrace();
              //Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

            }

            //If Connection response is not OK then show Logs
            try {
              if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                        + " " + c.getResponseMessage());

              }
            } catch (IOException e) {
              e.printStackTrace();
            //  Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

            }


            //Get File if SD card is present
            if (new CheckForSDCard().isSDCardPresent()) {

              apkStorage = new File(
                      Environment.getExternalStorageDirectory() + "/"
                              + "LessonPlan_DocFile");
            } else
              Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

            //If File is not present create directory
            if (!apkStorage.exists()) {
              apkStorage.mkdir();
              Log.e(TAG, "Directory Created.");
            }

            //docName = docName.replace("%20","");
            String[] docNameArray = doc_path.split("/");
            docName2 = docNameArray[5];
            docName = docName1 + "_" + docName2;
            Log.e("doclengthisss", String.valueOf(docNameArray.length));
            Log.e("Docnameesssss", docName);
            outputFile = new File(apkStorage, docName);//Create Output file in Main File
            Log.e("outputFile", String.valueOf(outputFile));
            //Create New File if not present
            if (!outputFile.exists()) {
              try {
                outputFile.createNewFile();
              } catch (IOException e) {
                e.printStackTrace();
               // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
              }
              Log.e(TAG, "File Created");
            }

            FileOutputStream fos = null;//Get OutputStream for NewFile Location
            try {
              fos = new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            //  Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
            }

            InputStream is = null;//Get InputStream for connection
            try {
              is = c.getInputStream();
            } catch (IOException e) {
              e.printStackTrace();
             // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
            }

            byte[] buffer = new byte[1024];//Set buffer type
            int len1 = 0;//init length
            try {
              while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);//Write new file
              }
            } catch (IOException e) {
              e.printStackTrace();
             // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();

            }

            //Close all connection after doing task
            try {
              fos.close();

              is.close();
            } catch (IOException e) {
              e.printStackTrace();
             // Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
            }

          } catch (Exception e) {
            e.printStackTrace();
            //DocView_MainActivity.runOnUiThread(new Runnable() {
             // public void run() {
              //  Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
             /* }
            });*/
          }

      return null;

      //return bitmaplogo;
    }

    @Override
    protected void onPostExecute(Void string) {
    /*        bitmapList.add(bitmap);

            Log.d("count1iss", String.valueOf(count1));
            Log.d("count2iss", String.valueOf(count2));
            Log.d("Bitmapsizess", String.valueOf(bitmapList.size()));
            if(bitmapList.size()==count2){
                img_mainGallery.setImageBitmap(bitmapList.get(0));
                txt_numOfImages.setText("Images: "+bitmapList.size());
            }*/

      try {
        if (outputFile != null) {
          //   progressDialog.dismiss();
          progressBar.setVisibility(GONE);
          Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();

          try {
            //FileOpen.openFile(mContext, myFile);
            FileOpen.openFile(context, outputFile);
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {

          new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
          }, 3000);

          Log.e(TAG, "Download Failed");

        }
      } catch (Exception e) {
        e.printStackTrace();

        //Change button text if exception occurs

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

          }
        }, 3000);
        Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

      }

      //   progressDialog.dismiss();

      progressBar.setVisibility(GONE);
    }
  }

  public static class FileOpen {

    public static void openFile(Context context, File url) throws IOException {
      // Create URI
      File file=url;
      //  Uri uri = Uri.fromFile(file);
      Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

      Intent intent = new Intent(Intent.ACTION_VIEW);
      // Check what kind of file you are trying to open, by comparing the url with extensions.
      // When the if condition is matched, plugin sets the correct intent (mime) type,
      // so Android knew what application to use to open the file
      if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
        // Word document
        intent.setDataAndType(uri, "application/msword");
      } else if(url.toString().contains(".pdf")) {
        // PDF file
        intent.setDataAndType(uri, "application/pdf");
      } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
        // Powerpoint file
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
      } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
        // Excel file
        intent.setDataAndType(uri, "application/vnd.ms-excel");
      } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
        // WAV audio file
        intent.setDataAndType(uri, "application/x-wav");
      } else if(url.toString().contains(".rtf")) {
        // RTF file
        intent.setDataAndType(uri, "application/rtf");
      } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
        // WAV audio file
        intent.setDataAndType(uri, "audio/x-wav");
      } else if(url.toString().contains(".gif")) {
        // GIF file
        intent.setDataAndType(uri, "image/gif");
      } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
        // JPG file
        intent.setDataAndType(uri, "image/jpeg");
      } else if(url.toString().contains(".txt")) {
        // Text file
        intent.setDataAndType(uri, "text/plain");
      } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
        // Video files
        intent.setDataAndType(uri, "video/*");
      } else {
        //if you want you can also define the intent type for any other file

        //additionally use else clause below, to manage other unknown extensions
        //in this case, Android will show all applications installed on the device
        //so you can choose which application to use
        intent.setDataAndType(uri, "*/*");
      }

      //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      context.startActivity(intent);
    }
  }


}
