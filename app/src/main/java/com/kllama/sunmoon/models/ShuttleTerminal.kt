package com.kllama.sunmoon.models

sealed class ShuttleTerminal

data class ShuttleTerminalWeekday(
        val no: String,
        val asanCampus: String,
        val terminal: String,
        val hanjeon: String,
        val asanCampus2: String
) : ShuttleTerminal()

data class ShuttleTerminalWeekend(
        val no: String,
        val asanCampus: String,
        val terminal: String,
        val asanCampus2: String
) : ShuttleTerminal()