package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String {
    val result = this.trimEnd()
    if (length >= result.length)
        return result

    var lastIndex = length-1
    while ((lastIndex >= 0) && (result[lastIndex] == ' '))
        --lastIndex
    return this.substring(0, lastIndex+1) + "..."
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
