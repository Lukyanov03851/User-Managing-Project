package ua.lukyanov.usermanaging.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.lukyanov.usermanaging.ui.auth.AuthActivity;

@Module
public abstract class AuthActivityModule {

    @ContributesAndroidInjector(modules = {LoginModule.class, RegistrationModule.class})
    abstract AuthActivity bindAuthActivity();

}
