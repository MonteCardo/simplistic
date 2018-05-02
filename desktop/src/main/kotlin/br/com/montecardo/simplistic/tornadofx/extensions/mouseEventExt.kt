import javafx.event.Event
import javafx.event.EventType
import javafx.scene.control.Control
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent

fun Control.act(eventType: EventType<MouseEvent>,
                button: MouseButton = MouseButton.PRIMARY,
                clickCount: Int = 1) {
    fireEvent(createEvent(eventType, button, clickCount))
}

private fun Control.createEvent(eventType: EventType<MouseEvent>, button: MouseButton, clickCount: Int): Event =
    MouseEvent(this, this, eventType, 0.0, 0.0, 0.0, 0.0,
        button, clickCount, false, false, false, false,
        button == MouseButton.PRIMARY,
        button == MouseButton.MIDDLE,
        button == MouseButton.SECONDARY,
        false, false, true, null)