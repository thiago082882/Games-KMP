package database

import app.cash.sqldelight.db.SqlDriver

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}