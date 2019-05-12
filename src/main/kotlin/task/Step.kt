package task

interface Step {
    val name: String
    val stepType: String
    /**
     * Scans the step for internal dictionarykeys that need a resolution
     */
    fun dictionaryKeys(): Collection<String>

    /**
     * Resolve the actions described in this step
     */
    fun resolveStep()
}


class JiraStep(
    override val name: String,
    override val stepType: String,
    val project: String,
    val jiraType: String,
    val parameters: List<Pair<String, String>>
) : Step {
    override fun dictionaryKeys(): Collection<String> {
        val regex = Regex("\\$[^$]*\\$")
        val parsableString = parameters.map { p -> p.second }.joinToString()
        return regex.findAll(parsableString).map { result -> result.value }.toList()
    }

    override fun resolveStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class MailStep(
    override val name: String, override val stepType: String,
    val to: String,
    val subject: String,
    val body: String
) : Step {
    override fun dictionaryKeys(): Collection<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resolveStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class FreeStep(
    override val name: String,
    override val stepType: String,
    val content: String
) : Step {
    override fun dictionaryKeys(): Collection<String> {
        return emptyList()
    }

    override fun resolveStep() {
        println(content)
    }

    override fun toString(): String {
        return "FREE: $content"
    }
}