package com.udacity.shoestore.shoesListing.presentation

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.utils.USER_LOGGED_IN

class ShoeListingViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    val newShoeName = MutableLiveData<String>()
    val newShoeSize = MutableLiveData<String>()
    val newShoesCompany = MutableLiveData<String>()
    val newShoeDescription = MutableLiveData<String>()

    val isSaveButtonEnabled = MediatorLiveData<Boolean>().apply {
        value = false
        addSource(newShoeName) {
            value = areInputsValid(
                it,
                newShoeSize.value,
                newShoesCompany.value,
                newShoeDescription.value
            )
        }
        addSource(newShoeSize) {
            value = areInputsValid(
                newShoeName.value,
                it,
                newShoesCompany.value,
                newShoeDescription.value
            )
        }
        addSource(newShoesCompany) {
            value =
                areInputsValid(newShoeName.value, newShoeSize.value, it, newShoeDescription.value)
        }
        addSource(newShoeDescription) {
            value = areInputsValid(newShoeName.value, newShoeSize.value, newShoesCompany.value, it)
        }
    }

    val newShoes = MediatorLiveData<Shoe?>().apply {
        addSource(isSaveButtonEnabled) {
            value = if (it) {
                Shoe(
                    newShoeName.value!!,
                    newShoeSize.value!!,
                    newShoesCompany.value!!,
                    newShoeDescription.value!!
                )
            } else {
                null
            }
        }
    }

    private val _shoesList = MutableLiveData<List<Shoe>>(emptyList())
    val shoesList: LiveData<List<Shoe>>
        get() = _shoesList

    fun saveDraftShoes(shoe: Shoe?) {
        shoe?.let {
            val currentShoesList = _shoesList.value as? MutableList ?: mutableListOf()
            currentShoesList.add(it)
            _shoesList.value = currentShoesList
        }
    }

    private fun areInputsValid(
        shoesName: String?,
        shoesSize: String?,
        shoesCompany: String?,
        shoesDescription: String?
    ): Boolean {
        return !shoesName?.trim().isNullOrEmpty() && !shoesSize?.trim()
            .isNullOrEmpty() && !shoesCompany?.trim().isNullOrEmpty()
                && !shoesDescription?.trim().isNullOrEmpty()
    }

    fun isUserLoggedIn() = sharedPreferences.getBoolean(USER_LOGGED_IN, false)

    fun setUserLoggedOut() = sharedPreferences.getBoolean(USER_LOGGED_IN, false)

}