package executor

import net.sargue.mailgun.Configuration
import net.sargue.mailgun.Mail
import net.sargue.mailgun.content.Body
import task.FreeStep
import task.JiraStep
import task.MailStep
import task.Step

/**
 * Code that actually executes the different types of steps.
 * Initially this was a visitor, but this also kind of works, since this project will only have one implementation
 */
interface StepExecutor {
    fun execute(step: Step)
}

class DefaultStepExecutor(
    private val jiraExecutor: Executor<JiraStep>,
    private val mailExecutor: Executor<MailStep>
) : StepExecutor {

    override fun execute(step: Step) {
        when (step) {
            is JiraStep -> jiraExecutor.doTask(step)
            is MailStep -> mailExecutor.doTask(step)
            is FreeStep -> println(step.toString())
            else -> throw IllegalArgumentException("No executor found for step $step")
        }
    }
}