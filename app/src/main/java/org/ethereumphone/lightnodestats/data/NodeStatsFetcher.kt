package org.ethereumphone.lightnodestats.data

import com.joaquimverges.helium.core.Background
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

object NodeStatsFetcher {

    // TODO probably need to change port here
    private val web3j = Web3j.build(HttpService("http://127.0.0.1:8545"));

    fun fetchPeers() = web3j.adminPeers().flowable().asFlow().flowOn(Background)
    fun fetchNodeInfo() = web3j.adminNodeInfo().flowable().asFlow().flowOn(Background)
    fun subscribeToBlockHashes() = web3j.ethBlockHashFlowable().asFlow().flowOn(Background)
    fun subscribeToBlocks() = web3j.blockFlowable(false).asFlow().flowOn(Background)
}