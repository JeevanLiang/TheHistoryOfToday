package com.example.administrator.thehistoryoftoday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int month;
    int day;
    BottomNavigationBar bottomNavigationBar;

    private List<Business> BusinessList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.business, "Event"))
                .addItem(new BottomNavigationItem(R.drawable.all, "Collection"))
                .initialise();


        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        GetWithHttp();

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        BusinessAdapter adapter=new BusinessAdapter(BusinessList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.update);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                if(month == calendar.get(Calendar.MONTH)+1 &&day == calendar.get(Calendar.DAY_OF_MONTH)){
                    Toast.makeText(MainActivity.this,"历史事件已是Today！",Toast.LENGTH_LONG).show();
                }
                else {
                    month = calendar.get(Calendar.MONTH)+1;
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    GetWithHttp();

                }
            }
        });

    }



    public void GetWithHttp() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://api.juheapi.com/japi/toh?key=26df491d7816cdb37f526af677dd1210&v=1.0&month="+month+"&day="+day);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String responseStr = response.toString();
                    parseJsonData(responseStr);

                    Log.v("DATA",responseStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void parseJsonData(String json){
        String text="";

        Log.v("TEXT1","work1");
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject3 = (JSONObject) jsonArray.get(i);

                Business newBusiness=new Business("      "+jsonObject3.getString("des"),jsonObject3.getString("title"));
                BusinessList.add(newBusiness);


                text=text+jsonObject3.getString("title")+"\n";
                text=text+jsonObject3.getString("des")+"\n\n\n\n";

            }
            Log.v("JSON",text);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
