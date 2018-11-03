package com.kllama.sunmoon.core.parser

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
            ShuttleType.TRAIN_WEEKDAY -> parseTrainWeekday(tbody)
            ShuttleType.TRAIN_SATURDAY, ShuttleType.TRAIN_SUNDAY -> parseTrainWeekend(tbody)
            ShuttleType.TERMINAL_WEEKDAY -> parseTerminalWeekday(tbody)
            ShuttleType.TERMINAL_SATURDAY, ShuttleType.TERMINAL_SUNDAY -> parseTerminalWeekend(tbody)
            ShuttleType.ONYANG_WEEKDAY -> parseOnyangWeekday(tbody)
        }
    }

    private fun parseTrainWeekday(tbody: Elements): MutableList<Shuttle> {
        val result = mutableListOf<Shuttle>()

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
    private fun parseTrainWeekend(tbody: Elements): MutableList<Shuttle> {
        val result = mutableListOf<Shuttle>()

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

    private fun parseTerminalWeekday(tbody: Elements): MutableList<Shuttle> {
        val result = mutableListOf<Shuttle>()

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
    private fun parseTerminalWeekend(tbody: Elements): MutableList<Shuttle> {
        val result = mutableListOf<Shuttle>()

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
                ShuttleType.TRAIN_WEEKDAY -> ShuttleParser.WEEKDAY_SHUTTLE_URLS[0]
                ShuttleType.TERMINAL_WEEKDAY -> ShuttleParser.WEEKDAY_SHUTTLE_URLS[1]
                ShuttleType.ONYANG_WEEKDAY -> ShuttleParser.WEEKDAY_SHUTTLE_URLS[2]

                ShuttleType.TRAIN_SATURDAY -> ShuttleParser.WEEKEND_SHUTTLE_URLS[0]
                ShuttleType.TERMINAL_SATURDAY -> ShuttleParser.WEEKEND_SHUTTLE_URLS[1]

                ShuttleType.TRAIN_SUNDAY -> ShuttleParser.SUNDAY_SHUTTLE_URLS[0]
                ShuttleType.TERMINAL_SUNDAY -> ShuttleParser.SUNDAY_SHUTTLE_URLS[1]
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