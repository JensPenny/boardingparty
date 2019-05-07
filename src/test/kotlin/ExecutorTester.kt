import executor.DefaultStepExecutor
import executor.StepExecutor
import executor.Task
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import task.JiraStep
import task.MailStep

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExecutorTester {

    @Test
    fun startDefaultExecutor() {
        val steps = TaskParser().parse("tasks/multiple_steps.toml")
        val executor = DefaultStepExecutor(
            JiraTestTasker(),
            MailTestTasker()
        )
        for (step in steps) {
            executor.execute(step)
        }
    }

    class JiraTestTasker : Task<JiraStep> {
        override fun doTask(step: JiraStep) {
            println("creating jira in project ${step.project}")
        }
    }

    class MailTestTasker : Task<MailStep> {
        override fun doTask(step: MailStep) {
            println("sending mail ${step.subject} to ${step.to}")
        }
    }
}
