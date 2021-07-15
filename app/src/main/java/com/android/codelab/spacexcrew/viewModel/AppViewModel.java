package com.android.codelab.spacexcrew.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.codelab.spacexcrew.repo.AppRepository;
import com.android.codelab.spacexcrew.model.SpaceX;

import java.util.List;

public class AppViewModel extends ViewModel {
    // 1 add dependency
    // 2 create viewModel
    // 3 connecting viewModel with Database
    // 4 LiveData
    // 5 connect ViewModel with UI (that is activity)
    // 6 connect ViewModel with UI and Listening to "LIVEDATA"
    // 7 Listen and Observe the changes to Live Data


    // LiveData<String> liveData;
    public MutableLiveData<List<SpaceX>> mutableLiveData;


    public AppViewModel() {
        super();
        AppRepository repository = AppRepository.getInstance();
        mutableLiveData = repository.getCrews();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<List<SpaceX>> getListMutableLiveData() {
        return mutableLiveData;
    }
}
