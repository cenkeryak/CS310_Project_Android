package com.workoutwarrior.workoutwarrior;

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

public class UpdateUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner genderSpinner;
    EditText nameField,ageField,heightField,weightField;
    Button updateUserButton;

    String selectedGender;
    SharedPreferences sharedPreferences;

    Handler userInfoHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            UserInfo userInfo = (UserInfo) msg.obj;
            if (userInfo.getUserId() != "" && userInfo.getUserId() != null ){
                nameField.setText(userInfo.getName());
                ageField.setText(String.valueOf(userInfo.getAge()));
                selectedGender = userInfo.getGender();


                heightField.setText(String.valueOf(userInfo.getHeight()));
                weightField.setText(String.valueOf(userInfo.getWeight()));



                // Setting the default spinner value
                if (userInfo.getGender().charAt(0) == 'M'){
                    genderSpinner.setSelection(0);

                }else if (userInfo.getGender().charAt(0) == 'F'){
                    genderSpinner.setSelection(1);

                }



            } else {
                // TODO: print necessary error messages
                Log.e("DEV","User Info is Null");
            }




            return true;
        }
    });

    Handler updateUserHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            String userId = msg.obj.toString();

            if (userId != "" && userId != null){

                Log.i("DEV","Update is done successfully on user with ID: " + userId);
                Toast.makeText(UpdateUserActivity.this, "User is successfully updated", Toast.LENGTH_LONG).show();

                finish();
            }else {
                // TODO: PRINT ERROR
            }

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        // Setting the spinner
        genderSpinner = (Spinner) findViewById(R.id.GenderSpinner2);
        ArrayAdapter<CharSequence> arrAdp = ArrayAdapter.createFromResource(this,R.array.genders, android.R.layout.simple_spinner_item);
        arrAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(arrAdp);
        genderSpinner.setOnItemSelectedListener(this);

        // Getting the text from the fields
        nameField = (EditText) findViewById(R.id.UsernameTextView2);
        ageField = (EditText) findViewById(R.id.AgeTextView2);
        heightField = (EditText) findViewById(R.id.HeightTextView2);
        weightField = (EditText) findViewById(R.id.WeightTextView2);
        updateUserButton = (Button) findViewById(R.id.updateUserButton);


        sharedPreferences = getSharedPreferences("userInformation",MODE_PRIVATE);

        updateUserButton.setOnClickListener(v -> {
            WorkoutwarriorRepo repo = new WorkoutwarriorRepo();
            ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;
            String userId = sharedPreferences.getString("userId","");

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

            repo.updateUser(srv,updateUserHandler,userId,nameField.getText().toString(),Integer.parseInt(ageField.getText().toString()),
                    Double.parseDouble(weightField.getText().toString()),Double.parseDouble(heightField.getText().toString()),selectedGender);



        });

    }

    @Override
    protected void onResume() {
        super.onResume();



        WorkoutwarriorRepo repo = new WorkoutwarriorRepo();
        ExecutorService srv = ((WorkoutWarriorApplication)getApplication()).srv;
        String userId = sharedPreferences.getString("userId","");
        repo.getUserInfo(srv,userInfoHandler,userId);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedGender = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}