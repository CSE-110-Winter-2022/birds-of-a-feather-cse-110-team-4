package com.example.birdsoffeather;

import static com.example.birdsoffeather.model.CSVReader.ReadCSV;
import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsoffeather.model.FakedMessageListener;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
@RunWith(AndroidJUnit4.class)
public class NearbyMessageTest {

    @Test
    public void testMSGSendAndReceive() {

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                assertEquals("test message", message.getContent());
            }

            @Override
            public void onLost(@NonNull Message message) {
            }

        };
        FakedMessageListener fakedMessageListener = new FakedMessageListener(realListener, "test message");

    }
}
