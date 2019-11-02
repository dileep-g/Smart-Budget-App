package com.iui.smartbudget.ui.expenses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iui.smartbudget.utilities.DataHolder;

import java.text.SimpleDateFormat;

public class ExpensesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExpensesViewModel() {
        mText = new MutableLiveData<>();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        mText.setValue(DataHolder.records.get(0).getVendor() + format.format(DataHolder.records.get(0).getDateTime()));
    }

    public LiveData<String> getText() {
        return mText;
    }
}
