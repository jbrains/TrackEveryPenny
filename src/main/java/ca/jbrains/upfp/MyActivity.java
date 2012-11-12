package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        Toast.makeText(getApplicationContext(), "Export not yet implemented", Toast.LENGTH_LONG).show();
    }

    public void createTransactionOnCurrentDate(View clicked) {
        Toast.makeText(getApplicationContext(), "Create transaction on current date not yet implemented", Toast.LENGTH_LONG).show();
    }
}
