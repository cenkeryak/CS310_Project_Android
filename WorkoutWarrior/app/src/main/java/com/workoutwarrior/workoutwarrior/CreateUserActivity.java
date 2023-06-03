package com.workoutwarrior.workoutwarrior;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class CreateUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner genderSpinner;
    EditText nameField,ageField,heightField,weightField;
    Button createUserButton2;

    String selectedGender;
    SharedPreferences sharedPreferences;
    Handler idHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            String userId = msg.obj.toString();

            // Store ID
            sharedPreferences.edit().putString("userId",userId).commit();

            Log.i("DEV","stored id is: " + sharedPreferences.getString("userId",""));

            // Go to next activity while closing this activity

            if (userId != ""){
                // for temporary, this will open userinfo Screen
                Intent intent = new Intent(CreateUserActivity.this,UserInfoActivity.class);
                startActivity(intent);

                finish();
            } else {

                Toast.makeText(CreateUserActivity.this, "There is a problem with creating user", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Setting the spinner
        genderSpinner = (Spinner) findViewById(R.id.GenderSpinner);
        ArrayAdapter<CharSequence> arrAdp = ArrayAdapter.createFromResource(this,R.array.genders, android.R.layout.simple_spinner_item);
        arrAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrAdp);
        genderSpinner.setOnItemSelectedListener(this);

        // Getting the text from the fields
        nameField = (EditText) findViewById(R.id.UsernameTextView);
        ageField = (EditText) findViewById(R.id.AgeTextView);
        heightField = (EditText) findViewById(R.id.HeightTextView);
        weightField = (EditText) findViewById(R.id.WeightTextView);
        createUserButton2 = (Button) findViewById(R.id.CreateUserButton2);

        // Default Gender is Male;
        selectedGender = "Male";

        // setting the shared preferences
        sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);

        createUserButton2.setOnClickListener(v -> {
            WorkoutwarriorRepo repo = new WorkoutwarriorRepo();

            // Update the application name if necessary
            ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;

            if (nameField.getText().toString().length() == 0) {
                Toast.makeText(this, "You did not enter a name", Toast.LENGTH_SHORT).show();
                return;
            } else if (ageField.getText().toString().length() == 0) {
                Toast.makeText(this, "You did not enter a age", Toast.LENGTH_SHORT).show();
                return;

            } else if (weightField.getText().toString().length() == 0) {
                Toast.makeText(this, "You did not enter a weight", Toast.LENGTH_SHORT).show();
                return;

            }else if (heightField.getText().toString().length() == 0) {
                Toast.makeText(this, "You did not enter a height", Toast.LENGTH_SHORT).show();
                return;

            }

            // TODO Input Check needs to be done before parsing

            // If input is not requested as program want, Message can be returned to user.
            int ageInt = Integer.parseInt(ageField.getText().toString());
            double weightDouble = Double.parseDouble(weightField.getText().toString());
            double heightDouble = Double.parseDouble(heightField.getText().toString());



            repo.createUser(srv,idHandler,nameField.getText().toString(),ageInt,weightDouble,heightDouble,selectedGender);
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // updates the selected gender.
        selectedGender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}