package executor

import net.sargue.mailgun.Configuration
import net.sargue.mailgun.Mail
import net.sargue.mailgun.MailRequestCallback
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
    private val jiraTasker : Task<JiraStep>,
    private val mailTasker : Task<MailStep>
) : StepExecutor{

    override fun execute(step: Step) {
        when (step) {
            is JiraStep -> jiraTasker.doTask(step)
            is MailStep -> mailTasker.doTask(step)
            is FreeStep -> println(step.toString())
            else -> throw IllegalArgumentException("No executor found for step $step")
        }
    }
}

interface Task<in E>{
    fun doTask(step: E)
}

class GunMailTask (
    private val mailConfiguration: Configuration
): Task<MailStep> {
    override fun doTask(step: MailStep) {
        Mail.using(mailConfiguration)
            .to(step.to)
            .subject(step.subject)
            .content(Body("", step.body))
            .build()
            .sendAsync()
    }

}