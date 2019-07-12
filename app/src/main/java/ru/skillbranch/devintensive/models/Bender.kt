package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {

    fun askQuestion() = question.question

    private fun positiveAnswer(): String {
        question = question.nextQuestion()
        return "Отлично - ты справился"
    }

    private fun negativeAnswer(): String {
        return if (status == Status.CRITICAL) {
            status = Status.NORMAL
            question = Question.NAME
            "Это неправильный ответ. Давай все по новой"
        } else {
            status = status.nextStatus()
            "Это неправильный ответ"
        }
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if (question == Question.IDLE)
            return question.question to status.color

        val verdict: String =
            if (!question.isAnswerValid(answer)) {
                question.invalidAnswer()
            } else if (question.isAnswerCorrect(answer)) {
                positiveAnswer()
            } else {
                negativeAnswer()
            }
        return "$verdict\n${question.question}" to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status =
            if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else
                values()[0]
    }

    enum class Question(
        val question: String,
        val answers: List<String>
    ) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question =
                PROFESSION

            override fun isAnswerValid(answer: String): Boolean =
                (answer.isNotEmpty()) && (answer[0].isUpperCase())

            override fun invalidAnswer(): String =
                "Имя должно начинаться с заглавной буквы"
        },

        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question =
                MATERIAL

            override fun isAnswerValid(answer: String): Boolean =
                (answer.isNotEmpty()) && (answer[0].isLowerCase())

            override fun invalidAnswer(): String =
                "Профессия должна начинаться со строчной буквы"
        },

        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question =
                BDAY

            override fun isAnswerValid(answer: String): Boolean =
                answer.all { !it.isDigit() }

            override fun invalidAnswer(): String =
                "Материал не должен содержать цифр"
        },

        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question =
                SERIAL

            override fun isAnswerValid(answer: String): Boolean =
                answer.all { it.isDigit() }

            override fun invalidAnswer(): String =
                "Год моего рождения должен содержать только цифры"
        },

        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question =
                IDLE

            override fun isAnswerValid(answer: String): Boolean =
                (answer.length == 7) && answer.all { it.isDigit() }

            override fun invalidAnswer(): String =
                "Серийный номер содержит только цифры, и их 7"
        },

        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question =
                IDLE

            override fun isAnswerValid(answer: String): Boolean =
                true

            override fun invalidAnswer(): String =
                ""
        };

        abstract fun nextQuestion(): Question
        abstract fun isAnswerValid(answer: String): Boolean
        abstract fun invalidAnswer(): String
        fun isAnswerCorrect(answer: String): Boolean =
            answers.contains(answer.toLowerCase())
    }
}