package com.ronald.apptractoleo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionUser extends AppCompatActivity {

    Button btnvActivityCreateProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_user);

        btnvActivityCreateProd = findViewById(R.id.btnActivityCreateProd);

        btnvActivityCreateProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(SelectOptionUser.this, CreateNewProduct.class);
                startActivity(a);
            }
        });

    }
}