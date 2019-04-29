class Task {
    val steps: MutableCollection<Step> = mutableListOf()

    fun addStep(step: Step){
        steps.add(step)
    }

}

interface Step {
    val name: String
    val type: String
    /**
     * Scans the step for internal dictionarykeys that need a resolution
     */
    fun findDictionaryKeys()

    /**
     * Resolve the actions described in this step
     */
    fun resolveStep()
}

class JiraStep(override val name: String, override val type: String) : Step {
    override fun findDictionaryKeys() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resolveStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class MailStep(override val name: String, override val type: String) : Step {
    override fun findDictionaryKeys() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resolveStep() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}