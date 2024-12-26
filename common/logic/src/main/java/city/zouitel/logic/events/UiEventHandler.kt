package city.zouitel.logic.events

interface UiEventHandler<T> {
    fun sendUiEvent(event: UiEvent<T>)

    fun performUiEvent(action: suspend () -> Unit)
}

interface UiEventsHandler {
    fun sendUiEvent(event: UiEvents)

    fun performUiEvent(action: suspend () -> Unit)
}
