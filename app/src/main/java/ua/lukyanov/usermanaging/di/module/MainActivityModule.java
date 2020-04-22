package ua.lukyanov.usermanaging.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ua.lukyanov.usermanaging.ui.MainActivity;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = {ProfileModule.class})
    abstract MainActivity bindMainActivity();

}
