package com.example.vbapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Locale;

public class DatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    //ダイアログに表示する日付ピッカーのTextView
    private TextView datePickerTextView;

    /**
     * コンストラクタ
     * @param textView
     */
    public DatePick(TextView textView) {
        this.datePickerTextView = textView;

    }

    /**
     * 現在の日時でのダイアログを返す
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return
     */
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,  year, month, day);
    }

    /**
     * 日付がセットされたらtextViewに反映させる
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String str = String.format(Locale.US, "%d/%02d/%02d",year, monthOfYear+1, dayOfMonth);
        datePickerTextView.setText( str );
    }

}
