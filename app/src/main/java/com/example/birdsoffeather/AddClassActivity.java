package com.example.birdsoffeather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddClassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String selectedYear, selectedQuarter, subject, course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        Spinner yearSpinner = (Spinner) findViewById(R.id.YearDropDown);
        Spinner quarterSpinner = (Spinner) findViewById(R.id.QuarterDropDown);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.Year, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> quarterAdapter = ArrayAdapter.createFromResource(this,
                R.array.Quarter, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(yearAdapter);
        quarterSpinner.setAdapter(quarterAdapter);
        yearSpinner.setOnItemSelectedListener(this);
        quarterSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if(parent.getId() == R.id.YearDropDown)
            selectedYear = (String) parent.getItemAtPosition(pos);
        if(parent.getId() == R.id.QuarterDropDown)
            selectedQuarter = (String) parent.getItemAtPosition(pos);
        //next few lines for testing purposes
        TextView t1 = findViewById(R.id.textView);
        t1.setText(selectedYear);
        TextView t2 = findViewById(R.id.textView3);
        t2.setText(selectedQuarter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}