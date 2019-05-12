package main

import com.moandjiezana.toml.Toml
import executor.DefaultStepExecutor
import executor.Executor
import executor.GunMailTask
import net.sargue.mailgun.Configuration
import task.JiraStep
import task.MailStep
import task.StepParser
import java.util.logging.Logger

fun main(args: Array<String>) {

    val loadedResource = ClassLoader.getSystemResourceAsStream("config.toml")
    val toml = Toml().read(loadedResource)
    val mailConfig = toml.getTable("mailer")

    val executor = DefaultStepExecutor(
        object : Executor<JiraStep> {
            override fun doTask(step: JiraStep) {
                //noop
            }
        }, createMailExecutor(mailConfig)
    )

    val steps = StepParser().parse("tasks/mailTestTask.toml")
    for (step in steps) {
        executor.execute(step)
    }
}


val LOGGER: Logger = Logger.getLogger("mailExecutor")
fun createMailExecutor(mailConfig: Toml): Executor<MailStep> {
    val type = mailConfig.getString("type")
    if (type == "mailgun") {
        val api = mailConfig.getString("apikey")
        val domain = mailConfig.getString("domain")
        val from = mailConfig.getString("from")

        val configuration = Configuration()
            .domain(domain)
            .apiKey(api)
            .from("Test account", from)

        return GunMailTask(configuration)
    } else {
        return object : Executor<MailStep> {
            override fun doTask(step: MailStep) {
                LOGGER.info("no real mail step defined: executing\n $step")
            }
        }
    }


}