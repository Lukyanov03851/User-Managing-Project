package ua.lukyanov.usermanaging.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import ua.lukyanov.usermanaging.App;
import ua.lukyanov.usermanaging.di.module.AuthActivityModule;
import ua.lukyanov.usermanaging.di.module.MainActivityModule;
import ua.lukyanov.usermanaging.di.module.NetworkModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, NetworkModule.class, AuthActivityModule.class, MainActivityModule.class})
public interface AppComponent extends AndroidInjector<App> {

@Component.Builder
interface Builder {
    @BindsInstance
    Builder application(Application application);

    AppComponent build();
}

    void inject(App app);
}