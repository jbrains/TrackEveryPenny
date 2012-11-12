package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class BrowseTransactionsActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView transactionsCountView = (TextView) findViewById(R.id.transactionsCount);
        transactionsCountView.setText(String.valueOf(1));
    }

    public void exportTransactionsToCsv(View clicked) {
        final String externalStorageState = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            Toast.makeText(getApplicationContext(), "There's no external storage available, so I can't export.", Toast.LENGTH_LONG).show();
            return;
        }

        final File externalStorageDirectory = Environment.getExternalStorageDirectory();
        final File externalDownloadsDirectory = new File(externalStorageDirectory, Environment.DIRECTORY_DOWNLOADS);
        final File transactionsCsvFile = new File(externalDownloadsDirectory, "TrackEveryPenny.csv");
        try {
            final PrintWriter canvas = new PrintWriter(transactionsCsvFile);
            canvas.println("Eventually, some awesome CSV shit.");
            canvas.println("Seriously, this will be CSV.");
            canvas.println("Why can't I see this file on the file manager?!");
            canvas.flush();
            canvas.close();
            Toast.makeText(getApplicationContext(), "Exported transactions to " + transactionsCsvFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

            final BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionsCsvFile));
            final StringWriter contents = new StringWriter();
            final PrintWriter readCanvas = new PrintWriter(contents);
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) break;
                readCanvas.println(line);
            }
            Toast.makeText(getApplicationContext(), "Contents: " + contents.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException logged) {
            Toast.makeText(getApplicationContext(), "I tried to write to external storage, but failed.", Toast.LENGTH_LONG).show();
            Log.e("TrackEveryPenny", "Failed to write to external public storage", logged);
        }
    }

    public void createTransactionOnCurrentDate(View clicked) {
        Toast.makeText(getApplicationContext(), "Create transaction on current date not yet implemented", Toast.LENGTH_LONG).show();
    }
}
