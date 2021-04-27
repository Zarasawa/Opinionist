package com.example.opinionist;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class NetThread extends Thread {
        TextView tv; // main window's textview
        public void saveTV(TextView tv)
        {
            this.tv=tv;
        }

        PrintWriter pw; // main output channel

        public void run()
        {
            Socket sock=new Socket();
            int timeout=4000;
            try {
                //connect to server
                sock.connect(new InetSocketAddress("zarasawa.org", 1812), timeout); // allow timeout
                //write message out to server
                pw = new PrintWriter(sock.getOutputStream());
                pw.println(tv.getText());
                pw.flush();

            } catch (Exception e) {
                tv.setText("Connection error:" + e);
                Log.e("NetThread","Can't connect: " + e);
            }
        }
    }

