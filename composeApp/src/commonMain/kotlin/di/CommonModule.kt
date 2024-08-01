package di


import database.datasource.GamesLocalDataSource
import database.datasource.GamesRemoteDataSource
import org.koin.dsl.module
import repository.HomeRepository
import root.DefaultRootComponent
import root.RootComponent
import viewmodel.HomeViewModel

fun commonModule() = cacheModule() + networkModule() + module {

    single {
        GamesLocalDataSource(get())
    }

    single {
        GamesRemoteDataSource(get())
    }

    single {
        HomeRepository(get(), get())
    }

    single {
        HomeViewModel(get())
    }

    single<RootComponent> {
        DefaultRootComponent(
            componentContext = get(),
            homeViewModel = get()
        )
    }

}