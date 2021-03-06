package com.gkk.candycoded_gk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.gkk.candycoded_gk.DB.CandyContract;
import com.gkk.candycoded_gk.DB.CandyContract.CandyEntry;
import com.gkk.candycoded_gk.DB.CandyCursorAdapter;
import com.gkk.candycoded_gk.DB.CandyDbHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.TextHttpResponseHandler;

public class MainActivity extends AppCompatActivity {
    private Candy[] candies;
    private CandyDbHelper candyDbHelper = new CandyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = candyDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM candy", null);

        final CandyCursorAdapter adapter = new CandyCursorAdapter(this, cursor);
        ListView listView = findViewById(R.id.list_view_candy);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_POSITION, i);
                startActivity(detailIntent);
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://vast-brushlands-23089.herokuapp.com/main/api",
            new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("AsyncHttpClient", "response = " + responseString);
                    Toast.makeText(MainActivity.this, "AsyncHttpClient failure", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("AsyncHttpClient", "response = " + responseString);
                    Gson gson = new GsonBuilder().create();
                    candies = gson.fromJson(responseString, Candy[].class);

                    addCandiesToDatabase(candies);

                    SQLiteDatabase db = candyDbHelper.getWritableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
                    adapter.changeCursor(cursor);
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    // ***
    // TODO - Task 1 - Show Store Information Activity
    // ***


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.info:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                break;
            default:
                return false;
        }
        return true;
    }

    private void addCandiesToDatabase(Candy[] candies) {
        SQLiteDatabase db = candyDbHelper.getWritableDatabase();

        for (Candy candy : candies){
            ContentValues values = new ContentValues();
            values.put(CandyEntry.COLUMN_NAME_NAME, candy.name);
            values.put(CandyEntry.COLUMN_NAME_PRICE, candy.price);
            values.put(CandyEntry.COLUMN_NAME_DESC, candy.description);
            values.put(CandyEntry.COLUMN_NAME_IMAGE, candy.image);

            db.insert(CandyEntry.TABLE_NAME, null, values);
        }
    }
}