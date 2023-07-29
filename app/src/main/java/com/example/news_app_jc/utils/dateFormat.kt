package com.example.news_app_jc.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDate(inputDateStr: String): String {
    val date = inputDateStr.substring(0, inputDateStr.length - 10)

    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormat = DateTimeFormatter.ofPattern("dd MMMM")

    val inputDate = LocalDate.parse(date, inputFormat)

    return inputDate.format(outputFormat)
}