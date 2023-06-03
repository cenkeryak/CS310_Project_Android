package com.example.workoutwarrior;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WorkoutRepository {


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
