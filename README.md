# ConvertWebViewToPdf
Library to create pdf file from webview

Add the following to your project level build.gradle:

        allprojects {	
	        repositories {
		        maven { url "https://jitpack.io" }
	        }
        }

Add this to your app build.gradle:

     compile 'com.github.pramodkr123:ConvertWebViewToPdfDemo:1.0.4'

Permission in Manifest

     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

     
If your targetSdkVersion >= 24, then we have to use FileProvider class to give access to the particular file or folder to make them accessible for other apps. 
Add a FileProvider tag in AndroidManifest.xml under tag.

    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    ...
    <application
    android:requestLegacyExternalStorage="true">
        ...
         <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.convertwebviewtopdf.demo.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>
    </application>
    </manifest>
    

Then create a provider_paths.xml file in res/xml folder.

    <paths xmlns:android="http://schemas.android.com/apk/res/android">
    <external-path
        name="external_files"
        path="." />
    <root-path
        name="root"
        path="." />
    </paths>

Before Create pdf check this permission for Android 11 devices.
   
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
	    // check permission is Granting
        if (requestCode == 1 && Environment.isExternalStorageManager()){
            //write create pdf code here
        }
    }
     



Sample code :

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




