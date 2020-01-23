package com.example.adamos_logistic;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String mes;

    Date date = new Date();

    List<Messages> message = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        ImageButton send = (ImageButton) findViewById(R.id.Send);
        EditText yourMessage = (EditText) findViewById(R.id.your_message);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.message_view);

        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, message);
        // устанавливаем для списка адаптер


        DataAdapter2 adapter2 = new DataAdapter2(this, message);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mes = yourMessage.getText().toString() + "\n";
                setInitialData();
                //if (yourMessage.getText().toString().equals("a")) {
                    recyclerView.setAdapter(adapter);
                //}
                //else recyclerView.setAdapter(adapter2);
                yourMessage.setText("");
            }
        });

    }

    private void setInitialData(){
        message.add(new Messages(mes + "   " + date.toString()));
    }
}