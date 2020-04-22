package ua.lukyanov.usermanaging.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import ua.lukyanov.usermanaging.di.util.DaggerViewModelFactory;
import ua.lukyanov.usermanaging.di.util.ViewModelKey;
import ua.lukyanov.usermanaging.ui.auth.login.LoginFragment;
import ua.lukyanov.usermanaging.ui.auth.login.LoginViewModel;

@Module
abstract class LoginModule {

    @ContributesAndroidInjector(modules = {ProvideViewModel.class})
    abstract LoginFragment bindLoginFragment();

    @Module
    abstract class ProvideViewModel {

        @Binds
        abstract ViewModelProvider.Factory bindDaggerViewModelFactory(DaggerViewModelFactory daggerViewModelFactory);

        @Binds
        @IntoMap
        @ViewModelKey(LoginViewModel.class)
        abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    }

}
