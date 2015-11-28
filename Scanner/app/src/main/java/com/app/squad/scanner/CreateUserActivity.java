package com.app.squad.scanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CreateUserActivity  extends AsyncTask<String, Void, String[]>  {

    private Context context;

    String firstName ;
    String lastName;
    String userName;
    String newSalt;
    String hashWord;
    String level;
    String echo;
    String firstTime;

    public CreateUserActivity(Context context) {

        this.context = context;
    }

    protected void onPreExecute(){

    }


    @Override
    protected String[] doInBackground(String... arg0) {

        try{

            this.firstName = (String)arg0[0];
            this.lastName = (String)arg0[1];
            this.userName = (String)arg0[2];
            this.newSalt = (String)arg0[3];
            this.hashWord = (String)arg0[4];
            this.level = (String)arg0[5];

            String link="http://54.69.210.120/CreateUser.php";  //This is the IP/Domain name of the server with the PHP
            String data  = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8");
            data  += "&" +URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8");
            data  += "&" +URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
            data  += "&" +URLEncoder.encode("newSalt", "UTF-8") + "=" + URLEncoder.encode(newSalt, "UTF-8");
            data  += "&" +URLEncoder.encode("hashWord", "UTF-8") + "=" + URLEncoder.encode(hashWord, "UTF-8");
            data  += "&" +URLEncoder.encode("level", "UTF-8") + "=" + URLEncoder.encode(level, "UTF-8");

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
            Log.i("Result:", result[0]);
            return result;

        }
        catch(Exception e){
            return new String[0];
        }
    }

    @Override  // This method occurs after data from the PHP has been returned
    protected void onPostExecute(String[] result){
        if (result[0].toString().matches("success")){

            new AlertDialog.Builder(context)
                    .setTitle("All Set")
                    .setMessage("User Created Successfully")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(context, AdminLanding.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else{
            new AlertDialog.Builder(context)
                    .setTitle("Uh Oh")
                    .setMessage("There was an Error!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }


}