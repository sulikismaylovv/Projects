package WordleView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import Notifier.WordNotifier;
import be.kuleuven.worddle_interface.R;


@RequiresApi(api = Build.VERSION_CODES.O)
public class Implementations extends AppCompatActivity implements WordNotifier {
    private final Timer timer = new Timer();
    private final Duration duration =Duration.ofSeconds(3,1);
    private final Duration t =Duration.ofSeconds(3,1);
    private TextView hiddenText;
    private String message;


    public Implementations(){ }
    public Implementations(TextView hiddenText){
        this.hiddenText = hiddenText;
    }




    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public String notifyNotEnoughLetters()
    {
        // Pop-up saying not enough letters
        message = "NOT ENOUGH LETTERS";
        return message;

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    public String notifyNotInTheWordList() {
        // pop up saying not in the wordList
        message = "NOT IN THE WORD LIST";
        return message;
    }

    @Override
    public String notifyGameWon() {
        // Text saying GameWon
        //hiddenText.setVisibility(View.VISIBLE);
        message = "GAME WON";
        return message;
    }

    @Override
    public String notiftGameLost() {
        //Text saying game lost after 5 try
        //hiddenText.setVisibility(View.VISIBLE);
        message = "GAME LOST";
        return message;
    }

}
