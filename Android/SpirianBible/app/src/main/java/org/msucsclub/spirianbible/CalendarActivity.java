package org.msucsclub.spirianbible;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout calendarLayout = (LinearLayout) findViewById(R.id.calendarLayout);
        LinearLayout tempLayout;
        LinearLayout.LayoutParams tempParams;

        String[] colors = { "#ffb5d6e1", "#20394018", "#482058a1", "#af492057", "#ab491041", "#d3104951", "#60294aa2"};

        for (int i = 0; i < 7; i++) {
            tempLayout = new LinearLayout(getApplicationContext());
            tempLayout.setBackgroundColor(Color.parseColor(colors[i]));
//            tempParams = (LinearLayout.LayoutParams) tempLayout.getLayoutParams();
            tempParams = new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/7, 800);
            tempLayout.setLayoutParams(tempParams);
            calendarLayout.addView(tempLayout);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
