package com.ronald.apptractoleo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CreateNewProduct extends AppCompatActivity {

    EditText edtvFieldProd, edtvFieldIdProd, edtvFileName;
    Button btnvCreateProd, btnvFileArchive;
    ImageView imgvInsertProd;
    TextView txtvFilePath;
    Spinner spinnvMarca;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_product);

        edtvFieldProd = findViewById(R.id.edtFieldProd);
        edtvFieldIdProd = findViewById(R.id.edtFieldIdProd);
        //edtvFileName = findViewById(R.id.edtFileName);

        btnvCreateProd = findViewById(R.id.btnCreateProd);
        btnvFileArchive = findViewById(R.id.btnFileImage);

        //txtvFilePath = findViewById(R.id.txtFilePath);

        imgvInsertProd = findViewById(R.id.imgviewProdInsert);

        spinnvMarca = findViewById(R.id.spinnMarca);

        fillSpinner();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        btnvFileArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(CreateNewProduct.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }
                else{
                    selectImage();
                }
            }
        });
    }

    public void fillSpinner()
    {
        try{
            Statement stm = connectionDB().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Tbl_Marca");

            ArrayList<String> marca = new ArrayList<String>();

            while(rs.next())
            {
                String namemarca = rs.getString("nombre_marca");
                marca.add(namemarca);
            }

            ArrayAdapter array = new ArrayAdapter(this, R.layout.spinn_fill_marca,marca);
            spinnvMarca.setAdapter(array);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void selectImage()
    {
        Intent intFileImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intFileImg.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intFileImg, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK)
        {
            if(data != null)
            {
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null)
                {
                    try{
                        InputStream varInputStream = getContentResolver().openInputStream(selectedImageUri);
                        Bitmap varBitmap = BitmapFactory.decodeStream(varInputStream);
                        imgvInsertProd.setImageBitmap(varBitmap);

                        btnvCreateProd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ByteArrayOutputStream varByteArrayOutputStream = new ByteArrayOutputStream();
                                varBitmap.compress(Bitmap.CompressFormat.PNG, 0, varByteArrayOutputStream);
                                byte[] byteImg = varByteArrayOutputStream.toByteArray();

                                try{
                                    PreparedStatement pst = connectionDB().prepareStatement("insert into Prueba values (?,?,?)");
                                    pst.setString(1,edtvFieldIdProd.getText().toString());
                                    pst.setString(2,edtvFieldProd.getText().toString());
                                    pst.setBytes(3,byteImg);
                                    pst.executeUpdate();

                                    Toast.makeText(getApplicationContext(), "Producto creado", Toast.LENGTH_SHORT).show();
                                }
                                catch (SQLException e)
                                {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
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

}
