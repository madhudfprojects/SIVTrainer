package com.det.skillinvillage;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

  private MutableLiveData<String> mTitle = new MutableLiveData<>();

  private LiveData<String> mText = Transformations.map(mTitle, new Function<String, String>() {
    @Override
    public String apply(String input) {
      return "Contact not available in " + input;
    }
  });

  public void setIndex(String index) {
    mTitle.setValue(index);
  }

  public LiveData<String> getText() {
    return mText;
  }
}