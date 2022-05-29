package com.example.fi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Upload_PDF extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int ALL_FILE_REQUEST = 102;
    private static final String TAG ="Upload_PDF";
    ArrayList <String> Selected_Files;
    ArrayList <String> Selected_Files_Name;
    private static final int requestcode =1 ;
    String  file_path;
    ProgressBar progressBar;
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        recyclerView = findViewById(R.id.selectedfilerecycler);
        Button select = findViewById(R.id.selectfilebtn);
        Button upload = findViewById(R.id.uploadfile);
        Button skip = findViewById(R.id.skipfornow);
        progressBar = findViewById(R.id.uploadpdfprogress);
        progressBar.setVisibility(View.GONE);
        Selected_Files = new ArrayList<String>();
        Selected_Files_Name = new ArrayList<String>();

        Recycler_Selected_Files adapter = new Recycler_Selected_Files(Selected_Files_Name , getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Upload_PDF.this,maindashboard.class);
                startActivity(i);
            }
        });


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent,requestcode);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( Selected_Files.size() < 1) {
                    Toast.makeText(Upload_PDF.this, "select file", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

//                SendRequest.uploadPdf(file_path , getApplicationContext());
                String[] str = new String[Selected_Files.size()];

                for (int i = 0; i < Selected_Files.size(); i++) {
                    str[i] = Selected_Files.get(i);
                }
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

                uploadTask.execute(str);
            }
        });

    }

    @Override
    public void onActivityResult(int requestcode,int resultcode , Intent data)
    {
        super.onActivityResult(resultcode,resultcode,data);
        if (resultcode== requestcode && requestcode== Activity.RESULT_OK );
        {
            Log.d("Log1"," "+Selected_Files);

            if ((data==null))
                return;
            Log.d("Log2"," "+data.getData());
            if(data.getData()!= null){
                Uri uri =data.getData();
                Selected_Files.add(copyFileToInternal(uri));
                Recycler_Selected_Files adapter = new Recycler_Selected_Files(Selected_Files_Name , getApplicationContext());
                recyclerView.setAdapter(adapter);

            }
            if(null!= data.getClipData()){
                Selected_Files = new ArrayList<String>();
                String tempstring  = "";
                Log.d("Log4"," "+Selected_Files);
                for (int i = 0 ; i<data.getClipData().getItemCount(); i++)
                {
                    Uri uri =data.getClipData().getItemAt(i).getUri();
                    Selected_Files.add(copyFileToInternal(uri));
                    Log.d("Log3"," "+Selected_Files);

                }
                Log.d("Log"," "+Selected_Files);
                Recycler_Selected_Files adapter = new Recycler_Selected_Files(Selected_Files_Name , getApplicationContext());
                recyclerView.setAdapter(adapter);
                Log.d("Abhi",tempstring + Selected_Files);
            }
            else{
                Uri uri = data.getData();
                Log.d("Abhi", String.valueOf(uri));

            }
        }
    }

    private String copyFileToInternal(Uri fileUri){
        Cursor theCursor = getContentResolver().query(fileUri,
                new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE},
                null,null,null);
        theCursor.moveToFirst();
        @SuppressLint("Range") String displayName = theCursor.getString(theCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
        @SuppressLint("Range") long size = theCursor.getLong(theCursor.getColumnIndex(OpenableColumns.SIZE));
        Selected_Files_Name.add(displayName);
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
