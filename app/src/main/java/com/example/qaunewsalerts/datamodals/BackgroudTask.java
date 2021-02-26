package com.example.qaunewsalerts.datamodals;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.http.HTTP;

public class BackgroudTask extends AsyncTask<String,Void,String> {
    Context context;
    public BackgroudTask(Context context){
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... voids) {
        String url="http://192.168.0.111/qauApi/index.php";
        String method=voids[0];
        if(method.equals("submit")){
            String NewsTitle=voids[1];
            String NewsDescription=voids[2];
            String NewsImage=voids[3];

            try {
                URL uri=new URL(url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) uri.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter= new BufferedWriter(new OutputStreamWriter(outputStream,"utf8"));

                String data= URLEncoder.encode("NewsTitle","utf8")+"="+URLEncoder.encode(NewsTitle,"utf8")+"&"
                        +URLEncoder.encode("NewsDescription","utf8")+"="+ URLEncoder.encode(NewsDescription,"utf8")+"&"
                        +URLEncoder.encode("NewsImage","utf8")+"="+URLEncoder.encode(NewsImage,"utf8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                return "Add Successfully";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
