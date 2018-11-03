package com.kllama.sunmoon.models

sealed class Shuttle

data class ShuttleTrainWeekday(
        val no: String,
        val asanCampus: String,
        val asanStation: String,
        val chenanStation: String,
        val yongamTown: String,
        val asanStation2: String,
        val asanCampus2: String
) : Shuttle() {
    constructor() : this("", "", "", "", "", "", "")
}

data class ShuttleTrainWeekend(
        val no: String,
        val asanCampus: String,
        val asanStation: String,
        val chenanStation: String,
        val asanStation2: String,
        val asanCampus2: String
) : Shuttle() {
    constructor() : this("", "", "", "", "", "")
}

data class ShuttleTerminalWeekday(
        val no: String,
        val asanCampus: String,
        val terminal: String,
        val hanjeon: String,
        val asanCampus2: String
) : Shuttle() {
    constructor() : this("", "", "", "", "")
}

data class ShuttleTerminalWeekend(
        val no: String,
        val asanCampus: String,
        val terminal: String,
        val asanCampus2: String
) : Shuttle() {
    constructor() : this("", "", "", "")
}

data class ShuttleOnyang(
        val no: String,
        val asanCampus: String,
        val baebang: String,
        val terminal: String,
        val onyangStation: String,
        val asanCampus2: String
) : Shuttle() {
    constructor() : this("", "", "", "", "", "")
}