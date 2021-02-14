package com.ronald.apptractoleo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class SearchProductUser extends AppCompatActivity {

    EditText edtvFieldProd;
    TextView txtvId, txtvProduct;
    ImageView imgvProduct;
    Button btnvSearchProd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_user);

       edtvFieldProd = (EditText) findViewById(R.id.edtFieldProd);
       btnvSearchProd = (Button) findViewById(R.id.btnSearchProd);
       txtvId = (TextView)findViewById(R.id.txtId);
       txtvProduct = (TextView) findViewById(R.id.txtProduct);
       imgvProduct = (ImageView) findViewById(R.id.imgvProduct);

       btnvSearchProd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SearchProd();
           }
       });
    }

    @SuppressLint("New Api")
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

    public void SearchProd()
    {
        try{
            Statement stm = connectionDB().createStatement();

            ResultSet rs = stm.executeQuery("SELECT id_producto, id_marca, id_serie, nombre_producto, precio_producto, stock_producto, " +
                    "codigo_producto, descripcion_producto, ubicacion_producto, precio_real FROM Tbl_Productos WHERE nombre_producto LIKE " +
                    "'%'+'"+edtvFieldProd.getText().toString()+"'+'%'");

            if(rs.next())
            {
                txtvId.setText(rs.getString(1));
                txtvProduct.setText(rs.getString(4));
            }
            edtvFieldProd.setText("");
        }
        catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}