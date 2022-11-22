package org.ethereumphone.lightnodestats.data

import com.joaquimverges.helium.core.Background
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import java.math.BigInteger
import kotlin.math.pow

object NodeStatsFetcher {

    // TODO probably need to change port here
    private val web3j = Web3j.build(HttpService("http://localhost:8545"));

    fun fetchPeers() = web3j.adminPeers().flowable().asFlow().flowOn(Background)
    fun fetchNodeInfo() = web3j.adminNodeInfo().flowable().asFlow().flowOn(Background)
    fun fetchGasPrice() = web3j.ethGasPrice().flowable().asFlow().flowOn(Background)

    fun observeBlocks() = web3j.blockFlowable(false).asFlow().flowOn(Background)

    fun shutDown() = web3j.shutdown()
}


fun BigInteger.toGwei(): Long {
    println("STATS_GWEI: $this")
    if (this == BigInteger.valueOf(1000000000)) {
        return 0
    }
    return (this.toLong() / 10.0.pow(9.0)).toLong()
}
fun BigInteger.toHumanNumber(): String {
    val n = this.toLong()
    return when {
        n > 1_000_000 -> "${n / 1_000_000}M"
        n > 1_000 -> "${n / 1_000}k"
        else -> "$n"
    }
}