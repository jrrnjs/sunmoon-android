package com.kllama.sunmoon.parser

import com.kllama.sunmoon.models.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class ShuttleParser {

    fun parse(type: ShuttleType): Any {
        val document = Jsoup.connect(getShuttleUrl(type))
                .timeout(5000)
                .get()

        val tbody = document.getElementsByClass("__se_tbl_ext")[0]
                .getElementsByTag("tbody")

        return when (type) {
            ShuttleType.WEEKDAY_TRAIN -> parseTrainWeekday(tbody)
            ShuttleType.WEEKEND_TRAIN, ShuttleType.SUNDAY_TRAIN -> parseTrainWeekend(tbody)
            ShuttleType.WEEKDAY_TERMINAL -> parseTerminalWeekday(tbody)
            ShuttleType.WEEKEND_TERMINAL, ShuttleType.SUNDAY_TERMINAL -> parseTerminalWeekend(tbody)
            ShuttleType.WEEKDAY_ONYANG -> parseOnyangWeekday(tbody)
        }
    }

    private fun parseTrainWeekday(tbody: Elements): MutableList<ShuttleTrain> {
        val result = mutableListOf<ShuttleTrain>()

        tbody.forEach { it ->
            it.getElementsByTag("tr").forEach { tr ->
                val tds = tr.getElementsByTag("td")

                if (tds.size == 6) {
                    val no = tr.getElementsByTag("th")[0].text()

                    val asanCampus = tds[0].text()
                    val asanStation = tds[1].text()
                    val chenanStation = tds[2].text()
                    val yongamTown = tds[3].text()
                    val asanStation2 = tds[4].text()
                    val asanCampus2 = tds[5].text()

                    result.add(ShuttleTrainWeekday(no, asanCampus, asanStation, chenanStation, yongamTown, asanStation2, asanCampus2))
                }
            }
        }
        return result
    }
    private fun parseTrainWeekend(tbody: Elements): MutableList<ShuttleTrain> {
        val result = mutableListOf<ShuttleTrain>()

        tbody.forEach { it ->
            it.getElementsByTag("tr").forEach { tr ->
                val tds = tr.getElementsByTag("td")

                if (tds.size == 5) {
                    val no = tr.getElementsByTag("th")[0].text()

                    val asanCampus = tds[0].text()
                    val asanStation = tds[1].text()
                    val chenanStation = tds[2].text()
                    val asanStation2 = tds[3].text()
                    val asanCampus2 = tds[4].text()

                    result.add(ShuttleTrainWeekend(no, asanCampus, asanStation, chenanStation, asanStation2, asanCampus2))
                }
            }
        }
        return result
    }

    private fun parseTerminalWeekday(tbody: Elements): MutableList<ShuttleTerminal> {
        val result = mutableListOf<ShuttleTerminal>()

        tbody.forEach { it ->
            it.getElementsByTag("tr").forEach { tr ->
                val tds = tr.getElementsByTag("td")

                if (tds.size == 4) {
                    val no = tr.getElementsByTag("th")[0].text()

                    val asanCampus = tds[0].text()
                    val terminal = tds[1].text()
                    val hanjeon = tds[2].text()
                    val asanCampus2 = tds[3].text()

                    result.add(ShuttleTerminalWeekday(no, asanCampus, terminal, hanjeon, asanCampus2))
                }
            }
        }
        return result
    }
    private fun parseTerminalWeekend(tbody: Elements): MutableList<ShuttleTerminal> {
        val result = mutableListOf<ShuttleTerminal>()

        tbody.forEach { it ->
            it.getElementsByTag("tr").forEach { tr ->
                val tds = tr.getElementsByTag("td")

                if (tds.size == 3) {
                    val no = tr.getElementsByTag("th")[0].text()

                    val asanCampus = tds[0].text()
                    val terminal = tds[1].text()
                    val asanCampus2 = tds[2].text()

                    result.add(ShuttleTerminalWeekend(no, asanCampus, terminal, asanCampus2))
                }
            }
        }
        return result
    }

    private fun parseOnyangWeekday(tbody: Elements): MutableList<ShuttleOnyang> {
        val result = mutableListOf<ShuttleOnyang>()

        tbody.forEach { it ->
            it.getElementsByTag("tr").forEach { tr ->
                val tds = tr.getElementsByTag("td")

                if (tds.size == 5) {
                    val no = tr.getElementsByTag("th")[0].text()

                    val asanCampus = tds[0].text()
                    val baebang = tds[1].text()
                    val terminal = tds[2].text()
                    val onyangStation = tds[3].text()
                    val asanCampus2 = tds[4].text()

                    result.add(ShuttleOnyang(no, asanCampus, baebang, terminal, onyangStation, asanCampus2))
                }
            }
        }
        return result
    }


    private fun getShuttleUrl(type: ShuttleType): String =
            when (type) {
                ShuttleType.WEEKDAY_TRAIN -> ShuttleParser.WEEKDAY_SHUTTLE_URLS[0]
                ShuttleType.WEEKDAY_TERMINAL -> ShuttleParser.WEEKDAY_SHUTTLE_URLS[1]
                ShuttleType.WEEKDAY_ONYANG -> ShuttleParser.WEEKDAY_SHUTTLE_URLS[2]

                ShuttleType.WEEKEND_TRAIN -> ShuttleParser.WEEKEND_SHUTTLE_URLS[0]
                ShuttleType.WEEKEND_TERMINAL -> ShuttleParser.WEEKEND_SHUTTLE_URLS[1]

                ShuttleType.SUNDAY_TRAIN -> ShuttleParser.SUNDAY_SHUTTLE_URLS[0]
                ShuttleType.SUNDAY_TERMINAL -> ShuttleParser.SUNDAY_SHUTTLE_URLS[1]
            }


    companion object {
        val WEEKDAY_SHUTTLE_URLS = mutableListOf(
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_01_01.aspx", //천안, 아산역
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_01_02.aspx", //천안터미널
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_01_03.aspx"  //온양
        )

        val WEEKEND_SHUTTLE_URLS = mutableListOf(
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_02_01.aspx", //천안, 아산역
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_02_02.aspx"  //천안터미널
        )

        val SUNDAY_SHUTTLE_URLS = mutableListOf(
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_03_01.aspx", //천안, 아산역
                "http://lily.sunmoon.ac.kr/Page/About/About08_04_02_03_02.aspx"  //천안터미널
        )
    }
}