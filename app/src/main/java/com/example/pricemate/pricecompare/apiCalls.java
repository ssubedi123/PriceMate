package com.example.pricemate.pricecompare;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class apiCalls {
    Context applicationContext;
    int temperature;
    public apiCalls(Context context){
        applicationContext = context;
    }

    public int getTemperature(){
        return this.temperature;
    }

    //contains values required to call ebay api and possibly other apis
    /*public StringRequest searchNameStringRequestForEbay(String nameSearch, String CLIENT_ID, String REDIRECT_URI, String SCOPE, String URL_PREFIX) {

        //following Ebay's API call Documentation

        //for now this is hardcoded but you could store these in resources
        //https://developer.ebay.com/support/kb OAuth Token Flow
        String url = URL_PREFIX + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code" + "&scope=" + SCOPE + "&prompt=login";

        // 1st param => type of method (GET/PUT/POST/PATCH/etc)
        // 2nd param => complete url of the API
        // 3rd param => Response.Listener -> Success procedure
        // 4th param => Response.ErrorListener -> Error procedure
        return new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        try {

                            JSONObject result = new JSONObject(response);
                            int authCode = result.getInt("code");
                            JSONArray resultList = result.getJSONArray("item");
                            Toast.makeText(applicationContext, authCode, Toast.LENGTH_LONG).show();


                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(applicationContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    */

    public StringRequest searchNameStringRequestForOpenWeather(String city, String API_KEY) {


        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID="+ API_KEY+"";

        // 1st param => type of method (GET/PUT/POST/PATCH/etc)
        // 2nd param => complete url of the API
        // 3rd param => Response.Listener -> Success procedure
        // 4th param => Response.ErrorListener -> Error procedure
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    // 3rd param - method onResponse lays the code procedure of success return
                    // SUCCESS
                    @Override
                    public void onResponse(String response) {
                        // try/catch block for returned JSON data
                        // see API's documentation for returned format
                        try {

                            JSONObject result = new JSONObject(response);
                            JSONObject mainObject = result.getJSONObject("main");
                            temperature = mainObject.getInt("temp");
                            System.out.println(temperature);
                            Toast.makeText(applicationContext, temperature + " Kelvin", Toast.LENGTH_LONG).show();



                            // catch for the JSON parsing error
                        } catch (JSONException e) {
                            Toast.makeText(applicationContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } // public void onResponse(String response)
                }, // Response.Listener<String>()
                new Response.ErrorListener() {
                    // 4th param - method onErrorResponse lays the code procedure of error return
                    // ERROR
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // display a simple message on the screen
                        Toast.makeText(applicationContext, "Error: OpenWeather", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
