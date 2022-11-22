package org.ethereumphone.lightnodestats.logic

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.joaquimverges.helium.core.LogicBlock
import com.joaquimverges.helium.core.event.BlockEvent
import com.joaquimverges.helium.core.state.BlockState
import kotlinx.coroutines.flow.*
import org.ethereumphone.lightnodestats.TAG
import org.ethereumphone.lightnodestats.data.NodeStatsFetcher
import org.ethereumphone.lightnodestats.data.toGwei
import org.web3j.abi.datatypes.Bool
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.core.methods.response.EthGasPrice
import org.web3j.protocol.core.methods.response.admin.AdminPeers
import org.web3j.protocol.http.HttpService
import java.math.BigInteger
import java.net.ConnectException

class StatsLogic(
    private val nodeStatsFetcher: NodeStatsFetcher = NodeStatsFetcher
) : LogicBlock<StatsLogic.State, StatsLogic.Event>() {

    // TODO Error state when not connected
    data class State(
        val peerCount: Int = 0,
        val gasPrice: Long = 0,
        val blocks: SnapshotStateList<EthBlock.Block> = mutableStateListOf(),
        var isOnline: Boolean = false
    ) : BlockState

    sealed class Event : BlockEvent {
        data class PeersUpdated(val peers: List<AdminPeers.Peer>) : Event()
        data class BlockUpdated(val block: EthBlock.Block) : Event()
        data class GasPriceUpdated(val gasPrice: BigInteger) : Event()
        data class IsOnline(val isOnline: Boolean) : Event()
    }

    init {
        observeEvents()
            .scan(State()) { state, event ->
                when (event) {
                    is Event.BlockUpdated -> state.apply { blocks.add(event.block) }
                    is Event.PeersUpdated -> state.copy(peerCount = event.peers.size)
                    is Event.GasPriceUpdated -> state.copy(gasPrice = event.gasPrice.toGwei())
                    is Event.IsOnline -> state.copy(isOnline = event.isOnline)
                }
            }.onEach { pushState(it) }
            .launchInBlock()

        val web3j = Web3j.build(HttpService())
        try {
            println("Get chain id: "+web3j.ethChainId().sendAsync().get().chainId.toString())
            processEvent(Event.IsOnline(true))
        } catch (e: Exception) {
            processEvent(Event.IsOnline(false))
        }

        /*
        nodeStatsFetcher.fetchPeers()
            .onEach { data ->
                processEvent(Event.PeersUpdated(data.result ?: listOf()))
                processEvent(Event.IsOnline(true))
            }.catch { err ->
                Log.e(TAG, "error", err)
                when(err) {
                    is ConnectException -> {
                        // No connection, probably down
                        processEvent(Event.IsOnline(false))
                    }
                }
            }
            .launchInBlock()
         */

        nodeStatsFetcher.fetchGasPrice()
            .onEach { data ->
                processEvent(Event.GasPriceUpdated(data.gasPrice))
                processEvent(Event.IsOnline(true))
            }.catch { err ->
                Log.e(TAG, "error", err)
                when(err) {
                    is ConnectException -> {
                        // No connection, probably down
                        processEvent(Event.IsOnline(false))
                    }
                }
            }
            .launchInBlock()

        // Getting newest block
        // PoS Ethereum has blocks every 12 seconds
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                // Get block number
                try {
                    val newestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).sendAsync().get().block
                    processEvent(Event.BlockUpdated(newestBlock))
                    processEvent(Event.IsOnline(true))
                    nodeStatsFetcher.fetchGasPrice()
                        .onEach { data ->
                            processEvent(Event.GasPriceUpdated(data.gasPrice))
                            processEvent(Event.IsOnline(true))
                        }.catch { err ->
                            Log.e(TAG, "error", err)
                            when(err) {
                                is ConnectException -> {
                                    // No connection, probably down
                                    processEvent(Event.IsOnline(false))
                                }
                            }
                        }
                        .launchInBlock()
                } catch (e: Exception) {
                    e.printStackTrace()
                    processEvent(Event.IsOnline(false))
                }
                mainHandler.postDelayed(this, 13000)

            }
        })
        /*
        nodeStatsFetcher.observeBlocks()
            .onEach { data ->
                data.block?.let {
                    processEvent(Event.BlockUpdated(it))
                    processEvent(Event.IsOnline(true))
                }
            }.flatMapConcat {
                nodeStatsFetcher.fetchGasPrice()
            }.onEach { data ->
                processEvent(Event.GasPriceUpdated(data.gasPrice))
            }
            .catch { err ->
                Log.e(TAG, "Block error:", err)
                when(err) {
                    is ConnectException -> {
                        // No connection, probably down
                        processEvent(Event.IsOnline(false))
                    }
                }
            }
            .launchInBlock()
         */

    }

    override fun onUiEvent(event: Event) {
        // no-op for now
    }

    override fun onCleared() {
        super.onCleared()
        nodeStatsFetcher.shutDown()
    }
}