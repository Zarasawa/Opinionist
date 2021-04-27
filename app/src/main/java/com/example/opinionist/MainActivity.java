package com.example.opinionist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doInc(View v)
    {
        TextView msg=(TextView) findViewById(R.id.textView);
        msg.setText("jfdshfjfhkaflhhfajlfhakfh");

        NetThread nt=new NetThread();
        nt.saveTV(msg);
        nt.start();

    }
}


