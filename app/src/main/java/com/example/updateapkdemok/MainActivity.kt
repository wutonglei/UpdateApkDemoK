package com.example.updateapkdemok

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_update.text = "点我呀"
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)

            val filePath = Environment.getExternalStorageDirectory().absolutePath + "/new.apk";
            Log.i(TAG, "onCreate: $filePath")
            var dir = File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
                Log.i(TAG, "onCreate: dir.exists() : ${dir.exists()}")
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                                                                                    顺便适配了AndroidQ
                val apkUri = FileProvider.getUriForFile(this@MainActivity, "com.example.updateapkdemok.fileProvider", dir)
                intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK);
                val uri = Uri.fromFile(dir);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }
            this@MainActivity.startActivity(intent);
        }
    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(this, "更新过了", Toast.LENGTH_LONG).show()
//        Log.i(TAG, "onResume: 更新过了")
    }
}