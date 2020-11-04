package com.det.skillinvillage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.io.File;
import java.net.URL;

public class DocView_QunPaperActivity extends AppCompatActivity {

    private Context context;

    String str_loginuserID;
    private int count1;
    private int count2;
    String serverPath="http://mis.detedu.org:8089/";
    private String wordDocPath="null";
    private String wordDocumentName;
    private String docName;
    private URL downloadUrl;
    File outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view__qun_paper);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Question Paper");

        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        str_loginuserID = myprefs.getString("login_userid", "nothing");

        Doc_QunPaperTabAdapter docQunPaperTabAdapter = new Doc_QunPaperTabAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(docQunPaperTabAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i=new Intent(DocView_QunPaperActivity.this,DocView_MainActivity.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(DocView_QunPaperActivity.this,DocView_MainActivity.class);
        startActivity(i);
        finish();

    }
}
