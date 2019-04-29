import com.moandjiezana.toml.Toml
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskParserTest {
    @Test
    fun parseTasks() {
        val steps = TaskParser().parse("tasks/onboarding.toml")
        Assertions.assertFalse(steps.isEmpty())
    }
}
