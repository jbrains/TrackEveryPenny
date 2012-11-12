package ca.jbrains.upfp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class MyActivity extends Activity {
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
        try {
            final FileOutputStream fileOutputStream = openFileOutput("TrackEveryPenny.csv", Context.MODE_WORLD_READABLE);
            final PrintWriter canvas = new PrintWriter(fileOutputStream);
            canvas.println("Awesome CSV!");
            canvas.flush();
            canvas.close();
            fileOutputStream.close();

            final FileInputStream fileInputStream = openFileInput("TrackEveryPenny.csv");
            final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final StringBuffer contents = new StringBuffer();
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) break;
                contents.append(line);
            }
            Toast.makeText(getApplicationContext(), "Contents: " + contents.toString(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Log.e("TrackEveryPenny", "Couldn't export", e);
        } catch (IOException e) {
            Log.e("TrackEveryPenny", "Couldn't export", e);
        }
    }

    public void createTransactionOnCurrentDate(View clicked) {
        Toast.makeText(getApplicationContext(), "Create transaction on current date not yet implemented", Toast.LENGTH_LONG).show();
    }
}
