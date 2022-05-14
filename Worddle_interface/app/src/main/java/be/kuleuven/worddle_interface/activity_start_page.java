package be.kuleuven.worddle_interface;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import java.util.Random;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class activity_start_page extends AppCompatActivity {
    private RequestQueue requestQueue;
    private int random_number;

    ///delete later txtResponse
    private TextView txtResponse;


    private String random_fiveLetterWord;
    private Button button_start;
    String  requestURL = "https://studev.groept.be/api/a21pt202/getWords";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        requestQueue = Volley.newRequestQueue(this);

        Random rand = new Random();
        int upperbound = 5756;
        random_number = rand.nextInt(upperbound);
        button_start = (Button) findViewById(R.id.buttonStart);
        button_start.setOnClickListener(this::Start_button_clicked);
        //txtResponse = (TextView) findViewById(R.id.textStart);

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            String five_letter_word_local = "";
                            JSONObject person = (JSONObject) response.get(random_number);
                            five_letter_word_local += person.getString("Words");
                            random_fiveLetterWord = five_letter_word_local;
                            System.out.println(random_fiveLetterWord);
                            System.out.println(five_letter_word_local);
                            //txtResponse.setText(random_fiveLetterWord);


                        }


                        catch (JSONException e) {
                            throw new RuntimeException(e);

                        }
                        //   txtResponse.setText(random_fiveLetterWord);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Start_button_clicked(View caller)
    {
        Intent intent = new Intent(this, GamePage.class);
        intent.putExtra("random-word",random_fiveLetterWord);
        startActivity(intent);
    }

}
