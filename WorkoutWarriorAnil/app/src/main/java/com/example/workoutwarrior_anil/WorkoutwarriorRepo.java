package com.example.workoutwarrior_anil;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WorkoutwarriorRepo {


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

}
