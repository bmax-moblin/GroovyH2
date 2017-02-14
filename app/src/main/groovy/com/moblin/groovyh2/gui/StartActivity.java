package com.moblin.groovyh2.gui;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.moblin.groovyh2.R;
import com.moblin.groovyh2.db.DatabaseHelper;
import com.moblin.groovyh2.util.Assert;

public class StartActivity extends AppCompatActivity {
    private TextView mTextOut;
    private DatabaseHelper mDbHelper;

    /** Activity methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setupGui();
        setupDb();
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
                mTextOut.setText(mDbHelper.write());
                return true;
            case R.id.action_read:
                mTextOut.setText(mDbHelper.read());
                return true;
            case R.id.action_delete:
                mTextOut.setText(mDbHelper.delete());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Private methods */

    private void setupGui() {
        mTextOut = lookup(R.id.text_out);
        Assert.notNull(mTextOut, "View not found: text_out");
        mTextOut.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setupDb() {
        String dbName = getFilesDir() + "/test.db";
        mDbHelper = new DatabaseHelper(dbName);
    }

    private <T extends View> T lookup(@IdRes int viewId) {
        //noinspection unchecked
        return (T) findViewById(viewId);
    }

}
