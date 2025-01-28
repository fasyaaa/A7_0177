package com.example.spa.ui.sesi.insert.page

import CoustumeTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spa.R
import com.example.spa.ui.customwidget.DynamicSelectedField
import com.example.spa.ui.navigation.DestinasiNavigasi
import com.example.spa.ui.sesi.SesiPenyediaViewModel
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertUiEvent
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertUiState
import com.example.spa.ui.sesi.insert.viewmodel.SesiInsertViewModel
import kotlinx.coroutines.launch

object DestinasiInsertSesiEntry: DestinasiNavigasi{
    override val route = "entry_sesi"
    override val titleRes = "Entry Sesi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrySsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SesiInsertViewModel = viewModel(factory = SesiPenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoustumeTopAppBar(
                title = DestinasiInsertSesiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        containerColor = colorResource(id = R.color.Background)
    ){innerPadding ->
        EntryBodySesi(
            insertUiState = viewModel.sesiUiState,
            onSesiValueChange = viewModel::updateInsertSsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertSs()
                    navigateBack()
                }
            },
            viewModel = viewModel,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodySesi(
    insertUiState: SesiInsertUiState,
    onSesiValueChange: (SesiInsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    viewModel: SesiInsertViewModel,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputSesi(
            insertUiEvent = insertUiState.sesiInsertUiEvent,
            onValueChange = onSesiValueChange,
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputSesi(
    insertUiEvent: SesiInsertUiEvent,
    onValueChange: (SesiInsertUiEvent) -> Unit,
    viewModel: SesiInsertViewModel,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val pasienList = viewModel.pasienList.map { it.namaPasien } // Ambil hanya nama pasien
    val terapisList = viewModel.terapisList.map { it.namaTerapis }
    val jenisTrapiList = viewModel.jenisTrapiList.map { it.namaJenisTrapi }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DynamicSelectedField(
            selectedValue = insertUiEvent.idPasien?.let { id ->
                viewModel.pasienList.find { it.idPasien == id }?.namaPasien ?: "Pilih Pasien"
            } ?: "Pilih Pasien",
            options = pasienList,
            label = "Pasien",
            placeholder = "Pilih Pasien",
            onValueChangedEvent = { selectedNama ->
                val selectedId = viewModel.pasienList.find { it.namaPasien == selectedNama }?.idPasien
                if (selectedId != null) {
                    onValueChange(insertUiEvent.copy(idPasien = selectedId))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectedField(
            selectedValue = insertUiEvent.idTerapis?.let { id ->
                viewModel.terapisList.find { it.idTerapis == id }?.namaTerapis ?: "Pilih Terapis"
            } ?: "Pilih Terapis",
            options = terapisList,
            label = "Terapis",
            placeholder = "Pilih Terapis",
            onValueChangedEvent = { selectedNama ->
                val selectedId = viewModel.terapisList.find { it.namaTerapis == selectedNama }?.idTerapis
                if (selectedId != null) {
                    onValueChange(insertUiEvent.copy(idTerapis = selectedId))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        DynamicSelectedField(
            selectedValue = insertUiEvent.idJenisTrapi?.let { id ->
                viewModel.jenisTrapiList.find { it.idJenisTrapi == id }?.namaJenisTrapi ?: "Pilih Jenis Terapi"
            } ?: "Pilih Jenis Terapi",
            options = jenisTrapiList,
            label = "Jenis Terapi",
            placeholder = "Pilih Jenis Terapi",
            onValueChangedEvent = { selectedNama ->
                val selectedId = viewModel.jenisTrapiList.find { it.namaJenisTrapi == selectedNama }?.idJenisTrapi
                if (selectedId != null) {
                    onValueChange(insertUiEvent.copy(idJenisTrapi = selectedId))
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertUiEvent.catatanSesi,
            onValueChange = { onValueChange(insertUiEvent.copy(catatanSesi = it)) },
            label = { Text("Catatan Sesi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.tanggalSesi,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalSesi = it)) },
            label = { Text("Tanggal") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
