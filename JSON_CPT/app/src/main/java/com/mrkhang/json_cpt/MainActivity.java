package com.mrkhang.json_cpt;

import android.content.Intent;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String urlGet = "http://113.191.251.104:8080/gpsmsg";

    ListView lvtoado;
    ArrayList<ToaDo> arraytoado;
    ToaDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvtoado = (ListView) findViewById(R.id.ListviewToaDo);
        arraytoado = new ArrayList<>();

        adapter = new ToaDoAdapter(this,R.layout.hien_thi_dong,arraytoado);
        lvtoado.setAdapter(adapter);


        GetData(urlGet);
    }
    private void GetData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                        for(int i =0; i <response.length();i++){
                            try {
                                JSONObject object=response.getJSONObject(i);
                                arraytoado.add(new ToaDo(
                                        object.getString("Latitude"),
                                        object.getString("Longitude"),
                                        object.getString("Altitude")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_gps,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuadd){
            startActivity(new Intent(MainActivity.this, AddGPSActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
