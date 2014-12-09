package com.around.me.autocomplete;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Google places auto complete.
 */
public class GooglePlacesAutoComplete implements IAutoComplete {

    private final String mBaseUrl;
    private String mApiKey;
    private final OkHttpClient mClient;

    /**
     * Instantiates a new Google places auto complete.
     *
     * @param baseUrl the base url
     * @param apiKey  the api key
     */
    public GooglePlacesAutoComplete(String baseUrl, String apiKey) {
        mBaseUrl = baseUrl;
        mApiKey = apiKey;

        mClient = new OkHttpClient();
    }

    /**
     * Search list.
     *
     * @param input the input
     * @return the list
     */
    public List<String> search(String input) {

        Request request = new Request.Builder()
                .url(mBaseUrl + "/maps/api/place/autocomplete/json" + "?" + "key=" + mApiKey + "&" + "input=" + input)
                .build();

        List<String> resultList = null;

        try {
            Response response = mClient.newCall(request).execute();

            if (response.isSuccessful() && response.body().contentLength() > 0) {
                // Create a JSON object hierarchy from the results
                JSONObject jsonObj = new JSONObject(response.body().string());
                JSONArray predictionsJsonArray = jsonObj.getJSONArray("predictions");

                // Extract the Place descriptions from the results
                resultList = new ArrayList<>(predictionsJsonArray.length());
                for (int i = 0; i < predictionsJsonArray.length(); i++) {
                    resultList.add(predictionsJsonArray.getJSONObject(i).getString("description"));
                }
            }
        } catch (IOException | JSONException ignored) {

        }

        return resultList;
    }
}
