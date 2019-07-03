package ru.skillbranch.devintensive.extensions

fun String.truncate(endIndex: Int = 16): String {
    val result = this.trimEnd()
    return if (endIndex + 1 >= result.length)
        result
    else
        result.substring(0, endIndex + 1).trimEnd() + "..."
}

fun String.removeDuplicateSpaces(): String =
    Regex(" ++").replace(this, " ")

fun String.removeTags(): String =
    Regex("<.*?>").replace(this, "")

fun String.removeHtmlSpecialChars(): String =
    this.replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&apos;", "'")

fun String.stripHtml(): String =
    this.removeTags()
        .removeHtmlSpecialChars()
        .removeDuplicateSpaces()
