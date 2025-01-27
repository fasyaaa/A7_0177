package com.example.spa.ui.sesi.update.page

import com.example.spa.ui.sesi.SesiPenyediaViewModel
import com.example.spa.ui.sesi.insert.page.EntryBodySesi
import com.example.spa.ui.sesi.update.viewmodel.SesiUpdateViewModel
import CoustumeTopAppBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateSs: DestinasiNavigasi{
    override val route = "update"
    override val titleRes = "Update Sesi"
    const val idSesi = "id_sesi"
    val routesWithArg = "$route/{$idSesi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateSsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: SesiUpdateViewModel = viewModel(factory = SesiPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiUpdateSs.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodySesi(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.sesiUpdateUiState,
            onSesiValueChange = viewModel::updateInsertSsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateSs()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}