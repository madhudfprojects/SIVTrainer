package com.det.skillinvillage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.florent37.tutoshowcase.TutoShowcase;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import es.voghdev.pdfviewpager.library.util.FileUtil;

import static com.det.skillinvillage.Activity_UserManual_DownloadPDF.key_usermanualpdfurl;
import static com.det.skillinvillage.Activity_UserManual_DownloadPDF.sharedpreferenc_usermanual;

public class Activity_UserManual_OpenPDF extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, DownloadFile.Listener{

    PDFView pdfView;
    LinearLayout pdflayout_LL;
    RemotePDFViewPager remotePDFViewPager;
//    String str_fileurl = "http://hrms.sandboxstartups.org/Policies/LeavePolicy-FSSI.pdf";
    String str_fileurl = "";
    private ProgressDialog progressDialog;
    PDFPagerAdapter adapter;
    Integer pageNumber = 0;
    String pdfFileName;
    Context context;
    SharedPreferences sharedpref_usermanualpdf_Obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user_manual__open_pdf);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Manual");

        sharedpref_usermanualpdf_Obj=getSharedPreferences(sharedpreferenc_usermanual, Context.MODE_PRIVATE);
        str_fileurl = sharedpref_usermanualpdf_Obj.getString(key_usermanualpdfurl, "").trim();
      //  str_fileurl="http://mis.detedu.org:8090/Document/Help/SIV_User_manual_1.0.pdf";
        context = getApplicationContext();
        progressDialog = new ProgressDialog(Activity_UserManual_OpenPDF.this);
        if (adapter == null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        displayTuto();
        pdfView = findViewById(R.id.pdfView_usermanual);
        pdflayout_LL = findViewById(R.id.pdflayout_LL);
        final Context ctx = Activity_UserManual_OpenPDF.this;
        final DownloadFile.Listener listener = Activity_UserManual_OpenPDF.this;
        remotePDFViewPager = new RemotePDFViewPager(ctx, str_fileurl, listener);
        remotePDFViewPager.setId(R.id.pdfView_usermanual);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (adapter != null) {
            adapter.close();
        }
    }


    @Override
    public void onSuccess(String url, String destinationPath) {
        progressDialog.dismiss();
        adapter = new PDFPagerAdapter(Activity_UserManual_OpenPDF.this, FileUtil.extractFileNameFromURL(url));
        remotePDFViewPager.setAdapter(adapter);
        updateLayout();
    }

    public void updateLayout() {
        pdflayout_LL.removeAllViewsInLayout();
        //  root.addView(layout_button,LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //pdflayout_LL.addView(swipetext_tv, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pdflayout_LL.addView(remotePDFViewPager, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }



    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
        System.out.println("pageCount..." + pageCount);

    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }


    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            // Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

        if (adapter == null) {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }


    }



    protected void displayTuto() {
        TutoShowcase.from(this)
                .setListener(new TutoShowcase.Listener() {
                    @Override
                    public void onDismissed() {
                    }
                })
                .setContentView(R.layout.pdfswipe)
                .setFitsSystemWindows(true)

                .on(R.id.swipable)
                .displaySwipableLeft()
                .delayed(399)
                .animated(true)
                .show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_UserManual_OpenPDF.this, ContactUs_Activity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu items
        getMenuInflater().inflate(R.menu.menu_register, menu);
        menu.findItem(R.id.addnewstudent_menu_id)
                .setVisible(false);
        menu.findItem(R.id.save)
                .setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i = new Intent(Activity_UserManual_OpenPDF.this, ContactUs_Activity.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }



}
