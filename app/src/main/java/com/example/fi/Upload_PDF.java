package com.example.fi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Upload_PDF extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_FILE_REQUEST = 102;
    String  file_path;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        Button select = findViewById(R.id.selectfilebtn);
        ImageView set_img = findViewById(R.id.pdfimage);
        Button upload = findViewById(R.id.uploadfile);
        Button skip = findViewById(R.id.skipfornow);
        Button proceednxt = findViewById(R.id.proceednxt);
        progressBar = findViewById(R.id.uploadpdfprogress);
        progressBar.setVisibility(View.GONE);


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
                progressBar.setVisibility(View.VISIBLE);

//                SendRequest.uploadPdf(file_path , getApplicationContext());
                UploadTask uploadTask = new UploadTask(getApplicationContext(),
                        new After_Request() {
                            @Override
                            public void onFileUploadComplete(String status) {
                                Toast.makeText(Upload_PDF.this, "File Upload " + status, Toast.LENGTH_SHORT).show();
                                if(status.equalsIgnoreCase("success"))
                                {
//                                    Toast.makeText(Upload_PDF.this, "File Upload Complete", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent i = new Intent(Upload_PDF.this,Mlmodel.class);
                                    startActivity(i);

                                }
                                else{
                                    Toast.makeText(Upload_PDF.this, "I have failed you master", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                );
                uploadTask.execute(file_path);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK&& data != null) {
            if (data == null) {
                Toast.makeText(this, "Select a file", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri uri = data.getData();
            String paths = uri.getPath().split(":")[1];

            Log.d("File Path : ", "" + paths);
            Log.d("File Path 2 : ", "" + uri.getPath());
            file_path = uri.getPath();
        }else {
            Toast.makeText(this, "Select a file", Toast.LENGTH_SHORT).show();
        }
    }

}
