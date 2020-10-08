package com.gkk.candycoded_gk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gkk.candycoded_gk.DB.CandyContract;
import com.gkk.candycoded_gk.DB.CandyDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String SHARE_DESCRIPTION = "Look at this delicious candy from Candy Coded - ";
    public static final String HASHTAG_CANDYCODED = "#candycoded";
    public static final String EXTRA_POSITION = "position";
    String mCandyImageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = DetailActivity.this.getIntent();

        if(intent != null && intent.hasExtra("position")){
            int position = intent.getIntExtra(EXTRA_POSITION, 0);

            CandyDbHelper dbHelper = new CandyDbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM candy", null);
            cursor.moveToPosition(position);

            String candyName = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_NAME));
            String candyPrice = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_PRICE));
            mCandyImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_IMAGE));
            String candyDesc = cursor.getString(cursor.getColumnIndexOrThrow(CandyContract.CandyEntry.COLUMN_NAME_DESC));


            TextView textView = this.findViewById(R.id.text_view_name);
            textView.setText(candyName);

            TextView textViewPrice = this.findViewById(R.id.text_view_price);
            textViewPrice.setText(candyPrice);

            TextView textViewDesc = this.findViewById(R.id.text_view_desc);
            textViewDesc.setText(candyDesc);

            ImageView imageView = this.findViewById(R.id.image_view_candy);

            Picasso.with(this).load(mCandyImageUrl).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    // ***
    // TODO - Task 4 - Share the Current Candy with an Intent
    // ***


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_detail:
                String sms_body = SHARE_DESCRIPTION + mCandyImageUrl;
                String phone_number = "411";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.putExtra(android.content.Intent.EXTRA_TEXT, sms_body);
                intent.putExtra("sms_body", sms_body);
                intent.putExtra("address", phone_number);

                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else{
                    Toast.makeText(this, getResources().getString(R.string.plain_text_app_not_available), Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                return false;
        }
        return true;
    }
}