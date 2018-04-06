# ConvertWebViewToPdfDemo
Library to create pdf file from webview

Add the following to your project level build.gradle:

        allprojects {	
	        repositories {
		        maven { url "https://jitpack.io" }
	        }
        }

Add this to your app build.gradle:

     compile 'com.github.pramodkr123:ConvertWebViewToPdfDemo:1.0.2'

Permission in Manifest

     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

Sample code :

                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
                final String fileName="Test.pdf";

                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                PdfView.createWebPrintJob(MainActivity.this, webView, path, fileName, new PdfView.Callback() {

                    @Override
                    public void success(String path) {
                        progressDialog.dismiss();
                        PdfView.openPdfFile(MainActivity.this,getString(R.string.app_name),"Do you want to open the pdf file?"+fileName,path);
                    }

                    @Override
                    public void failure() {
                        progressDialog.dismiss();

                    }
                });




