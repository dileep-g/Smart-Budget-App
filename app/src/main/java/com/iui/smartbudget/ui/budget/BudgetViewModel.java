package com.iui.smartbudget.ui.budget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iui.smartbudget.utilities.DataHolder;

import java.time.Month;

public class BudgetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BudgetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(String.valueOf(DataHolder.monthToCategoryMap.get(Month.JANUARY).get("travel")));
    }

    public LiveData<String> getText() {
        return mText;
    }
}
