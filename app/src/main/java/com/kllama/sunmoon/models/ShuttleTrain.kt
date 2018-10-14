package com.kllama.sunmoon.models

sealed class ShuttleTrain

data class ShuttleTrainWeekday(
        val no: String,
        val asanCampus: String,
        val asanStation: String,
        val chenanStation: String,
        val yongamTown: String,
        val asanStation2: String,
        val asanCampus2: String
) : ShuttleTrain()

data class ShuttleTrainWeekend(
        val no: String,
        val asanCampus: String,
        val asanStation: String,
        val chenanStation: String,
        val asanStation2: String,
        val asanCampus2: String
) : ShuttleTrain()
