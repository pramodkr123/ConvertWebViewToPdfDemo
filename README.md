# ConvertWebViewToPdfDemo
Library to create pdf file from webview

Use this dependenices into your app level build.gradle file


allprojects {
	repositories {
		maven { url "https://jitpack.io" }
	}
}

compile 'com.github.pramodkr123:ConvertWebViewToPdfDemo:1.0.0'


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




