package ua.lukyanov.usermanaging.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import ua.lukyanov.usermanaging.di.util.DaggerViewModelFactory;
import ua.lukyanov.usermanaging.di.util.ViewModelKey;
import ua.lukyanov.usermanaging.ui.auth.registration.RegistrationFragment;
import ua.lukyanov.usermanaging.ui.auth.registration.RegistrationViewModel;

@Module
abstract class RegistrationModule {

    @ContributesAndroidInjector(modules = {ProvideViewModel.class})
    abstract RegistrationFragment bindRegistrationFragment();

    @Module
    abstract class ProvideViewModel {

        @Binds
        abstract ViewModelProvider.Factory bindDaggerViewModelFactory(DaggerViewModelFactory daggerViewModelFactory);

        @Binds
        @IntoMap
        @ViewModelKey(RegistrationViewModel.class)
        abstract ViewModel bindRegistrationViewModel(RegistrationViewModel loginViewModel);

    }

}