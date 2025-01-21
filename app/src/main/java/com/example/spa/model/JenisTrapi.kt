package com.example.spa.model

import kotlinx.serialization.Serializable

@Serializable
data class AllJenisTrapiResponse(
    val status: Boolean,
    val message: String,
    val data: List<JenisTrapi>
)

@Serializable
data class JenisTrapiDetailResponse(
    val status: Boolean,
    val message: String,
    val data: List<JenisTrapi>
)

@Serializable
data class JenisTrapi(
    val id_jenisTrapi: String,
    val nama_jenisTrapi: String,
    val deskripsi_jenisTrapi: String
)