
package di


import org.koin.dsl.module
import repository.HomeRepository
import root.DefaultRootComponent
import root.RootComponent
import viewmodel.HomeViewModel

fun commonModule() = networkModule() + module {

    single {
        HomeRepository(get())
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
