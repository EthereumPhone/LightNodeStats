package org.ethereumphone.lightnodestats.logic

import android.content.Context
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
import org.ethereumphone.lightnodestats.data.EthHttpService
import org.ethereumphone.lightnodestats.data.NodeStatsFetcher
import org.ethereumphone.lightnodestats.data.toGwei
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.core.methods.response.admin.AdminPeers
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
        var isOnline: Boolean = false,
        var canGetBlocks: Boolean = false,
    ) : BlockState

    sealed class Event : BlockEvent {
        data class PeersUpdated(val peers: List<AdminPeers.Peer>) : Event()
        data class BlockUpdated(val block: EthBlock.Block) : Event()
        data class GasPriceUpdated(val gasPrice: BigInteger) : Event()
        data class IsOnline(val isOnline: Boolean) : Event()
        data class CanGetBlocks(val canGetBlocks: Boolean) : Event()
    }

    val cls = Class.forName("android.os.GethProxy")

    lateinit var context: Context
    lateinit var gethservice: Any
    lateinit var blockList: List<EthBlock.Block>
    val isRunning = cls.getMethod("isRunning")

    fun pushContext(context: Context) {
        this.context = context
        gethservice = context.getSystemService("geth")
        processEvent(Event.IsOnline(isRunning.invoke(gethservice) as Boolean))
    }

    init {
        observeEvents()
            .scan(State()) { state, event ->
                when (event) {
                    is Event.BlockUpdated -> state.apply {
                        if (blocks.size > 0) {
                            if (blocks.get(blocks.size - 1).number != event.block.number) {
                                if (blocks.size>=10) {
                                    blocks.removeAt(0)
                                }
                                blocks.add(event.block)
                            }
                        } else {
                            if (blocks.size>=10) {
                                blocks.removeAt(0)
                            }
                            blocks.add(event.block)
                        }

                    }
                    is Event.PeersUpdated -> state.copy(peerCount = event.peers.size)
                    is Event.GasPriceUpdated -> state.copy(gasPrice = event.gasPrice.toGwei())
                    is Event.IsOnline -> state.copy(isOnline = event.isOnline)
                    is Event.CanGetBlocks -> state.copy(canGetBlocks = event.canGetBlocks)
                }
            }.onEach { pushState(it) }
            .launchInBlock()

        val web3j = Web3j.build(EthHttpService("http://127.0.0.1:8545"))


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

        fun isOnlineUsingChainID() : Boolean {
            return try{
                val chainId = web3j.ethChainId().sendAsync().get().chainId
                true
            }catch (e: Exception) {
                false
            }
        }
        /**
         Gas price is not supported
        nodeStatsFetcher.fetchGasPrice()
            .onEach { data ->
                println("Maybe error message: "+data.error?.message)
                processEvent(Event.GasPriceUpdated(data.gasPrice))
                processEvent(Event.IsOnline(true))
            }.catch { err ->
                Log.e(TAG, "error", err)
                when(err) {
                    is ConnectException -> {
                        // No connection, probably down
                        processEvent(Event.IsOnline(isRunning.invoke(gethservice) as Boolean))
                    }
                }
            }
            .launchInBlock()
        */

        // Getting newest block
        // PoS Ethereum has blocks every 12 seconds
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                // Get block number
                try {
                    web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false).sendAsync().whenComplete { ethBlock, throwable ->
                        if (ethBlock == null) {
                            Log.e(TAG, "Error getting block", throwable)
                            processEvent(Event.IsOnline(isRunning.invoke(gethservice) as Boolean))
                            return@whenComplete
                        }

                        processEvent(Event.BlockUpdated(ethBlock.block))
                        processEvent(Event.IsOnline(isRunning.invoke(gethservice) as Boolean))
                        processEvent(Event.CanGetBlocks(true))
                        /*
                        nodeStatsFetcher.fetchGasPrice()
                            .onEach { data ->
                                processEvent(Event.GasPriceUpdated(data.gasPrice))
                                processEvent(Event.IsOnline(true))
                            }.catch { err ->
                                Log.e(TAG, "error", err)
                                when(err) {
                                    is ConnectException -> {
                                        // No connection, probably down
                                        processEvent(Event.IsOnline(isRunning.invoke(gethservice) as Boolean))
                                        processEvent(Event.CanGetBlocks(false))
                                    }
                                }
                            }
                            .launchInBlock()
                            */
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    processEvent(Event.IsOnline(isRunning.invoke(gethservice) as Boolean))
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