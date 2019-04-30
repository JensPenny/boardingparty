import com.moandjiezana.toml.Toml
import task.FreeStep
import task.JiraStep
import task.MailStep
import task.Step

class TaskParser {

    fun parse(resource: String): Collection<Step> {
        val resultSteps = mutableListOf<Step?>()

        val loadedResource = ClassLoader.getSystemResourceAsStream(resource)
        val toml = Toml().read(loadedResource)
        val steps = toml.getTables("step")

        steps.forEach { step ->
            val type = step.getString("type")
            val desc = step.getString("description")
            println("type $type - desc $desc")

            val actualStep = when (type) {
                "jira" -> createJiraStep(desc, type, step.getTable(type))
                "mail" -> createMailStep(desc, type, step.getTable(type))
                "free" -> createFreeStep(desc, type, step.getTable(type))
                else -> null
            }
            resultSteps.add(actualStep)

            val table = step.getTable(type)
            println(table.isEmpty)
        }

        return resultSteps.filterNotNull()
    }

    private fun createFreeStep(desc: String, type: String, table: Toml): Step {
        return FreeStep(desc, type, table.getString("content"))
    }

    private fun createJiraStep(desc: String, type: String, input: Toml): JiraStep {
        val project = input.getString("project")
        val fields = input.getTables("field")

        val parameters = fields.map { field ->
            Pair<String, String>(field.getString("fieldId"), field.getString("content"))
        }

        return JiraStep(desc, type, project, parameters)
    }

    private fun createMailStep(desc: String, type: String, input: Toml): MailStep {
        val to = input.getString("to")
        val subject = input.getString("subject")
        val body = input.getString("body")

        return MailStep(desc, type, to, subject, body)
    }
}