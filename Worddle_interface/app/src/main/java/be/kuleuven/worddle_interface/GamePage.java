package be.kuleuven.worddle_interface;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import WordleView.Implementations;
import WordleView.TileImplementations;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GamePage extends AppCompatActivity{

    //you can delete this field "textTest  it was for testing the intent functionality"
    // private TextView textTest;
    private RequestQueue requestQueue;
    private String five_lettered_word;
    private ArrayList<Letter> correct_list = new ArrayList<>();
    String requestURL = "https://studev.groept.be/api/a21pt202/getWords";
    String requestURLForStatistics = "https://studev.groept.be/api/a21pt202/getStatistics";
    // wordTable ArrayList is an arraylist with the every word in our table, we need it for checkWordList method
    ArrayList<String> wordTable = new ArrayList<String>(6000);

    //variables neccesary to run game
    private String letter;
    private final Letter character = new Letter('/');
    private int row = 1;
    private int column = 1;
    private Implementations notifierMessage = new Implementations();
    private final ArrayList<Letter> guessing_list = new ArrayList<>();
    private int cursor = 1;

    // Fields created for parsing to next activity for statistics;
    private int wins;
    private int losses;
    private int maxStreak;
    private int currentStreak;
    private RequestQueue requestQueueForStatistics;



    // Fields for countdown
    private final Timer timer = new Timer();
    private Duration duration = Duration.ofSeconds(5, 1);
    private final Duration t = Duration.ofSeconds(5, 1);

    // Fields for countdown2
    private final Timer timerL = new Timer();
    private Duration durationL =Duration.ofSeconds(5,1);
    private final Duration tL =Duration.ofSeconds(5,1);
    // constructors
    @RequiresApi(api = Build.VERSION_CODES.S)
    Map<ImageView, Integer> positionBox  = new HashMap<>();


    // keyboard keys (buttons)
    private Button mButtonA;
    private Button mButtonB;
    private Button mButtonC;
    private Button mButtonD;
    private Button mButtonE;
    private Button mButtonF;
    private Button mButtonG;
    private Button mButtonH;
    private Button mButtonI;
    private Button mButtonJ;
    private Button mButtonK;
    private Button mButtonL;
    private Button mButtonM;
    private Button mButtonN;
    private Button mButtonO;
    private Button mButtonP;
    private Button mButtonQ;
    private Button mButtonR;
    private Button mButtonS;
    private Button mButtonT;
    private Button mButtonU;
    private Button mButtonV;
    private Button mButtonW;
    private Button mButtonX;
    private Button mButtonY;
    private Button mButtonZ;
    private Button mButtonDelete;
    private Button mButtonEnter;
    private Button ButtonY;

    //word boxes
    private ImageView Box1;
    private ImageView Box2;
    private ImageView Box3;
    private ImageView Box4;
    private ImageView Box5;
    private ImageView Box6;
    private ImageView Box7;
    private ImageView Box8;
    private ImageView Box9;
    private ImageView Box10;
    private ImageView Box11;
    private ImageView Box12;
    private ImageView Box13;
    private ImageView Box14;
    private ImageView Box15;
    private ImageView Box16;
    private ImageView Box17;
    private ImageView Box18;
    private ImageView Box19;
    private ImageView Box20;
    private ImageView Box21;
    private ImageView Box22;
    private ImageView Box23;
    private ImageView Box24;
    private ImageView Box25;
    private ImageView BoxX;
    private int resID;
    private EditText editText;
    private TextView hiddenText;

    // Our communication link to the EditText
    InputConnection inputConnection;

    //sparse Array for (button,letter)
    HashMap<Integer,String> keyValues = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.S)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamepage);
        editText = (EditText) findViewById(R.id.text);
        hiddenText = findViewById(R.id.hidden);
        notifierMessage = new Implementations(hiddenText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event , it sets LetterKey based on the value of keyboard input
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_ENTER){
                        onEnterButton(v);
                        //setLetterKey("enter");
                        return true;
                    }
                    if(keyCode == KeyEvent.KEYCODE_DEL){
                        setLetterKey("delete");
                        return true;
                    }
                    else{
                    char unicodeChar = (char)event.getUnicodeChar();
                    String letterx = String.valueOf(unicodeChar);
                    setLetterKey(letterx);
                    return true;}
                }
                return false;
            }
        });

        mButtonA = findViewById(R.id.button_a);
        mButtonB = findViewById(R.id.button_b);
        mButtonC = findViewById(R.id.button_c);
        mButtonD = findViewById(R.id.button_d);
        mButtonE = findViewById(R.id.button_e);
        mButtonF = findViewById(R.id.button_f);
        mButtonG = findViewById(R.id.button_g);
        mButtonH = findViewById(R.id.button_h);
        mButtonI = findViewById(R.id.button_i);
        mButtonJ = findViewById(R.id.button_j);
        mButtonK = findViewById(R.id.button_k);
        mButtonL = findViewById(R.id.button_l);
        mButtonM = findViewById(R.id.button_m);
        mButtonN = findViewById(R.id.button_n);
        mButtonO = findViewById(R.id.button_o);
        mButtonP = findViewById(R.id.button_p);
        mButtonQ = findViewById(R.id.button_q);
        mButtonR = findViewById(R.id.button_r);
        mButtonS = findViewById(R.id.button_s);
        mButtonT = findViewById(R.id.button_t);
        mButtonU = findViewById(R.id.button_u);
        mButtonV = findViewById(R.id.button_v);
        mButtonW = findViewById(R.id.button_w);
        mButtonX = findViewById(R.id.button_x);
        mButtonY = findViewById(R.id.button_y);
        mButtonZ = findViewById(R.id.button_z);
        mButtonDelete = findViewById(R.id.button_delete);
        mButtonEnter = findViewById(R.id.button_enter);

        //initiaze boxes
        Box1 = (ImageView) findViewById(R.id.Box1);
        Box2 = (ImageView) findViewById(R.id.Box2);
        Box3 = (ImageView) findViewById(R.id.Box3);
        Box4 = (ImageView) findViewById(R.id.Box4);
        Box5 = (ImageView) findViewById(R.id.Box5);
        Box6 = (ImageView) findViewById(R.id.Box6);
        Box7 = (ImageView) findViewById(R.id.Box7);
        Box8 = (ImageView) findViewById(R.id.Box8);
        Box9 = (ImageView) findViewById(R.id.Box9);
        Box10 = (ImageView) findViewById(R.id.Box10);
        Box11 = (ImageView) findViewById(R.id.Box11);
        Box12 = (ImageView) findViewById(R.id.Box12);
        Box13 = (ImageView) findViewById(R.id.Box13);
        Box14 = (ImageView) findViewById(R.id.Box14);
        Box15 = (ImageView) findViewById(R.id.Box15);
        Box16 = (ImageView) findViewById(R.id.Box16);
        Box17 = (ImageView) findViewById(R.id.Box17);
        Box18 = (ImageView) findViewById(R.id.Box18);
        Box19 = (ImageView) findViewById(R.id.Box19);
        Box20 = (ImageView) findViewById(R.id.Box20);
        Box21 = (ImageView) findViewById(R.id.Box21);
        Box22 = (ImageView) findViewById(R.id.Box22);
        Box23 = (ImageView) findViewById(R.id.Box23);
        Box24 = (ImageView) findViewById(R.id.Box24);
        Box25 = (ImageView) findViewById(R.id.Box25);
        resID = R.drawable.background;

        //map Boxes
        positionBox.put(Box1, 11);
        positionBox.put(Box2, 12);
        positionBox.put(Box3, 13);
        positionBox.put(Box4, 14);
        positionBox.put(Box5, 15);
        positionBox.put(Box6, 21);
        positionBox.put(Box7, 22);
        positionBox.put(Box8, 23);
        positionBox.put(Box9, 24);
        positionBox.put(Box10, 25);
        positionBox.put(Box11, 31);
        positionBox.put(Box12, 32);
        positionBox.put(Box13, 33);
        positionBox.put(Box14, 34);
        positionBox.put(Box15, 35);
        positionBox.put(Box16, 41);
        positionBox.put(Box17, 42);
        positionBox.put(Box18, 43);
        positionBox.put(Box19, 44);
        positionBox.put(Box20, 45);
        positionBox.put(Box21, 51);
        positionBox.put(Box22, 52);
        positionBox.put(Box23, 53);
        positionBox.put(Box24, 54);
        positionBox.put(Box25, 55);

        //map buttons-letters
        keyValues.put(R.id.button_a, "a");
        keyValues.put(R.id.button_b, "b");
        keyValues.put(R.id.button_c, "c");
        keyValues.put(R.id.button_d, "d");
        keyValues.put(R.id.button_e, "e");
        keyValues.put(R.id.button_f, "f");
        keyValues.put(R.id.button_g, "g");
        keyValues.put(R.id.button_h, "h");
        keyValues.put(R.id.button_i, "i");
        keyValues.put(R.id.button_j, "j");
        keyValues.put(R.id.button_k, "k");
        keyValues.put(R.id.button_l, "l");
        keyValues.put(R.id.button_m, "m");
        keyValues.put(R.id.button_n, "n");
        keyValues.put(R.id.button_o, "o");
        keyValues.put(R.id.button_p, "p");
        keyValues.put(R.id.button_q, "q");
        keyValues.put(R.id.button_r, "r");
        keyValues.put(R.id.button_s, "s");
        keyValues.put(R.id.button_t, "t");
        keyValues.put(R.id.button_u, "u");
        keyValues.put(R.id.button_v, "v");
        keyValues.put(R.id.button_w, "w");
        keyValues.put(R.id.button_x, "x");
        keyValues.put(R.id.button_y, "y");
        keyValues.put(R.id.button_z, "z");
        keyValues.put(R.id.button_enter, "enter");
        keyValues.put(R.id.button_delete, "delete");

        // set button click listeners
        mButtonA.setOnClickListener(this::onClick);
        mButtonB.setOnClickListener(this::onClick);
        mButtonC.setOnClickListener(this::onClick);
        mButtonD.setOnClickListener(this::onClick);
        mButtonE.setOnClickListener(this::onClick);
        mButtonF.setOnClickListener(this::onClick);
        mButtonG.setOnClickListener(this::onClick);
        mButtonH.setOnClickListener(this::onClick);
        mButtonI.setOnClickListener(this::onClick);
        mButtonJ.setOnClickListener(this::onClick);
        mButtonK.setOnClickListener(this::onClick);
        mButtonL.setOnClickListener(this::onClick);
        mButtonM.setOnClickListener(this::onClick);
        mButtonN.setOnClickListener(this::onClick);
        mButtonO.setOnClickListener(this::onClick);
        mButtonP.setOnClickListener(this::onClick);
        mButtonQ.setOnClickListener(this::onClick);
        mButtonR.setOnClickListener(this::onClick);
        mButtonS.setOnClickListener(this::onClick);
        mButtonT.setOnClickListener(this::onClick);
        mButtonU.setOnClickListener(this::onClick);
        mButtonV.setOnClickListener(this::onClick);
        mButtonW.setOnClickListener(this::onClick);
        mButtonX.setOnClickListener(this::onClick);
        mButtonY.setOnClickListener(this::onClick);
        mButtonZ.setOnClickListener(this::onClick);
        mButtonDelete.setOnClickListener(this::onClick);
        mButtonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEnterButton(view);
            }
        });

        Bundle extras = getIntent().getExtras();
        five_lettered_word = extras.getString("random-word");

        for (int i = 0; i < 5; i++) {
            correct_list.add(new Letter(five_lettered_word.charAt(i)));
        }


        //DB CONNECTION FOR ARRAYLIST OF WORDS
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // try {
                        for (int a = 0; a < response.length(); a++) {

                            String tempFiveLetter = "";
                            try {

                                JSONObject element = (JSONObject) response.get(a);
                                tempFiveLetter += element.getString("Words");
                                wordTable.add(tempFiveLetter);


                            } catch (JSONException e) {
                                throw new RuntimeException(e);


                            }

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });
        requestQueue.add(submitRequest);

        //DB CONNECTION FOR Statistics
        requestQueueForStatistics = Volley.newRequestQueue(this);
        JsonArrayRequest submitRequestForStatistics = new JsonArrayRequest(Request.Method.GET, requestURLForStatistics, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            ///String five_letter_word_local = "";
                            JSONObject person = (JSONObject) response.get(response.length()-1);
                            wins= person.getInt("Wins");
                            losses = person.getInt("Loses");
                            currentStreak = person.getInt("CurrentStreak");
                            maxStreak = person.getInt("MAXStreak");




                            System.out.println("Wins = "+wins);
                            System.out.println("Losses = "+losses);
                            System.out.println("Current Streak = "+currentStreak);
                            System.out.println("Max Streak = "+maxStreak);
                            //txtResponse.setText(random_fiveLetterWord);


                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }}},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });

        requestQueueForStatistics.add(submitRequestForStatistics);

    }

    private int concatinate() {
        int c = 0;
        String s1 = Integer.toString(row);
        String s2 = Integer.toString(column);
        String s = s1 + s2;
        c = Integer.parseInt(s);
        return c;

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint("ResourceAsColor")
    public void onClick(View v) {
        setBox(v);
    }

    //sets letter based on id
    public String findId(View v) {
        int buttonInt = v.getId();
        letter = keyValues.get(buttonInt);
        return letter;
    }


    //sets BOX if game-keyboard is used
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void setBox(View v) {
        //animation
        if (column < 6){
            ImageView BoxY = null;
            Letter char1 = new Letter('/');
            findId(v);
            getBackground(letter);
            BoxY = getCurrentBoxN();
            char1.setLetter(letter.charAt(0));
            if (v.getId() == R.id.button_delete) {
                BoxY.setImageResource(resID);
                column -=1;
                if(column == 0){column = 1;}
                if(guessing_list.size()!=0){
                    guessing_list.remove(guessing_list.size()-1);}}
            else {
                BoxY.setImageResource(resID);
                if(letter.equals("enter")){}else{ column += 1;}
                guessing_list.add(char1);
            }}
        if(column == 6 && v.getId() == R.id.button_delete){
            ImageView BoxY = null;
            Letter char1 = new Letter('/');
            findId(v);
            getBackground(letter);
            BoxY = getCurrentBoxN();
            char1.setLetter(letter.charAt(0));
            BoxY.setImageResource(resID);
            column -= 1;
            if(guessing_list.size()!=0){
                guessing_list.remove(guessing_list.size()-1);}
        }
        else{}
    }

    //gets current letter
    public String getLetter() {
        return letter;
    }



    //sets letter if laptop keys are used
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void setLetterKey(String letterKey) {
        if (column < 6){
            //AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.flipping);
            Letter char1 = new Letter('/');
            letter = letterKey;
            ImageView BoxY = null;
            getBackground(letter);
            BoxY = getCurrentBoxN();
            char1.setLetter(letter.charAt(0));
            //set.setTarget(BoxY);
            //set.start();
            if (letter.equals("delete")) {
                BoxY.setImageResource(resID);
                column -= 1;
                if(column == 0){column = 1;}
                if(guessing_list.size()!=0){
                    guessing_list.remove(guessing_list.size()-1);}}
            else {
                BoxY.setImageResource(resID);
                if(letter.equals("enter")){}else{ column += 1;}
                guessing_list.add(char1);
            }}
        if(column == 6 && letterKey.equals("delete")){
            Letter char1 = new Letter('/');
            letter = letterKey;
            ImageView BoxY = null;
            getBackground(letter);
            BoxY = getCurrentBoxN();
            char1.setLetter(letter.charAt(0));
            BoxY.setImageResource(resID);
            column -= 1;
            if(guessing_list.size()!=0){
                guessing_list.remove(guessing_list.size()-1);}
        }
        else{}
    }

    //gets background for specific letter
    public void getBackground(String letter) {
        if (getLetter() != null) {
            try {

                if (letter.equals("a")) {
                    resID = R.drawable.letter_a;
                    System.out.println("letter a set");
                }
                if (letter.equals("b")) {
                    resID = R.drawable.letter_b;
                    System.out.println("letter b set");
                }
                if (letter.equals("c")) {
                    resID = R.drawable.letter_c;
                    System.out.println("letter c set");
                }
                if (letter.equals("d")) {
                    resID = R.drawable.letter_d;
                    System.out.println("letter d set");
                }
                if (letter.equals("e")) {
                    resID = R.drawable.letter_e;
                    System.out.println("letter e set");
                }
                if (letter.equals("f")) {
                    resID = R.drawable.letter_f;
                    System.out.println("letter f set");
                }
                if (letter.equals("g")) {
                    resID = R.drawable.letter_g;
                    System.out.println("letter g set");
                }
                if (letter.equals("h")) {
                    resID = R.drawable.letter_h;
                    System.out.println("letter h set");
                }
                if (letter.equals("i")) {
                    resID = R.drawable.letter_i;
                    System.out.println("letter i set");
                }
                if (letter.equals("j")) {
                    resID = R.drawable.letter_j;
                    System.out.println("letter j set");
                }
                if (letter.equals("k")) {
                    resID = R.drawable.letter_k;
                    System.out.println("letter k set");
                }
                if (letter.equals("l")) {
                    resID = R.drawable.letter_l;
                    System.out.println("letter l set");
                }
                if (letter.equals("m")) {
                    resID = R.drawable.letter_m;
                    System.out.println("letter m set");
                }
                if (letter.equals("n")) {
                    resID = R.drawable.letter_n;
                    System.out.println("letter n set");
                }
                if (letter.equals("o")) {
                    resID = R.drawable.letter_o;
                    System.out.println("letter o set");
                }
                if (letter.equals("p")) {
                    resID = R.drawable.letter_p;
                    System.out.println("letter p set");
                }
                if (letter.equals("q")) {
                    resID = R.drawable.letter_q;
                    System.out.println("letter q set");
                }
                if (letter.equals("r")) {
                    resID = R.drawable.letter_r;
                    System.out.println("letter r set");
                }
                if (letter.equals("s")) {
                    resID = R.drawable.letter_s;
                    System.out.println("letter s set");
                }
                if (letter.equals("t")) {
                    resID = R.drawable.letter_t;
                    System.out.println("letter t set");
                }
                if (letter.equals("u")) {
                    resID = R.drawable.letter_u;
                    System.out.println("letter u set");
                }
                if (letter.equals("v")) {
                    resID = R.drawable.letter_v;
                    System.out.println("letter v set");
                }
                if (letter.equals("w")) {
                    resID = R.drawable.letter_w;
                    System.out.println("letter w set");
                }
                if (letter.equals("x")) {
                    resID = R.drawable.letter_x;
                    System.out.println("letter x set");
                }
                if (letter.equals("y")) {
                    resID = R.drawable.letter_y;
                    System.out.println("letter y set");
                }
                if (letter.equals("z")) {
                    resID = R.drawable.letter_z;
                    System.out.println("letter z set");
                }
                if (letter.equals("")) {
                    resID = R.drawable.background;
                    System.out.println("letter null set");
                }
                if (letter.equals("delete")) {
                    resID = R.drawable.background;
                    System.out.println("letter delete set");
                }
                if (letter.equals("enter")) {
                    resID = R.drawable.background;
                    System.out.println("letter enter set");
                }
            } catch (NullPointerException e) {
                Box1.setImageResource(R.drawable.background);
                System.out.println("letter null set");
            }
        } else {
            return;
        }
    }


    //gets current used Box
    @RequiresApi(api = Build.VERSION_CODES.S)
    public ImageView getCurrentBoxN() {
        int value ;
        if (letter.equals("delete") ){
            if(column ==6){column = 5;}}
        if (column == 6 && !letter.equals("delete")){
            row +=1;
            column = 1;}
        value = concatinate();
        BoxX = getKeys(positionBox, value);
        return BoxX;
    }

    //gets BOX from the current position
    private static ImageView getKeys(Map<ImageView, Integer> map, Integer value) {
        return map.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findAny().get();
    }

    private static Integer getButtons(HashMap<Integer, String> map, String value) {
        return map.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findAny().get();
    }



    @RequiresApi(api = Build.VERSION_CODES.S)
    public void onEnterButton(View caller) {
        cursor = column;
        if (cursor != 6) {
            hiddenText.setText(notifierMessage.notifyNotEnoughLetters());
            hiddenText.setVisibility(View.VISIBLE);
            System.out.println("Not enough letters");
            countDown();
        }
        else {
            if (row == 5) {
                // this if method deals with the last attempt;
                if (equals(guessing_list, correct_list)) {
                    wins++;
                    currentStreak++;
                    compareStreaks();
                    hiddenText.setText(notifierMessage.notifyGameWon());
                    hiddenText.setVisibility(View.VISIBLE);
                    System.out.println("game won");
                    setBackgroundEnter();
                    countDown2();

                } else {
                    losses++;
                    currentStreak = 0;
                    hiddenText.setText(notifierMessage.notiftGameLost());
                    hiddenText.setVisibility(View.VISIBLE);
                    System.out.println("game lost");
                    setBackgroundEnter();

                    countDown2();
                }
            }
            else {
                //this else method deals with the case you have written five lettered word and checks DB
                if (equals(guessing_list, correct_list)) {
                    //if you have found correctly

                    wins++;
                    currentStreak++;
                    compareStreaks();
                    hiddenText.setText(notifierMessage.notifyGameWon());
                    hiddenText.setVisibility(View.VISIBLE);
                    System.out.println("game won");
                    setBackgroundEnter();
                    countDown2();
                } else {
                    if (!checkWordList(guessing_list, caller)) {
                        //if you have found wrong but in the word list
                        //notifierMessageForTile.notifyTileColor();
                        System.out.println("change colors");
                        setBackgroundEnter();
                        row +=1;
                        column = 1;
                    } else {
                        // if you have written something gibberish that is not in the word list
                        hiddenText.setText(notifierMessage.notifyNotInTheWordList());
                        hiddenText.setVisibility(View.VISIBLE);
                        System.out.println("not in the word list");
                        countDown();
                    }

                }
            }
        }
        guessing_list.clear();
        System.out.print("row =" + row + "column = " + column);
    }


    public boolean equals(ArrayList<Letter> y, ArrayList<Letter> x) {
        int z = 0;
        for(int j = 0; j<5; j++){
            for(int k = 0;k<5;k++){
                if (x.get(j).getLetter() == y.get(k).getLetter()) {
                    if(j==k){
                        guessing_list.get(j).setWrongPlacement(false);}
                    else{guessing_list.get(k).setWrongPlacement(true);}
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (x.get(i).getLetter() == y.get(i).getLetter()) {
                guessing_list.get(i).setUsed(true);
                guessing_list.get(i).setPresent(true);
            } else {
                guessing_list.get(i).setUsed(true);
                guessing_list.get(i).setPresent(false);
                z++;
            }
        }
        return z == 0;
    }

    public boolean equalsString(String x, String y) {
        int z = 0;
        for (int i = 0; i < 5; i++) {
            if (x.charAt(i) == y.charAt(i)) {
            } else {
                z++;
            }
        }
        return z == 0;
    }

    public boolean checkWordList(ArrayList<Letter> guessList, View v) {
        int z = 0;
        String x = "";
        for (int i = 0; i < 5; i++) {
            x += guessList.get(i).getLetter();
        }
        for (int a = 0; a < wordTable.size(); a++) {
            if (equalsString(x, wordTable.get(a))) {
                z++;
            } else {
            }
        }
        return z == 0;

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void setBackgroundEnter(){
        ImageView BoxY;
        //AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.flipping);
        for (int i = 0; i < 5; i++){
            column = i+1;
            ButtonY = findViewById(getButtons(keyValues,guessing_list.get(i).getLetterS()));
            BoxY = getCurrentBoxN();
            //set.setTarget(BoxY);
            //set.start();
            BoxY.setBackground(getResources().getDrawable(R.drawable.background_black));
            ButtonY.setBackgroundTintList(getResources().getColorStateList(R.color.black));
            if (guessing_list.get(i).getIsPresent() && !guessing_list.get(i).getWrongPlacement()){
                BoxY.setBackground(getResources().getDrawable(R.drawable.green_back));
                ButtonY.setBackgroundTintList(getResources().getColorStateList(R.color.green));
            }

            if (guessing_list.get(i).getWrongPlacement()){
                BoxY.setBackground(getResources().getDrawable(R.drawable.yellow_back));
                ButtonY.setBackgroundTintList(getResources().getColorStateList(R.color.yellow));}
            if(i==4){
                column +=1;
            }
        }

    }

    public void countDown() {
        TimerTask task = new TimerTask() {
            int i = (int) t.toMillis() / 1000;

            public void run() {
                duration = duration.minusSeconds(1);
                i--;
                if(i<0){hiddenText.setVisibility(View.INVISIBLE);}
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 1000);
    }


    public void countDown2(){
        TimerTask task = new TimerTask() {
            int i = (int) tL.toMillis()/1000;
            public void run() {
                durationL=durationL.minusSeconds(1);
                i--;
                if (i==0) {
                    newActivity();

                }

            }
        };
        Timer timerL = new Timer();
        // duration = Duration.ofSeconds(5);
        //t=Duration.ofSeconds(5);
        timerL.scheduleAtFixedRate(task, 0, 1000);
    }

    public void compareStreaks() {
        if (maxStreak <= currentStreak) {
            maxStreak = currentStreak;
        } else {
        }
    }

    public void newActivity(){
        Intent intent = new Intent(this, StatisticsFinish.class);
        intent.putExtra("WINS",wins);
        intent.putExtra("LOSSES",losses);
        intent.putExtra("CURRENTSTREAK",currentStreak);
        intent.putExtra("MAXSTREAK",maxStreak);

        startActivity(intent);
    }




}
