package com.det.skillinvillage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.io.File;
import java.net.URL;

public class DocView_LessonPlanActivity extends AppCompatActivity {

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
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_view__qun_paper);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lesson Plan");

        SharedPreferences myprefs = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        str_loginuserID = myprefs.getString("login_userid", "nothing");

        Doc_LessonPlanTabAdapter doc_lessonPlanTabAdapter = new Doc_LessonPlanTabAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(doc_lessonPlanTabAdapter);

        tabs = findViewById(R.id.tabs);
        tabselect(viewPager);



    }
    public void tabselect(ViewPager view){
        tabs.setupWithViewPager(view);
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabs.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabs.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {


            case android.R.id.home:
                Intent i=new Intent(DocView_LessonPlanActivity.this,DocView_MainActivity.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(DocView_LessonPlanActivity.this,DocView_MainActivity.class);
        startActivity(i);
        finish();

    }

}
