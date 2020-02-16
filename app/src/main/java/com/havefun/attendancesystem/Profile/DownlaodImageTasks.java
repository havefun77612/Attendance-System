package com.havefun.attendancesystem.Profile;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class DownlaodImageTasks extends AsyncTask<URL,Void, Bitmap> {
    // Before the tasks execution
    protected void onPreExecute(){

    }

    // Do the task in background/non UI thread
    protected Bitmap doInBackground(URL...urls){
        URL url = urls[0];
        HttpURLConnection connection = null;

        try{
            // Initialize a new http url connection
            connection = (HttpURLConnection) url.openConnection();

            // Connect the http url connection
            connection.connect();

            // Get the input stream from http url connection
            InputStream inputStream = connection.getInputStream();

                /*
                    BufferedInputStream
                        A BufferedInputStream adds functionality to another input stream-namely,
                        the ability to buffer the input and to support the mark and reset methods.
                */
                /*
                    BufferedInputStream(InputStream in)
                        Creates a BufferedInputStream and saves its argument,
                        the input stream in, for later use.
                */
            // Initialize a new BufferedInputStream from InputStream
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                /*
                    decodeStream
                        Bitmap decodeStream (InputStream is)
                            Decode an input stream into a bitmap. If the input stream is null, or
                            cannot be used to decode a bitmap, the function returns null. The stream's
                            position will be where ever it was after the encoded data was read.

                        Parameters
                            is InputStream : The input stream that holds the raw data
                                              to be decoded into a bitmap.
                        Returns
                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
                */
            // Convert BufferedInputStream to Bitmap object
            Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

            // Return the downloaded bitmap
            return bmp;

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            // Disconnect the http url connection
            connection.disconnect();
        }
        return null;
    }

    // When all async task done
    protected void onPostExecute(Bitmap result){


        if(result!=null){

        }else {

        }
    }


    // Custom method to convert string to url
    protected URL stringToURL(String urlString){
        try{
            URL url = new URL(urlString);
            return url;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }


}