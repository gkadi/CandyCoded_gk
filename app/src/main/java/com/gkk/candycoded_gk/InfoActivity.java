package com.gkk.candycoded_gk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView candyStoreImageView = (ImageView)findViewById(R.id.image_view_candy_store);
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.drawable.store_front);
        Picasso.with(this).
                load(uri).
                into(candyStoreImageView);

    }

    // ***
    // TODO - Task 2 - Launch the Google Maps Activity
    // ***

    public void onAdressesViewsClick(View view) {
        final String address = ((TextView) view).getText().toString();
        Uri uri = Uri.parse("geo:0,0?q=" + address);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            Toast.makeText(this, getResources().getString(R.string.google_maps_not_available), Toast.LENGTH_SHORT).show();
        }
    }


    // ***
    // TODO - Task 3 - Launch the Phone Activity
    // ***
    public void onPhoneNumberViewsClick(View view) {
        final String phoneNumber = ((TextView) view).getText().toString();
        Uri uri = Uri.parse("tel:" + phoneNumber);

        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        startActivity(intent);
    }
}