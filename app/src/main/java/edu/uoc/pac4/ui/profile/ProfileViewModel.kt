package edu.uoc.pac4.ui.profile

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel()
{
    private val TAG = "ProfileViewModel"
    private val user = MutableLiveData<User>()
    private val visibilityProgressBar = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            user.postValue(repository.getUser())
        }
    }

    fun getUser(): LiveData<User> {
        //loadUser()
        return user
    }

    fun getVisibilityProgressBar(): LiveData<Int>
    {
        return visibilityProgressBar
    }

    private fun loadUser() {
        viewModelScope.launch {
            user.postValue(repository.getUser())
        }
    }

    fun updateUserDescription(description: String) {

        viewModelScope.launch {
            visibilityProgressBar.postValue(View.VISIBLE)
            // Update the Twitch User Description using the API
            try {
                repository.updateUser(description)?.let { updatedUser ->
                    user.postValue(updatedUser)
                } ?: run {
                    // Error :( Que hacer?
                }
            } catch (t: UnauthorizedException) {
                throw t
            }
            visibilityProgressBar.postValue(View.GONE)
        }

    }

}