package task

interface Step {
    val name: String
    val type: String
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
    override val type: String,
    val project: String,
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
    override val name: String, override val type: String,
    private val to: String,
    private val subject: String,
    private val body: String
) : Step {
    override fun dictionaryKeys(): Collection<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resolveStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class FreeStep(
    override val name: String, override val type: String, private val content: String
) : Step {
    override fun dictionaryKeys(): Collection<String> {
        return emptyList()
    }

    override fun resolveStep() {
        println(content)
    }

}