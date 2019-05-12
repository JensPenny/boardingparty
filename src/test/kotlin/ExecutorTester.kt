import executor.DefaultStepExecutor
import executor.Executor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import task.JiraStep
import task.MailStep
import task.StepParser

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExecutorTester {

    @Test
    fun startDefaultExecutor() {
        val steps = StepParser().parse("tasks/multiple_steps.toml")
        val executor = DefaultStepExecutor(
            JiraTestTasker(),
            MailTestTasker()
        )
        for (step in steps) {
            executor.execute(step)
        }
    }

    class JiraTestTasker : Executor<JiraStep> {
        override fun doTask(step: JiraStep) {
            println("creating jira in project ${step.project}")
        }
    }

    class MailTestTasker : Executor<MailStep> {
        override fun doTask(step: MailStep) {
            println("sending mail ${step.subject} to ${step.to}")
        }
    }
}
