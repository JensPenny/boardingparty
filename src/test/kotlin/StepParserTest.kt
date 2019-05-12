import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import task.JiraStep
import task.StepParser

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StepParserTest {
    @Test
    fun parseTasks() {
        val steps = StepParser().parse("tasks/multiple_steps.toml")
        assertFalse(steps.isEmpty())
        assertEquals(3, steps.size)
    }

    @Test
    fun parseAndCheckJira() {
        val steps = StepParser().parse("tasks/jiratask.toml")
        assertFalse(steps.isEmpty())
        assertTrue(steps.first() is JiraStep)
        val jira = steps.first() as JiraStep
        assertEquals("A step that creates a jira issue", jira.name)
        assertEquals("jira", jira.type)
        assertEquals("testProject", jira.project)

        val parameters = jira.parameters
        val firstParameter = parameters[0]
        assertEquals("custom-id", firstParameter.first)
        assertEquals("\$first test\$ test test ", firstParameter.second)

        val keys = jira.dictionaryKeys()
        assertTrue(keys.contains("\$first test\$"))
        assertTrue(keys.contains("\$ola\$"))
        assertEquals(2, keys.size)
    }
}
