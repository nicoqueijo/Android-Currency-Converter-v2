package com.nicoqueijo.android.data

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.network.ApiOperation
import com.nicoqueijo.android.network.OpenExchangeRatesEndPoint
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getExchangeRates(): ApiOperation<OpenExchangeRatesEndPoint>
    suspend fun upsertCurrency(currency: Currency)
    suspend fun upsertCurrencies(currencies: List<Currency>)
    suspend fun updateExchangeRates(currencies: List<Currency>)
    suspend fun getCurrency(currencyCode: String): Currency
    suspend fun getAllCurrencies(): Flow<MutableList<Currency>>
    suspend fun getSelectedCurrencies(): Flow<MutableList<Currency>>
    suspend fun setFirstLaunch(value: Boolean)
    suspend fun isFirstLaunch(): Boolean
    suspend fun setTimestampInSeconds(value: Long)
    suspend fun getTimestampInSeconds(): Long
    suspend fun isDataEmpty(): Boolean
    suspend fun isDataStale(): Boolean
}