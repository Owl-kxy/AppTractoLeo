package com.ronald.apptractoleo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateNewProduct extends AppCompatActivity {

    EditText edtvFieldProd, edtvFieldIdProd;
    Button btnvSearchProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_product);

        edtvFieldProd = (EditText) findViewById(R.id.edtFieldProd);
        edtvFieldIdProd = (EditText) findViewById(R.id.edtFieldIdProd);
        btnvSearchProd = (Button) findViewById(R.id.btnSearchProd);

        btnvSearchProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
            }
        });
    }

    public Connection connectionDB()
    {
        Connection Varconnection = null;
        try
        {
            StrictMode.ThreadPolicy Varpolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(Varpolicy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return Varconnection;
    }

    public void AddProduct()
    {
        try{
            PreparedStatement pst = connectionDB().prepareStatement("insert into Prueba values (?,?)");
            pst.setString(1,edtvFieldIdProd.getText().toString());
            pst.setString(2,edtvFieldProd.getText().toString());
            pst.executeUpdate();

            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_SHORT).show();
        }
        catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
