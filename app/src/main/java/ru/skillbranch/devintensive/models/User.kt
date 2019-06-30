package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    val isOnline: Boolean = false
) {
    var introBit: String

    constructor(id: String, firstName: String?, lastName: String?) : this(id, firstName, lastName, avatar = null)

    constructor(id: String) : this(id, "John", "Doe")

    init {
        introBit = getIntro()
        println(
            "It's Alive!!!\n" +
                    "${if (lastName === "Doe") "His" else "And his"} name is $firstName $lastName\n"
        )
    }

    private fun getIntro() = """
        tu tu ru tuuuuu !!!
        tu tu ru tuuuuuuuuu ...

        tu tu ru tuuuuu !!!
        tu tu ru tuuuuuuuuu ...
        ${"\n\n\n"}
        $firstName $lastName
    """.trimIndent()

    fun printMe() = println(
        """
        id: $id
        firstName: $firstName
        lastName: $lastName
        avatar: $avatar
        rating: $rating
        respect: $respect
        lastVisit: $lastVisit
        isOnline: $isOnline

        """.trimIndent()
    )

    companion object Factory {
        private var nextId = 0
        fun makeUser(fullName: String?): User {
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "${nextId++}", firstName = firstName ?: "John", lastName = lastName ?: "Doe")
        }
    }

    class Builder() {
        private var id: String = "${nextId++}"
        private var firstName: String? = null
        private var lastName: String? = null
        private var avatar: String? = null
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date = Date()
        private var isOnline: Boolean = false

        fun id(id: String) = apply { this.id = id }
        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun avatar(avatar: String) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

        fun build(): User =
            User(
                id,
                firstName,
                lastName,
                avatar,
                rating,
                respect,
                lastVisit,
                isOnline
            )
    }
}