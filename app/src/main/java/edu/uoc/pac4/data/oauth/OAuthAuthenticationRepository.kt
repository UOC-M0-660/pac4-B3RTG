package edu.uoc.pac4.data.oauth

import android.util.Log
import edu.uoc.pac4.data.SessionManager

/**
 * Created by alex on 11/21/20.
 */
class OAuthAuthenticationRepository(
     private val dataSource: OAuthDataSource,
     private val sessionManager: SessionManager
) : AuthenticationRepository {

    private val TAG = "OAAuthRepoImple"

    override suspend fun isUserAvailable(): Boolean {
        return sessionManager.isUserAvailable()
    }

    override suspend fun login(authorizationCode: String): Boolean {

        val tokens = dataSource.getTokens(authorizationCode)
        var loggedCorrectly: Boolean = false
        tokens?.let { it ->
            Log.d(TAG, "Got Access token ${it.accessToken}")

            sessionManager.saveAccessToken(it.accessToken)
            it.refreshToken?.let {
                sessionManager.saveRefreshToken(it)
            }

            loggedCorrectly = true
        }

        return loggedCorrectly
    }

    override suspend fun logout() {
        sessionManager.clearAccessToken()
        sessionManager.clearRefreshToken()
    }
}