package ua.lukyanov.usermanaging.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;
import ua.lukyanov.usermanaging.di.util.DaggerViewModelFactory;
import ua.lukyanov.usermanaging.di.util.ViewModelKey;
import ua.lukyanov.usermanaging.ui.profile.ProfileFragment;
import ua.lukyanov.usermanaging.ui.profile.ProfileViewModel;

@Module
abstract class ProfileModule {

    @ContributesAndroidInjector(modules = {ProvideViewModel.class})
    abstract ProfileFragment bindProfileFragment();

    @Module
    abstract class ProvideViewModel {

        @Binds
        abstract ViewModelProvider.Factory bindDaggerViewModelFactory(DaggerViewModelFactory daggerViewModelFactory);

        @Binds
        @IntoMap
        @ViewModelKey(ProfileViewModel.class)
        abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    }

}
