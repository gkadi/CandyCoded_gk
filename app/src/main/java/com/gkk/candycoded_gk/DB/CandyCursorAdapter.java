package com.gkk.candycoded_gk.DB;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.gkk.candycoded_gk.R;

public class CandyCursorAdapter extends CursorAdapter {
    public CandyCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_candy, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = view.findViewById(R.id.text_view_candy);

        String candyName = cursor.getString(cursor.getColumnIndex(CandyContract.CandyEntry.COLUMN_NAME_NAME));
        textView.setText(candyName);
    }
}
