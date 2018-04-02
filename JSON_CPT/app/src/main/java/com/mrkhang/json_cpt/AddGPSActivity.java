package com.mrkhang.json_cpt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AddGPSActivity extends AppCompatActivity {

    EditText addLat, addLong, addAlti;
    Button btnAd, btnRe;

    String urladd = "http://192.168.100.4:8080/Post_JSON.php";

    GPS_ToaDo gps_toado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gps);
        AnhXa();

        btnAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lattitu = addLat.getText().toString().trim();
                String longtitu = addLong.getText().toString().trim();
                String altitu = addLat.getText().toString().trim();


             //
                new HttpAsyncTask().execute("http://113.191.251.104:8080/addgpsmsg");
             //
            if (lattitu.isEmpty() || longtitu.isEmpty() || altitu.isEmpty()){
                Toast.makeText(AddGPSActivity.this, "Fill all", Toast.LENGTH_SHORT).show();
            }else {
                AddGPS(urladd);
            }
            }
        });

        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public static String POST(String url, GPS_ToaDo toaDo){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("Latitude", toaDo.getLatitude1());
            jsonObject.accumulate("Longitude", toaDo.getLongitude1());
            jsonObject.accumulate("Altitude", toaDo.getAltitude1());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            gps_toado = new GPS_ToaDo();
            gps_toado.setLatitude1(addLat.getText().toString().trim());
            gps_toado.setLongitude1(addLong.getText().toString().trim());
            gps_toado.setAltitude1(addAlti.getText().toString().trim());
            return POST(urls[0],gps_toado);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddGPSActivity.this, MainActivity.class));
        }
    }
    //
    private void AddGPS(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    if (response.trim().equals("Success")){
                        Toast.makeText(AddGPSActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddGPSActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(AddGPSActivity.this, "Error Add", Toast.LENGTH_SHORT).show();
                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddGPSActivity.this, "Error code", Toast.LENGTH_SHORT).show();
                Log.d("AAA","Error code :\n" + error.toString());

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Latitude",addLat.getText().toString().trim());
                params.put("Longitude", addLong.getText().toString().trim());
                params.put("Altitude", addAlti.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private void AnhXa(){
        btnRe   = (Button) findViewById(R.id.btnReset);
        btnAd = (Button) findViewById(R.id.btnAdd);
        addLat = (EditText) findViewById(R.id.addtxtLa);
        addLong = (EditText) findViewById(R.id.addtxtLo);
        addAlti = (EditText) findViewById(R.id.addtxtAl);
    }

}
