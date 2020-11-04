package com.det.skillinvillage;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Doc_LessonPlanViewFragment extends Fragment {

  private static final String TAG = "Recents";

  private PageViewModel pageViewModel;

  ListView lv_pdf;
  public static ArrayList<File> fileList = new ArrayList<File>();
  Doc_PDFAdapter obj_adapter;
  public static int REQUEST_PERMISSIONS = 1;
  boolean boolean_permission;
  File dir;
  File outputFile = null;
  SwipeRefreshLayout mSwipeRefreshLayout;
  public Doc_LessonPlanViewFragment() {
    // Required empty public constructor
  }

  /**
   * @return A new instance of fragment QunPaperDocViewFragment.
   */
  public static Doc_LessonPlanViewFragment newInstance() {
    return new Doc_LessonPlanViewFragment();
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
    View root = inflater.inflate(R.layout.fragment_viewpdf, container, false);
    /*final TextView textView = root.findViewById(R.id.section_label);
    pageViewModel.getText().observe(this, new Observer<String>() {
      @Override
      public void onChanged(@Nullable String s) {
        textView.setText(s);
      }
    });*/
    Toast.makeText(getActivity(),"Please drag to refresh when clicked on View Document",Toast.LENGTH_LONG).show();

    init(root);
    return root;
  }

  private void init(View root) {

    lv_pdf = root.findViewById(R.id.lv_pdf);
    //dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh);
    if (new CheckForSDCard().isSDCardPresent()) {

      dir = new File(
              Environment.getExternalStorageDirectory() + "/"
                      + "LessonPlan_DocFile");
    } else
      Toast.makeText(getContext(), "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

    fn_permission();


    lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (fileList.get(i).getName().endsWith(".doc")||fileList.get(i).getName().endsWith(".docx")) {
          try {
            //FileOpen.openFile(mContext, myFile);
            Log.e("tAg","fileList.get(i).getName()="+fileList.get(i).getName());
            outputFile = new File(dir, fileList.get(i).getName());
            Doc_QunPaperDownloadFragment.FileOpen.openFile(getContext(), outputFile);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }else {


          Intent intent = new Intent(getContext(), Doc_LessonPlan_PdfActivity.class);
          intent.putExtra("position", i);
          startActivity(intent);

          Log.e("Position", i + "");
        }
        /*Intent intent = new Intent(getContext(), Doc_LessonPlan_PdfActivity.class);
        intent.putExtra("position", i);
        startActivity(intent);

        Log.e("Position", i + "");*/
      }
    });

    mSwipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {
              @Override
              public void onRefresh() {
                Log.i("tag", "onRefresh called from SwipeRefreshLayout");

                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                if (new CheckForSDCard().isSDCardPresent()) {

                  dir = new File(Environment.getExternalStorageDirectory() + "/"
                                  + "LessonPlan_DocFile");
                } else
                  Toast.makeText(getContext(), "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                fn_permission();
              }
            }
    );
  }

  public ArrayList<File> getfile(File dir) {
      File[] listFile = dir.listFiles();
    if (listFile != null && listFile.length > 0) {
      for (int i = 0; i < listFile.length; i++) {

        if (listFile[i].isDirectory()) {
          getfile(listFile[i]);

        } else {

          boolean booleanpdf = false;
      //    if (listFile[i].getName().endsWith(".pdf")) {
          if (listFile[i].getName().endsWith(".pdf")||listFile[i].getName().endsWith(".doc")||listFile[i].getName().endsWith(".docx")) {

            for (int j = 0; j < fileList.size(); j++) {
              if (fileList.get(j).getName().equals(listFile[i].getName())) {
                booleanpdf = true;
              } else {

              }
            }

            if (booleanpdf) {
              booleanpdf = false;
            } else {
              fileList.add(listFile[i]);

            }
          }
        }
      }
    }
    return fileList;
  }


  private void fn_permission() {
    if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

      if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))) {


      } else {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSIONS);

      }
    } else {
      boolean_permission = true;

      getfile(dir);

      obj_adapter = new Doc_PDFAdapter(getContext(), fileList);
      lv_pdf.setAdapter(obj_adapter);
      mSwipeRefreshLayout.setRefreshing(false);
    }
  }
  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_PERMISSIONS) {

      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        boolean_permission = true;
        getfile(dir);

        obj_adapter = new Doc_PDFAdapter(getContext(), fileList);
        lv_pdf.setAdapter(obj_adapter);

      } else {
        Toast.makeText(getContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

      }
    }
  }

}
