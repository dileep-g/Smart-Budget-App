package com.iui.smartbudget.ui.expenses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iui.smartbudget.utilities.DataHolder;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
public class ExpensesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExpensesViewModel() {
        mText = new MutableLiveData<>();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        //mText.setValue(DataHolder.records.get(0).getVendor() + format.format(DataHolder.records.get(0).getDateTime()));
        Date date=DataHolder.records.get(0).getDateTime();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //mText.setValue(localDate.getMonth().name());
    }

    public LiveData<String> getText() {
        return mText;
    }
}
