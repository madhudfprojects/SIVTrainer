package com.det.skillinvillage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import static com.det.skillinvillage.Activity_UserManual_DownloadPDF.key_usermanualpdfurl;
import static com.det.skillinvillage.Activity_UserManual_DownloadPDF.sharedpreferenc_usermanual;

public class Doc_LessonPlan_PdfActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    public static final String SAMPLE_FILE = "android_tutorial.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String TAG="PdfActivity";
    int position=-1;


    SharedPreferences sharedpref_usermanualpdf_Obj;
    String str_fileurl="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        sharedpref_usermanualpdf_Obj=getSharedPreferences(sharedpreferenc_usermanual, Context.MODE_PRIVATE);
        str_fileurl = sharedpref_usermanualpdf_Obj.getString(key_usermanualpdfurl, "").trim();
        init();

    }

    private void init(){
        pdfView= findViewById(R.id.pdfView);
       position = getIntent().getIntExtra("position",-1);
        displayFromSdcard();
    }

    private void displayFromSdcard() {
        pdfFileName = Doc_LessonPlanViewFragment.fileList.get(position).getName();

        pdfView.fromFile(Doc_LessonPlanViewFragment.fileList.get(position))
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
}
