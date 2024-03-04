package com.convertwebviewtopdf.demo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

import com.webviewtopdf.PdfView;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    WebView webView;
    Button button_convert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=findViewById(R.id.webView);
        button_convert=findViewById(R.id.button_convert);

        webView.loadUrl("https://developer.android.com/develop");


        button_convert.setOnClickListener(v -> {
            final String fileName="Test.pdf";
            File file = new File(getExternalFilesDir("DemoApp"), fileName);
            if (file.exists()) file.delete();

            final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait");
            progressDialog.show();
            PdfView.createWebPrintJob(MainActivity.this, webView, file.getAbsolutePath(), new PdfView.Callback() {

                @Override
                public void success(String path) {
                    progressDialog.dismiss();
                    PdfView.openPdfFile(MainActivity.this, getString(R.string.app_name), "Do you want to open the pdf file?" + fileName, path, "com.convertwebviewtopdf.demo.fileprovider");
                }

                @Override
                public void failure(int errorCode) {
                    progressDialog.dismiss();
                }
            });

        });

    }

}
