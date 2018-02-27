package com.example.rafael.popularmovies.Utilities;

import android.content.Context;

import com.example.rafael.popularmovies.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Rafael on 20/02/2018.
 */

public class Load {

    public static String loadApiKey(Context context) {

        //TODO add your API here

        // return "Your Api here"


        InputStream inputStream = context.getResources().openRawResource(R.raw.apikey);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line = null;
        try {
            if ((line = reader.readLine()) != null) {
                return line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}