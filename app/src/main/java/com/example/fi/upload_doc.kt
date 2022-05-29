package com.example.fi

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_upload_doc.*

@Suppress("DEPRECATION")
class upload_doc : AppCompatActivity() {

    lateinit var filepath: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_doc)

        val url = Constant.Docuploadurl
        val skipbtn = findViewById<Button>(R.id.skipfornow)


//        skipbtn.setOnClickListener {
//            startActivity(Intent(this, maindashboard::class.java))
//        }
//
//
//
//        choosebtn.setOnClickListener {
//            startFileChooser()
//        }
//
//    }
//
//
//    private fun startFileChooser() {
//        var i = Intent()
//        i.setType("application/pdf")
//        i.setAction(Intent.ACTION_GET_CONTENT)
//        startActivityForResult(Intent.createChooser(i, "Choose File"), 111)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
//            filepath = data.data!!
//            val path = data.dataString
//            val image = findViewById<ImageView>(R.id.imageview)
//            val txt = findViewById<TextView>(R.id.text)
//            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filepath)
//            image.setBackgroundResource(R.drawable.pdficon)
//            txt.setText(path)
//            choosebtn.setText("Change File")
//        }
    }
}



