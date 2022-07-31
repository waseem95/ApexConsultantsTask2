package com.task.apexConsultants.MVVM

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apexConsultants.R
import com.task.apexConsultants.Guest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// @HiltViewModel will make models to be
// created using Hilt's model factory
// @Inject annotation used to inject all
// dependencies to view model class
@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    private var _buttonState = MutableLiveData<Boolean>()
    val buttonState: LiveData<Boolean> = _buttonState
    var guestsList = ArrayList<Guest>()

    fun updateButtonState(state: Boolean? = false) {
        _buttonState.postValue(state == true)
    }

    fun populateList(context: Context) {
        if (guestsList.isEmpty()) {
            guestsList.add(Guest(context.getString(R.string.have_reservations), "title", false))
            guestsList.add(Guest("Dale Gibson", "have_seat", false))
            guestsList.add(Guest("Jeremy Gibson", "have_seat", false))
            guestsList.add(Guest(context.getString(R.string.need_reservations), "title", false))
            guestsList.add(Guest("Linda Gibson", "need_seat", false))
            guestsList.add(Guest("Margaret Gibson", "need_seat", false))
        }
    }
}

