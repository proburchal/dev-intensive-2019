package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        if (firstName == "")
            firstName = null
        if (lastName == "")
            lastName = null
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val lower: HashMap<Char, String> = hashMapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
        val upper: HashMap<Char, String> = hashMapOf()
        lower.forEach {
            upper[it.key.toUpperCase()] =
                if (it.value == "") "" else it.value[0].toUpperCase() + it.value.substring(1)
        }
        lower[' '] = divider

        var result = ""
        for (char in payload) {
            result += lower[char] ?: upper[char] ?: char
        }
        return result
    }

    private fun toInitial(name: String?): String? =
        if (name.isNullOrBlank()) {
            ""
        } else {
            name[0].toString().toUpperCase()
        }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val result = toInitial(firstName) + toInitial(lastName)
        return if (result.isEmpty())
            null
        else
            result
    }

    fun inclineAfterNumeral(numeral: Long, forms: Triple<String, String, String>): String {
        val tens = numeral % 100 / 10
        val ones = numeral % 10
        return when {
            tens == 1L || ones == 0L || ones >= 5L -> forms.third
            ones == 1L -> forms.first
            else -> forms.second
        }
    }
}