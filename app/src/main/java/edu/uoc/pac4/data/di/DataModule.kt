package edu.uoc.pac4.data.di

import edu.uoc.pac4.data.SessionManager
import edu.uoc.pac4.data.network.Network
import edu.uoc.pac4.data.oauth.AuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthAuthenticationRepository
import edu.uoc.pac4.data.oauth.OAuthDataSource
import edu.uoc.pac4.data.streams.StreamsDataSource
import edu.uoc.pac4.data.streams.StreamsRepository
import edu.uoc.pac4.data.streams.TwitchStreamsRepository
import edu.uoc.pac4.data.user.TwitchUserRepository
import edu.uoc.pac4.data.user.UserDataSource
import edu.uoc.pac4.data.user.UserRepository
import edu.uoc.pac4.ui.LaunchViewModel
import edu.uoc.pac4.ui.profile.ProfileViewModel
import edu.uoc.pac4.ui.streams.StreamViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by alex on 11/21/20.
 */

val dataModule = module {
    single<AuthenticationRepository> { OAuthAuthenticationRepository(OAuthDataSource(Network.createHttpClient(androidContext())), SessionManager(androidContext())) }
    single<StreamsRepository> { TwitchStreamsRepository(StreamsDataSource(Network.createHttpClient(androidContext()))) }
    single<UserRepository> { TwitchUserRepository(UserDataSource(Network.createHttpClient(androidContext()))) }

    viewModel { LaunchViewModel(get()) }
    viewModel { StreamViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}