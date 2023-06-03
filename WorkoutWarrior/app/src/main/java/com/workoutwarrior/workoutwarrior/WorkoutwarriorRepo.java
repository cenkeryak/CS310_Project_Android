package com.workoutwarrior.workoutwarrior;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
}
