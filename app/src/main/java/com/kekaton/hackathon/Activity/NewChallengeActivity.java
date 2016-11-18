package com.kekaton.hackathon.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kekaton.hackathon.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewChallengeActivity extends AppCompatActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.description) EditText description;
    @Bind(R.id.confirmBtn) Button confirmBtn;
    @Bind(R.id.quantity) EditText quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_challenge_view);
        ButterKnife.bind(this);

        mToolbar.setTitle("Новый челлендж");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typeSelection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Clicked!", Toast.LENGTH_SHORT).show();
                Log.d("DESC", description.getText().toString());
                Log.d("QUANT", quantity.getText().toString());
            }
        };
        confirmBtn.setOnClickListener(oclBtnOk);
    }
}
