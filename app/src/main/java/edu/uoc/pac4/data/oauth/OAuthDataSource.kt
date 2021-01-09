package edu.uoc.pac4.data.oauth

import android.util.Log
import edu.uoc.pac4.data.network.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class OAuthDataSource(
        private val httpClient: HttpClient
) {
    private val TAG = "OAuthDataSource"

    /// Gets Access and Refresh Tokens on Twitch
    suspend fun getTokens(authorizationCode: String): OAuthTokensResponse? {
        // Get Tokens from Twitch
        return try {

            httpClient
                    .post<OAuthTokensResponse>(Endpoints.tokenUrl) {
                        parameter("client_id", OAuthConstants.clientID)
                        parameter("client_secret", OAuthConstants.clientSecret)
                        parameter("code", authorizationCode)
                        parameter("grant_type", "authorization_code")
                        parameter("redirect_uri", OAuthConstants.redirectUri)
                    }

        } catch (t: Throwable) {
            Log.w(TAG, "Error Getting Access token", t)
            null
        }
    }

}