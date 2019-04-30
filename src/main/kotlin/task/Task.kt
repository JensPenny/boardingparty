package task

class Task {
    val steps: MutableCollection<Step> = mutableListOf()

    fun addStep(step: Step){
        steps.add(step)
    }
}