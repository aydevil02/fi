package com.example.fi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Upload_PDF extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_FILE_REQUEST = 102;
    private static final String TAG ="Upload_PDF";
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
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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
            String paths = copyFileToInternal(data.getData());

            Log.d("File Path : ", "" + paths);
            Log.d("File Path 2 : ", "" + uri.getPath());
            file_path = paths;
        }else {
            Toast.makeText(this, "Select a file", Toast.LENGTH_SHORT).show();
        }
    }

    private String copyFileToInternal(Uri fileUri){
        Cursor theCursor = getContentResolver().query(fileUri,
                new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE},
                null,null,null);
        theCursor.moveToFirst();
        @SuppressLint("Range") String displayName = theCursor.getString(theCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        @SuppressLint("Range") long size = theCursor.getLong(theCursor.getColumnIndex(OpenableColumns.SIZE));
        Log.d(TAG,"Size"+size );
        Log.d(TAG,"Name"+displayName );
        File theFile = new File(getFilesDir()+"/"+displayName);
        try {
            FileOutputStream fileoutputStream = new FileOutputStream(theFile);
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            byte buffer[] = new byte[1024];
            int read ;
            while ((read=inputStream.read(buffer))!= -1){
                fileoutputStream.write(buffer,0,read);
            }
            inputStream.close();
            fileoutputStream.close();
            return theFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
