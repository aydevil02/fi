package com.example.fi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Upload_PDF extends AppCompatActivity implements After_Request {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_FILE_REQUEST = 102;
    String  file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        Button select = findViewById(R.id.selectfilebtn);
        ImageView set_img = findViewById(R.id.pdfimage);
        Button upload = findViewById(R.id.uploadfile);
        Button skip = findViewById(R.id.skipfornow);
        Button proceednxt = findViewById(R.id.proceednxt);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Upload_PDF.this,maindashboard.class);
                startActivity(i);
            }
        });
        proceednxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Upload_PDF.this,Mlmodel.class);
                startActivity(i);
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                set_img.setImageResource(R.drawable.pdficon);

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.setType("application/pdf");

                startActivityForResult(intent, ALL_FILE_REQUEST);

            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(file_path == null) {
                    Toast.makeText(Upload_PDF.this, "select file", Toast.LENGTH_SHORT).show();
                    return;
                }

                SendRequest.uploadPdf(file_path , getApplicationContext());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            if (data == null) {
                return;
            }

        Uri uri = data.getData();
        String paths = uri.getPath().split(":")[1];
        Log.d("File Path : ", "" + paths);
        file_path = paths;
    }

    @Override
    public void upload_pdf_result(String response) {
        Log.d("abhishek lodu",response.toString());
    }
}
