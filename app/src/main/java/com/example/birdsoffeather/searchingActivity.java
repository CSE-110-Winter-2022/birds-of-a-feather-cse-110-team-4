package com.example.birdsoffeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class searchingActivity extends AppCompatActivity {

    private static final String TAG = "Lab5-Nearby";
    private MessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


    }

    public void searchonClick(View view) {
        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.d(TAG, "found message: " + new String(message.getContent()));
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.d(TAG, "Lost messages: " + new String(message.getContent()));
            }

        };
        this.messageListener = new FakedMessageListener(realListener, "Hello world");

    }

    public void backonClick(View view) {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }

    public void goToMockOnClick(View view) {
        Intent intent = new Intent(this, MockCSVActivity.class);
        startActivity(intent);
    }
}