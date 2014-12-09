package com.around.me;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The type Test utils.
 */
public class TestUtils {

    /**
     * Read from assets.
     *
     * @param fileName the file name
     * @param context the context
     * @return the string
     * @throws IOException the iO exception
     */
    public static String readFromAssets(String fileName, Context context) throws IOException {
        InputStream inputStream = context.getAssets().open(fileName);
        return convertStreamToString(inputStream);
    }

    /**
     * Convert stream to string.
     *
     * @param is the is
     * @return the string
     * @throws IOException the iO exception
     */
    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        is.close();
        return sb.toString();
    }
}
