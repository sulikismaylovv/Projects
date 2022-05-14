package be.kuleuven.worddle_interface;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class StatisticsFinish extends AppCompatActivity {


       private Button home;


       private TextView winsText;
    private TextView losesText;
    private TextView currentText;
    private TextView maxText;

        private int wins;
        private int losses;
        private int maxStreak;
        private int currentStreak;
        private RequestQueue requestQueue;
        String requestURLForStatistics = "https://studev.groept.be/api/a21pt202/setStatistics";
        String newUrl;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_statistics_finish);


            home = (Button) findViewById(R.id.buttonHome);
            winsText = (TextView)findViewById(R.id.textWinScore) ;
            losesText = (TextView)findViewById(R.id.textLoseScore) ;
            currentText = (TextView)findViewById(R.id.textCurrentScore) ;
            maxText = (TextView)findViewById(R.id.textMaxScore) ;


            Bundle extras = getIntent().getExtras();
            wins = extras.getInt("WINS");
            losses = extras.getInt("LOSSES");
            currentStreak = extras.getInt("CURRENTSTREAK");
            maxStreak = extras.getInt("MAXSTREAK");


            winsText.setText(Integer.toString(wins));
            losesText.setText(Integer.toString(losses));
            currentText.setText(Integer.toString(currentStreak));
            maxText.setText(Integer.toString(maxStreak));



            newUrl = requestURLForStatistics + "/"+ wins +"/"+ losses+"/" + currentStreak +"/"+maxStreak+"/";


            requestQueue = Volley.newRequestQueue(this);

            JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, newUrl, null,

                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {

                                System.out.println("VALUES SET");

                            }


                            catch (Exception e) {


                                // throw new RuntimeException(e);

                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //txtResponse.setText(error.getLocalizedMessage());
                        }
                    });

            requestQueue.add(submitRequest);


        }

        //We will use this method to go back to MainActivity after we press X button on the upper_right side of
        //textView of Statistics
      /*  public void onButtonX(View caller){
            finish();
        }*/

        public void onButtonHome(View caller){
            super.onBackPressed();
            startActivity(new Intent(StatisticsFinish.this, activity_start_page.class));
            finish();
        }

    }