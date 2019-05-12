package executor

import net.sargue.mailgun.Configuration
import net.sargue.mailgun.Mail
import net.sargue.mailgun.content.Body
import task.JiraStep
import task.MailStep
import net.rcarz.jiraclient.JiraClient
import net.rcarz.jiraclient.BasicCredentials



interface Executor<in E> {
    fun doTask(step: E)
}

class GunmailExecutor(
    private val mailConfiguration: Configuration
) : Executor<MailStep> {
    override fun doTask(step: MailStep) {
        Mail.using(mailConfiguration)
            .to(step.to)
            .subject(step.subject)
            .content(Body("", step.body))
            .build()
            .sendAsync()
    }
}

class JiraExecutor(user: String, pass: String, url: String) : Executor<JiraStep>{
    val client: JiraClient

    init {
        val creds = BasicCredentials(user, pass)
        client = JiraClient(url, creds)
    }

    override fun doTask(step: JiraStep) {
        val issue = client.createIssue(step.project, step.jiraType)

        for (parameter in step.parameters){
            issue.field(parameter.first, parameter.second)
        }
        issue.execute()
    }

}