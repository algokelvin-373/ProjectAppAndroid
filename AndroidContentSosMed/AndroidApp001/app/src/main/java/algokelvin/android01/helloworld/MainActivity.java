
package algokelvin.android01.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt = findViewById(R.id.txt_hello_world);
        String msg = "Hello World, AlgoKelvin \n" +
                "I'm learning Android Developer Beginner\n" +
                "Focus on Finding Solutions";
        txt.setText(msg);

    }
}