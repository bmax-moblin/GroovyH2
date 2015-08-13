package com.moblin.groovyh2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {
    private TextView textOut;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textOut = (TextView) findViewById(R.id.text_out);
        dbHelper = new DatabaseHelper();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_write:
                textOut.setText(dbHelper.write());
                return true;
            case R.id.action_read:
                textOut.setText(dbHelper.read());
                return true;
            case R.id.action_delete:
                textOut.setText(dbHelper.delete());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
