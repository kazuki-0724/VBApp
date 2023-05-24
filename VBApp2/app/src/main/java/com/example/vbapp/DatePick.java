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

    private TextView dayTextView;

    public DatePick(TextView textView){
        this.dayTextView = textView;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this,  year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year,
                          int monthOfYear, int dayOfMonth) {
        String str = String.format(Locale.US, "%d/%d/%d",year, monthOfYear+1, dayOfMonth);
        dayTextView.setText( str );
    }

}
