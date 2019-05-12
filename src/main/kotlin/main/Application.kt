package main

import com.moandjiezana.toml.Toml
import executor.DefaultStepExecutor
import executor.Executor
import executor.GunmailExecutor
import executor.JiraExecutor
import net.sargue.mailgun.Configuration
import task.JiraStep
import task.MailStep
import task.StepParser
import java.util.logging.Logger

fun main(args: Array<String>) {

    val loadedResource = ClassLoader.getSystemResourceAsStream("config.toml")
    val toml = Toml().read(loadedResource)
    val mailConfig = toml.getTable("mailer")
    val jiraConfig = toml.getTable("jira")

    val executor = DefaultStepExecutor(
        createJiraExecutor(jiraConfig),
        createMailExecutor(mailConfig)
    )

    val steps = StepParser().parse("tasks/mailTestTask.toml")
    for (step in steps) {
        executor.execute(step)
    }
}


val LOGGER: Logger = Logger.getLogger("mailExecutor")
fun createMailExecutor(mailConfig: Toml): Executor<MailStep> {
    val type = mailConfig.getString("stepType")
    return if (type == "mailgun") {
        val api = mailConfig.getString("apikey")
        val domain = mailConfig.getString("domain")
        val from = mailConfig.getString("from")

        val configuration = Configuration()
            .domain(domain)
            .apiKey(api)
            .from("Test account", from)

        GunmailExecutor(configuration)
    } else {
        object : Executor<MailStep> {
            override fun doTask(step: MailStep) {
                LOGGER.info("no real mail step defined: executing\n $step")
            }
        }
    }
}

val JIRALOGGER: Logger = Logger.getLogger("jiraExecutor")
fun createJiraExecutor(jiraConfig: Toml): Executor<JiraStep> {
    val type = jiraConfig.getString("stepType")
    return if (type == "jira"){
        val server = jiraConfig.getString("server")
        val user = jiraConfig.getString("user")
        val pass = jiraConfig.getString("pass")
        JiraExecutor(user, pass, server)
    } else {
        object : Executor<JiraStep> {
            override fun doTask(step: JiraStep) {
                JIRALOGGER.info("no real jira step defined: executing\n $step")
            }
        }
    }
}