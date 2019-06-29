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
        private var lastId = -1
        fun makeUser(fullName: String?): User {
            ++lastId
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = firstName ?: "John", lastName = lastName ?: "Doe")
        }
    }

    class Builder(
        var id: String? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var avatar: String? = null,
        var rating: Int? = null,
        var respect: Int? = null,
        var lastVisit: Date? = null,
        var isOnline: Boolean? = null
    ) {
        fun id(id: String? = null) = apply { this.id = id }
        fun firstName(firstName: String? = null) = apply { this.firstName = firstName }
        fun lastName(lastName: String? = null) = apply { this.lastName = lastName }
        fun avatar(avatar: String? = null) = apply { this.avatar = avatar }
        fun rating(rating: Int? = null) = apply { this.rating = rating }
        fun respect(respect: Int? = null) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date? = null) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean? = null) = apply { this.isOnline = isOnline }

        fun build(): User =
            User(
                id!!,
                firstName,
                lastName,
                avatar,
                rating ?: 0,
                respect ?: 0,
                lastVisit ?: Date(),
                isOnline ?: false
            )
    }
}