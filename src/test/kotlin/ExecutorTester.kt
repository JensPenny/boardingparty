import executor.DefaultStepExecutor
import executor.StepExecutor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExecutorTester {

    @Test
    fun startDefaultExecutor(){
        val steps = TaskParser().parse("tasks/multiple_steps.toml")
        val executor = DefaultStepExecutor()
        for (step in steps){
            executor.execute(step)
        }
    }
}
