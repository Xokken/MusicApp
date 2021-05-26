package ru.itis.xokken.musicapp.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ru.itis.xokken.musicapp.R
import ru.itis.xokken.musicapp.presentation.adapter.SwipeSongAdapter
import ru.itis.xokken.musicapp.domain.exoplayer.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.multibindings.IntoMap
import kotlinx.coroutines.Dispatchers
import ru.itis.xokken.musicapp.data.repository.UserDatabase
import ru.itis.xokken.musicapp.domain.logincase.*
import ru.itis.xokken.musicapp.presentation.viewmodel.LoginViewModel
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMusicServiceConnection(
        @ApplicationContext context: Context
    ) = MusicServiceConnection(context)

    @Singleton
    @Provides
    fun provideSwipeSongAdapter() = SwipeSongAdapter()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

    @Singleton
    @Provides
    fun provideUserDatabase(mAuth: FirebaseAuth): UserDatabase = UserDatabase(mAuth)

    @Singleton
    @Provides
    fun provideSignUpCase(userDatabase: UserDatabase, context: CoroutineContext): SignUpCase
    = LoginCaseImpl(userDatabase, context)

    @Singleton
    @Provides
    fun provideCheckUser(userDatabase: UserDatabase, context: CoroutineContext): CheckCurrentUser
    = LoginCaseImpl(userDatabase, context)

    @Singleton
    @Provides
    fun provideSignInCase(userDatabase: UserDatabase, context: CoroutineContext): SignInCase
    = LoginCaseImpl(userDatabase, context)

    @Singleton
    @Provides
    fun provideLogOut(userDatabase: UserDatabase, context: CoroutineContext): LogOut
            = LoginCaseImpl(userDatabase, context)

    @Singleton
    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO



}