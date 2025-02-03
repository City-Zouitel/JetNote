package city.zouitel.repository.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.Constants.DEFAULT_BOOLEAN
import city.zouitel.domain.utils.Constants.DEFAULT_INT
import city.zouitel.domain.utils.Constants.DEFAULT_TXT

/**
 * Represents a data item with various properties.
 *
 * This data class holds information about a single item, including its unique identifier,
 * title, description, priority, visual appearance (background and text color), date, and
 * whether it's archived.
 *
 * @property uid A unique identifier for the data item. Defaults to [DEFAULT_TXT].
 * @property title The title of the data item. Defaults to [DEFAULT_TXT].
 * @property description A detailed description of the data item. Defaults to [DEFAULT_TXT].
 * @property priority The priority level of the data item (e.g., 1 for high, 3 for low). Defaults to [DEFAULT_INT].
 * @property background The resource ID of the background color or drawable for the data item. Defaults to [DEFAULT_INT].
 * @property textColor The resource ID of the text color for the data item. Defaults to [DEFAULT_INT].
 * @property date The date associated with the data item, typically in a string format. Defaults to [DEFAULT_TXT].
 * @property archived Indicates whether the data item is archived or not. Defaults to [DEFAULT_BOOLEAN].
 */
@Keep
data class Data(
    var uid: String = DEFAULT_TXT,
    var title: String = DEFAULT_TXT,
    var description: String = DEFAULT_TXT,
    var priority: Int = DEFAULT_INT,
    var background: Int = DEFAULT_INT,
    var textColor: Int = DEFAULT_INT,
    var date: String = DEFAULT_TXT,
    var archived: Boolean = DEFAULT_BOOLEAN
)
