package edu.uoc.pac4.ui.streams

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import edu.uoc.pac4.R
import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.TwitchApiService
import edu.uoc.pac4.data.network.Network
import edu.uoc.pac4.data.network.UnauthorizedException
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.streams.Stream
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.user.User
import edu.uoc.pac4.ui.LaunchViewModel
import edu.uoc.pac4.ui.login.LoginActivity
import edu.uoc.pac4.ui.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_streams.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class StreamsActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"

    private val adapter = StreamsAdapter()
    private val layoutManager = LinearLayoutManager(this)

   //private val twitchApiService = TwitchApiService(Network.createHttpClient(this))
   // Lazy Inject ViewModel
   private val viewModel: StreamViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)
        // Init RecyclerView
        initRecyclerView()
        // Swipe to Refresh Listener
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getStreams()
        }

        setupViewModel()

        viewModel.loadStreams()
    }

    private fun setupViewModel()
    {
        viewModel.getIsRefreshing().observe(this, Observer<Boolean> {
            swipeRefreshLayout.isRefreshing = it
        })

        viewModel.getError().observe(this, Observer<StreamViewModel.Errors> { error ->

            if(error == StreamViewModel.Errors.Unauthorized) {
                // Clear local access token
                SessionManager(this@StreamsActivity).clearAccessToken()
                // User was logged out, close screen and open login
                finish()
                startActivity(Intent(this@StreamsActivity, LoginActivity::class.java))
            }

        })

        viewModel.getStreams().observe(this, Observer<List<Stream>> {
            if (viewModel.getCurrentCursor() != null) {
                // We are adding more items to the list
                adapter.submitList(adapter.currentList.plus(it))
            } else {
                // It's the first n items, no pagination yet
                adapter.submitList(it)
            }

            //error??
            if (adapter.currentList.isNullOrEmpty()) {
                Toast.makeText(
                        this@StreamsActivity,
                        getString(R.string.error_streams), Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



    private fun initRecyclerView() {
        // Set Layout Manager
        recyclerView.layoutManager = layoutManager
        // Set Adapter
        recyclerView.adapter = adapter
        // Set Pagination Listener
        recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
               viewModel.loadStreams(viewModel.getNextCursor())
            }

            override fun isLastPage(): Boolean {
                return viewModel.getNextCursor() == null
            }

            override fun isLoading(): Boolean {
                return swipeRefreshLayout.isRefreshing
            }
        })
    }

    // region Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate Menu
        menuInflater.inflate(R.menu.menu_streams, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_user -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // endregion
}