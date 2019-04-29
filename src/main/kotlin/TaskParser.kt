import com.moandjiezana.toml.Toml

class TaskParser {

    fun parse(resource: String) : Collection<Step> {
        val resultSteps = mutableListOf<Step?>()

        val loadedResource = ClassLoader.getSystemResourceAsStream(resource)
        val toml = Toml().read(loadedResource)
        val steps = toml.getTables("step")

        steps.forEach { step ->
            val type = step.getString("type")
            val desc = step.getString("description")
            println("type $type - desc $desc")

            val actualStep = when(type){
                "jira" -> JiraStep(desc, type)
                "mail" -> MailStep(desc, type)
                else -> null
            }
            resultSteps.add(actualStep)

            val table = step.getTable(type)
            println(table.isEmpty)
        }

        return resultSteps.filterNotNull()
    }
}