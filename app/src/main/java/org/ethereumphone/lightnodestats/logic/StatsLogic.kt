package org.ethereumphone.lightnodestats.logic

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.joaquimverges.helium.core.LogicBlock
import com.joaquimverges.helium.core.event.BlockEvent
import com.joaquimverges.helium.core.state.BlockState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import org.ethereumphone.lightnodestats.TAG
import org.ethereumphone.lightnodestats.data.NodeStatsFetcher
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.core.methods.response.admin.AdminPeers

class StatsLogic(
    private val nodeStatsFetcher: NodeStatsFetcher = NodeStatsFetcher
) : LogicBlock<StatsLogic.State, StatsLogic.Event>() {

    // TODO Error state when not connected
    data class State(
        val peerCount: Int = 0,
        val blocks: SnapshotStateList<EthBlock.Block> = mutableStateListOf()
    ) : BlockState

    sealed class Event : BlockEvent {
        data class PeersUpdated(val peers: List<AdminPeers.Peer>) : Event()
        data class BlockUpdated(val block: EthBlock.Block) : Event()
    }

    init {
        observeEvents().scan(State()) { state, event ->
            when (event) {
                is Event.BlockUpdated -> state.apply { blocks.add(event.block) }
                is Event.PeersUpdated -> state.copy(peerCount = event.peers.size)
            }
        }.onEach { pushState(it) }
            .launchInBlock()

        nodeStatsFetcher.fetchPeers().onEach { data ->
            processEvent(Event.PeersUpdated(data.result ?: listOf()))
        }.catch { err -> Log.e(TAG, "error", err) }
            .launchInBlock()


        nodeStatsFetcher.subscribeToBlocks().onEach { data ->
            data.block?.let {
                processEvent(Event.BlockUpdated(it))
            }
        }.catch { err -> Log.e(TAG, "error", err) }
            .launchInBlock()

    }

    override fun onUiEvent(event: Event) {

    }
}