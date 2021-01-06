package com.det.skillinvillage;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

public class Doc_QunPaperViewFragment extends Fragment {

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
  public Doc_QunPaperViewFragment() {
    // Required empty public constructor
  }

  /**
   * @return A new instance of fragment QunPaperDocViewFragment.
   */
  public static Doc_QunPaperViewFragment newInstance() {
    return new Doc_QunPaperViewFragment();
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
                      + "QunPaper_DocFile");
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


          Intent intent = new Intent(getContext(), Doc_QunPaper_PdfActivity.class);
          intent.putExtra("position", i);
          startActivity(intent);

          Log.e("Position", i + "");
        }
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

                  dir = new File(
                          Environment.getExternalStorageDirectory() + "/"
                                  + "QunPaper_DocFile");
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

      if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE))) {


      } else {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_PERMISSIONS);

      }
    } else {
      boolean_permission = true;

      getfile(dir);

//      obj_adapter = new Doc_PDFAdapter(getContext(), fileList,DocView_Module.listview_arr);
      obj_adapter = new Doc_PDFAdapter(getContext(), fileList);

      lv_pdf.setAdapter(obj_adapter);
      mSwipeRefreshLayout.setRefreshing(false);
    }
  }

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
    }
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    context.startActivity(intent);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_PERMISSIONS) {

      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        boolean_permission = true;
        getfile(dir);

//        obj_adapter = new Doc_PDFAdapter(getContext(), fileList,DocView_Module.listview_arr);
        obj_adapter = new Doc_PDFAdapter(getContext(), fileList);

        lv_pdf.setAdapter(obj_adapter);

      } else {
        Toast.makeText(getContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

      }
    }
  }

}
