package com.capstone.maternalcare.util


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.ui.home.HomeViewModel
import com.capstone.maternalcare.ui.login.LoginViewModel
import com.capstone.maternalcare.ui.profil.ProfilViewModel
import com.capstone.maternalcare.ui.profil.edit.EditProfilViewModel
import com.capstone.maternalcare.ui.profil.settings.SettingsViewModel
import com.capstone.maternalcare.ui.recomendations.ArticlesViewModel
import com.capstone.maternalcare.ui.register.RegisterViewModel
import com.capstone.maternalcare.ui.register.RegisterViewModel2


class ViewModelFactory(private val pref: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            //modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
               // SplashScreenViewModel(pref) as T
            //}

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }


            modelClass.isAssignableFrom(ArticlesViewModel::class.java) -> {
                ArticlesViewModel() as T
            }

            modelClass.isAssignableFrom(ProfilViewModel::class.java) -> {
                ProfilViewModel(pref) as T
            }

            modelClass.isAssignableFrom(EditProfilViewModel::class.java) -> {
                EditProfilViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel2::class.java) -> {
                RegisterViewModel2(pref) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }


}