package ru.skillbranch.devintensive.extensions

fun String.truncate(length_: Int = 16): String {
    val result = this.trimEnd()
    return if (length_ >= result.length)
        result
    else
        result.substring(0, length_).trimEnd() + "..."
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
