package edu.uoc.pac4.ui.streams

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import edu.uoc.pac4.R
import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.data.user.UserRepository
import edu.uoc.pac4.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_streams.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Created by albert on 09/01/21.
 */

class StreamViewModel (private val repository: StreamsRepository) : ViewModel()
{

    enum class Errors {
        None,
        Unauthorized
    }

    private val TAG = "StreamViewModel"

    private val streams = MutableLiveData<List<Stream>>()
    private var currentCursor: String? = null
    private var nextCursor: String? = null
    private var isRefresing = MutableLiveData<Boolean>()

    private val error = MutableLiveData<Errors>()

    init {
        loadStreams()
    }

    fun getCurrentCursor(): String?
    {
        return currentCursor
    }
    fun getNextCursor(): String?
    {
        return nextCursor
    }

    fun getError(): LiveData<Errors>
    {
        return error
    }

    fun getStreams(): LiveData<List<Stream>>
    {
        return streams
    }

    fun getIsRefreshing(): LiveData<Boolean>
    {
        return isRefresing
    }

    fun loadStreams(cursor: String? = null) {
        isRefresing.postValue(true)

        Log.d(TAG, "Requesting streams with cursor $cursor")
        currentCursor = cursor

        // Get Twitch Streams
        viewModelScope.launch {
            try {

                var response = repository.getStreams(cursor)
                response.second.let {
                    Log.d(TAG, "Got Streams: $response")

                    streams.postValue(it.orEmpty())
                    // Save cursor for next request
                    nextCursor = response.first
                }

            } catch (t: UnauthorizedException) {

                Log.w(TAG, "Unauthorized Error getting streams", t)
                isRefresing.postValue(false)
                error.postValue(Errors.Unauthorized)

            }

            isRefresing.postValue(false)
        }
    }


}