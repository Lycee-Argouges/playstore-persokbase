package fr.argouges.persokbase.ui.example;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExampleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExampleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is example fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
