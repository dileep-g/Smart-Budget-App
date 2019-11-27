package com.iui.smartbudget.ui.budget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.iui.smartbudget.utilities.DataHolder;

public class BudgetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BudgetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(String.valueOf(DataHolder.getInstance().categoryToAvgExpenseMap.get("shopping")));
    }

    public LiveData<String> getText() {
        return mText;
    }
}
