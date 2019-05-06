package executor

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

class DefaultStepExecutor : StepExecutor {

    private val jiraCreator = JiraCreator()
    private val mailCreator = Mailer()

    override fun execute(step: Step) {
        when (step) {
            is JiraStep -> jiraCreator.create(step)
            is MailStep -> mailCreator.send(step)
            is FreeStep -> println(step.toString())
            else -> throw IllegalArgumentException("No executor found for step $step")
        }
    }
}

class JiraCreator {
    fun create(step: JiraStep) {
        println("creating jira in project ${step.project}")
    }
}

class Mailer {
    fun send(step: MailStep) {
        println("sending mail ${step.subject} to ${step.to}")
    }
}