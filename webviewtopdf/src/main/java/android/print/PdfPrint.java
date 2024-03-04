package android.print;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;

public class PdfPrint {
    private static final String TAG = PdfPrint.class.getSimpleName();
    private final PrintAttributes printAttributes;

    public PdfPrint(PrintAttributes printAttributes) {
        this.printAttributes = printAttributes;
    }

    public void print(final PrintDocumentAdapter printAdapter, String path, final CallbackPrint callback) {
        ParcelFileDescriptor parcelFileDescriptor = getOutputFile(path);
        if (parcelFileDescriptor == null) {
            callback.onFailure();
            return;
        }

        printAdapter.onLayout(null, printAttributes, null, new PrintDocumentAdapter.LayoutResultCallback() {
            @Override
            public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                printAdapter.onWrite(new PageRange[]{PageRange.ALL_PAGES}, parcelFileDescriptor, new CancellationSignal(), new PrintDocumentAdapter.WriteResultCallback() {
                    @Override
                    public void onWriteFinished(PageRange[] pages) {
                        super.onWriteFinished(pages);
                        if (pages.length > 0) {
                            callback.success(path);
                        }else {
                            callback.onFailure();
                        }

                    }
                });
            }
        }, null);
    }

    private ParcelFileDescriptor getOutputFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor", e);
        }
        return null;
    }

    public interface CallbackPrint{
        void success(String path);
        void onFailure();
    }
}
