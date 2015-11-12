package com.app.squad.scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.support.v4.app.ActivityCompat.startActivity;

public class ChangePasswordActivity  extends AsyncTask<String, Void, String[]>  {

    private Context context;


    String userName;
    String newSalt;
    String hashWord;
    String echo;

    public ChangePasswordActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){

    }


    @Override
    protected String[] doInBackground(String... arg0) {

        try{
            this.userName = (String)arg0[0];
            this.newSalt = (String)arg0[1];
            this.hashWord = (String)arg0[2];
            String link="http://192.168.1.126/ChangePassword.php";  //This is the IP/Domain name of the server with the PHP
            String data  = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
            data  += "&" +URLEncoder.encode("newSalt", "UTF-8") + "=" + URLEncoder.encode(newSalt, "UTF-8");
            data  += "&" +URLEncoder.encode("hashWord", "UTF-8") + "=" + URLEncoder.encode(hashWord, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            //  This reads the data coming from the PHP and puts it into a single string
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line.toString());
            }
            this.echo = sb.toString();

            // This splits the string into an array based on delimiter '!!!' (PHP handles that part)
            String[] result = echo.split("!!!");
            return result;

        }
        catch(Exception e){
            return new String[0];
        }
    }

    @Override  // This method occurs after data from the PHP has been returned
    protected void onPostExecute(String[] result){
        Log.i("result", result[0]);

    }






}