package di

import database.Database
import database.DbHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

fun cacheModule() = module {

    single<CoroutineContext> { Dispatchers.Default }
    single { CoroutineScope(get()) }

    single { DbHelper(get()) }
    single { Database(get(), get()) }
}