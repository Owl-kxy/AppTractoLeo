package com.ronald.apptractoleo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
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

public class SearchProductUser extends AppCompatActivity {

    EditText edtvFieldProd;
    TextView txtvId, txtvProduct, txtvSerieProd, txtvCodigoProd, txtvStockProd, txtvPriceProd, txtvDescriptionProd, txtvUbicationProd, txtvMarca;
    ImageView imgvProduct;
    Button btnvSearchProd, btnvSearchProdImg;
    Dialog varDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_user);

       edtvFieldProd = findViewById(R.id.edtFieldProd);

       btnvSearchProd = findViewById(R.id.btnSearchProd);
       btnvSearchProdImg = findViewById(R.id.btnSearchProdImg);

       //imgvProduct = findViewById(R.id.imgvProduct);

       txtvId = findViewById(R.id.txtId);
       txtvProduct = findViewById(R.id.txtProduct);
       txtvSerieProd = findViewById(R.id.txtSerieProd);
       txtvCodigoProd = findViewById(R.id.txtCodigoProd);
       txtvStockProd = findViewById(R.id.txtStockProd);
       txtvPriceProd = findViewById(R.id.txtPriceProd);
       txtvDescriptionProd = findViewById(R.id.txtDescriptionProd);
       txtvUbicationProd = findViewById(R.id.txtUbicationProd);
       txtvMarca = findViewById(R.id.txtMarca);

       varDialog = new Dialog(this);

       btnvSearchProd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SearchProd();
           }
       });

       /*btnvSearchProdImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SearchImgProd();
           }
       });*/
    }

    public void ShowPopUpImg(View v)
    {
        TextView txtvClose;
        ImageView imgvPoPProduct;

        varDialog.setContentView(R.layout.custompopupimage);
        imgvPoPProduct = varDialog.findViewById(R.id.imgPoPProduct);
        txtvClose = varDialog.findViewById(R.id.txtClose);

        String id = txtvId.getText().toString();

        try{
            Statement stm2 = connectionDB().createStatement();
            ResultSet rs2 = stm2.executeQuery("SELECT image_producto FROM Tbl_Productos WHERE id_producto = '"+id+"'");

            if(rs2.next())
            {
                byte[] img = rs2.getBytes(1);

                //byte[] byteImg = Base64.decode(a,Base64.DEFAULT);
                Bitmap bitmapImg = BitmapFactory.decodeByteArray(img,0,img.length);
                imgvPoPProduct.setImageBitmap(bitmapImg);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        txtvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varDialog.dismiss();
            }
        });

        varDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        varDialog.show();
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

            ResultSet rs = stm.executeQuery("SELECT id_producto, ma.nombre_marca, se.nombre_serie, nombre_producto, precio_producto, stock_producto, " +
                    "codigo_producto, descripcion_producto, ubicacion_producto, precio_real FROM Tbl_Productos pro INNER JOIN Tbl_Marca ma ON " +
                    "ma.id_marca = pro.id_marca INNER JOIN Tbl_Serie se ON se.id_serie = pro.id_serie WHERE nombre_producto LIKE " +
                    "'%'+'"+edtvFieldProd.getText().toString()+"'+'%'");

            if(rs.next())
            {
                txtvId.setText(rs.getString(1));
                txtvMarca.setText(rs.getString(2));
                txtvSerieProd.setText(rs.getString(3));
                txtvProduct.setText(rs.getString(4));
                String precio = rs.getString(5);
                txtvPriceProd.setText("s/. "+ precio);
                String stock = rs.getString(6);
                txtvStockProd.setText(stock + " unidades");
                txtvCodigoProd.setText(rs.getString(7));
                txtvDescriptionProd.setText(rs.getString(8));
                txtvUbicationProd.setText(rs.getString(9));
            }

            edtvFieldProd.setText("");
        }
        catch (SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

/*    public void SearchImgProd()
    {
        String id = txtvId.getText().toString();

        try{
            Statement stm2 = connectionDB().createStatement();
            ResultSet rs2 = stm2.executeQuery("SELECT image_producto FROM Tbl_Productos WHERE id_producto = '"+id+"'");

            if(rs2.next())
            {
                byte[] img = rs2.getBytes(1);

                //byte[] byteImg = Base64.decode(a,Base64.DEFAULT);
                Bitmap bitmapImg = BitmapFactory.decodeByteArray(img,0,img.length);
                imgvProduct.setImageBitmap(bitmapImg);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/

}