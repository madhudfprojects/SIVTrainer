package com.det.skillinvillage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Activity_LessonVerification extends AppCompatActivity {

    Spinner questions_LPverification_Spinner;
    Button submit_bt;
    String[] status_scheduler_SpinnerArray={"Question-1","Question-2","Question-3"};
    EditText Comments_ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_verification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lesson Verification");

        questions_LPverification_Spinner=(Spinner)findViewById(R.id.questions_LPverification_Spinner);
        submit_bt=(Button)findViewById(R.id.submit_LPverification_bt);
        Comments_ET=(EditText)findViewById(R.id.Comments_ET);

        ArrayAdapter dataAdapter_edu = new ArrayAdapter(getApplicationContext(), R.layout.spinnercenterstyle, status_scheduler_SpinnerArray);
        dataAdapter_edu.setDropDownViewResource(R.layout.spinnercenterstyle);
        questions_LPverification_Spinner.setAdapter(dataAdapter_edu);
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_LessonVerification.this, "Submitted Successfully!!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.schedule_menu, menu);
        menu.findItem(R.id.logout_menu)
                .setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Show toast when menu items selected
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
                startActivity(i);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Activity_LessonVerification.this, DocView_LessonPlanActivity.class);
        startActivity(i);
        finish();

    }

}