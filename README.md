# ConvertWebViewToPdfDemo
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
     <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" /> //Add this permission for Android 11
     
If your targetSdkVersion >= 24, then we have to use FileProvider class to give access to the particular file or folder to make them accessible for other apps. 
Add a FileProvider tag in AndroidManifest.xml under tag.

    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
    ...
    <application
    android:requestLegacyExternalStorage="true">
        ...
         <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="com.package.name.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
        </provider>
    </application>
    </manifest>
    

Then create a provider_paths.xml file in res/xml folder.

    <?xml version="1.0" encoding="utf-8"?>
    <paths xmlns:android="http://schemas.android.com/apk/res/android">
     <external-path name="external_files" path="."/>
    </paths>

Before Create pdf check this pernmission for Android 11 devices.

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (Environment.isExternalStorageManager()) {
            //write create pdf code here
         } else {
	    // request permission
	     try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, 1)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 1)
            }
	   }
     }
     
     
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
	    // check permission is Granting
        if (requestCode == 1 && Environment.isExternalStorageManager()){
            //write create pdf code here
        }
    }
     



Sample code :

                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
                final String fileName="Test.pdf";

                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                PdfView.createWebPrintJob(MainActivity.this, webView, directory, fileName, new PdfView.Callback() {

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




