package com.workoutwarrior.workoutwarrior;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WorkoutwarriorRepo {

    public WorkoutwarriorRepo() {
    }

    public void createUser(ExecutorService srv, Handler idHandler, String name, int age, double weight, double height, String gender){
        srv.execute(()->{
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:8080/controller/createuser");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputObject = new JSONObject();
                outputObject.put("name",name);
                outputObject.put("age",age);
                outputObject.put("weight",weight);
                outputObject.put("height",height);
                outputObject.put("gender",gender);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(outputObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buff = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null){
                    buff.append(line);
                }

                JSONObject inputObject = new JSONObject(buff.toString());
                String userId = inputObject.getString("userId");

                Message msg = new Message();
                msg.obj = userId;

                idHandler.sendMessage(msg);

                Log.i("DEV","User successfully created with ID: " + userId);


                conn.disconnect();



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (ProtocolException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }
        });
    }

    public void getUserInfo(ExecutorService srv, Handler userInfoHandler, String id){
        srv.execute(()->{
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:8080/controller/getuser");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputObject = new JSONObject();
                outputObject.put("userId",id);


                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(outputObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buff = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null){
                    buff.append(line);
                }

                // parsing JSON object into UserInfo Class
                JSONObject inputObject = new JSONObject(buff.toString());
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(inputObject.getString("userId"));
                userInfo.setName(inputObject.getString("name"));
                userInfo.setGender(inputObject.getString("gender"));
                userInfo.setWeightCategory(inputObject.getString("weightCategory"));
                userInfo.setAge(inputObject.getInt("age"));
                userInfo.setHeight(inputObject.getDouble("height"));
                userInfo.setWeight(inputObject.getDouble("weight"));
                userInfo.setBmi(inputObject.getDouble("bmi"));

                Message msg = new Message();
                msg.obj = userInfo;

                userInfoHandler.sendMessage(msg);

                if (userInfo.getUserId() != ""){
                    Log.i("DEV","User succesfully taken from server with ID: " + userInfo.getUserId());
                }
                else {
                    Log.i("DEV","NULL info is taken from server ");
                }


                conn.disconnect();



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (ProtocolException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }
        });
    }

    public void updateUser(ExecutorService srv, Handler updateHandler,String userId ,String name, int age, double weight, double height, String gender){
        srv.execute(()->{
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:8080/controller/updateuser");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");

                JSONObject outputObject = new JSONObject();
                outputObject.put("userId",userId);
                outputObject.put("name",name);
                outputObject.put("age",age);
                outputObject.put("weight",weight);
                outputObject.put("height",height);
                outputObject.put("gender",gender);

                BufferedOutputStream writer = new BufferedOutputStream(conn.getOutputStream());
                writer.write(outputObject.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buff = new StringBuilder();
                String line = "";
                while((line = reader.readLine()) != null){
                    buff.append(line);
                }

                JSONObject inputObject = new JSONObject(buff.toString());
                String userIdReturn = inputObject.getString("userId");



                if(userIdReturn != "" && userIdReturn != null){
                    Log.i("DEV","User successfully updated with ID: " + userId);

                    Message msg = new Message();
                    msg.obj = userIdReturn;
                    updateHandler.sendMessage(msg);
                } else{
                    // Print problem
                    Log.e("DEV","Update failed");
                }




                conn.disconnect();



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (ProtocolException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }
        });
    }
    public void getExercises(ExecutorService srv, Handler handler,Exercise exercise){

        srv.submit(()->{

            try {

                List<Exercise> exerciseList = new ArrayList<>();

                URL url= new URL("http://10.0.2.2:8080/exercises/getExercise");
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                conn.setRequestProperty("Content-type","application/json");

                BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));


                JSONObject exerciseJson = new JSONObject();
                if(!exercise.getName().equals("")){
                    exerciseJson.put("name", exercise.getName());
                }
                if(!exercise.getType().equals("")){
                    exerciseJson.put("type", exercise.getType());
                }if(!exercise.getDifficulty().equals("")){
                    exerciseJson.put("difficulty", exercise.getDifficulty());
                }
                if(!exercise.getMuscle().equals("")){
                    exerciseJson.put("muscle", exercise.getMuscle());
                }

                writer.write(exerciseJson.toString());
                writer.flush();

                BufferedReader reader= new BufferedReader(new InputStreamReader(conn.getInputStream()));


                StringBuilder builder=new StringBuilder();
                String line="";
                while((line= reader.readLine())!=null){
                    builder.append(line);
                }

                JSONArray arr= new JSONArray(builder.toString());

                for(int i=0;i<arr.length();i++){
                    JSONObject obj= (JSONObject) arr.get(i);
                    Exercise ex = new Exercise(obj.getString("name"),obj.getString("type")
                            ,obj.getString("muscle"),obj.getString("equipment"),obj.getString("difficulty"),
                            obj.getString("instructions"));
                    exerciseList.add(ex);
                }

                Message msg= new Message();
                msg.obj=exerciseList;


                handler.sendMessage(msg);




            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });




    }

    public void getAllWorkouts(ExecutorService srv, Handler uiHandler, String userId){

        srv.submit(()->{
            try {
                List<Workout> retVal =new ArrayList<>();

                URL url = new URL("http://10.0.2.2:8080/workout/getallworkouts/" + userId);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while((line= reader.readLine())!=null) {
                    stringBuilder.append(line);
                }

                JSONArray arr = new JSONArray(stringBuilder.toString());
                for(int i = 0; i < arr.length(); i++){
                    JSONObject jsonObject = arr.getJSONObject(i);

                    JSONArray exerciseArr = jsonObject.getJSONArray("exercise");
                    List<Workout.Exercise> exerciseList = new ArrayList<>();
                    for(int x = 0; x < exerciseArr.length(); x++)
                    {
                        JSONObject exerciseObject = exerciseArr.getJSONObject(x);
                        Workout.Exercise exercise = new Workout.Exercise(
                                exerciseObject.getString("name"),
                                exerciseObject.getInt("setCount"),
                                exerciseObject.getInt("repCountInASet")
                        );

                        if(exerciseObject.getString("additionalNote") != "null")
                            exercise.setAdditionalNote(exerciseObject.getString("additionalNote"));

                        exerciseList.add(exercise);
                    }

                    Workout workout = new Workout(
                            jsonObject.getString("id"),
                            jsonObject.getString("date"),
                            exerciseList
                    );

                    retVal.add(workout);
                }

                Message msgErr = new Message();
                msgErr.obj = retVal;
                uiHandler.sendMessage(msgErr);

            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV", e.getMessage());
            }

        });

    }
    public void postWorkout(ExecutorService executorService, Handler handler, String userid, List<Workout.Exercise> exercises, Callback callback) {
        executorService.execute(() -> {
            try {
                // Create the JSON object for the request body
                JSONObject requestBody = new JSONObject();
                requestBody.put("userid", userid);

                JSONArray exerciseArray = new JSONArray();
                for (Workout.Exercise exercise : exercises) {
                    JSONObject exerciseObj = new JSONObject();
                    exerciseObj.put("name", exercise.getName());
                    exerciseObj.put("setCount", exercise.getSetCount());
                    exerciseObj.put("repCountInASet", exercise.getRepCountInASet());
                    if(!exercise.getAdditionalNote().equals(""))
                        exerciseObj.put("additionalNote", exercise.getAdditionalNote());
                    exerciseArray.put(exerciseObj);     //"put" for the array, not sure.
                }

                requestBody.put("exercise", exerciseArray);

                // Create the URL object
                URL url = new URL("http://10.0.2.2:8080/workout/createworkout");

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                // Enable output and set the request body
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(requestBody.toString());
                writer.flush();

                // Get the response code
                int responseCode = connection.getResponseCode();

                // Close the connection and writer
                writer.close();
                connection.disconnect();

                // Handle the response
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Request succeeded
                    handler.sendEmptyMessage(0);
                    callback.onSuccess();
                } else {
                    // Request failed
                    Log.e("DEV", "POST request failed with response code: " + responseCode);
                    callback.onError();
                }
            } catch (Exception e) {
                Log.e("DEV", "Error occurred while sending POST request: " + e.getMessage());
                callback.onError();
            }
        });
    }
    public void deleteWorkoutFromBackend(ExecutorService srv, Handler uiHandler, String workoutId) {
        srv.submit(()->{
            URL url = null;
            try {
                url = new URL("http://10.0.2.2:8080/workout/delete/" + workoutId);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("DELETE");

                int responseCode = conn.getResponseCode();

                // Check if the request was successful
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Workout deleted successfully
                    uiHandler.sendEmptyMessage(0);
                } else {
                    // Workout deletion failed
                    uiHandler.sendEmptyMessage(1);
                }

                conn.disconnect();

            } catch (MalformedURLException e) {
                Log.e("DEV", e.getMessage());
                uiHandler.sendEmptyMessage(1);
            } catch (IOException e) {
                Log.e("DEV", e.getMessage());
                uiHandler.sendEmptyMessage(1);
            }
        });
    }


    public interface Callback {
        void onSuccess();
        void onError();
    }
}
