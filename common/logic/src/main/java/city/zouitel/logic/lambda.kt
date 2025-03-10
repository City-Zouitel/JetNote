package city.zouitel.logic

import android.content.Context
import android.content.Intent
import android.net.Uri

val sharNote: (Context, String, String, then: () -> Unit) -> Unit = { context, title, description, then ->
    context.startActivity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
                type = "text/plain"
            },
            null
        )
    )
    then.invoke()
}

val sharApp: (Context, String) -> Unit = { context, txt ->
    context.startActivity(
        Intent.createChooser(
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, txt)
                type = "text/plain"
            },
            null
        )
    )
}

val mailTo: (Context, String) -> Unit = { context, to ->
    context.startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(to)
            },
            null
        )
    )
}

//
val callNumber: (Context, String) -> Unit = { context, number ->
    context.startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
            },
            null
        )
    )
}

//val findUrlLink: (String?) -> List<String>? = {
//    it?.split(' ')?.filter { str -> str.matches("https?://.+".toRegex()) }
//}

//

//@JvmName("filterBadWordsString")
//fun MutableState<String?>.filterBadWords(): MutableState<String?> {
//    value?.split(' ')?.onEach {
//        if (
//            it.lowercase() in listOfBadEnglishWords
//                .plus(badItian)
//                .plus(badSpanish)
//                .plus(badGerman)
//                .plus(badFrench)
//        )
//        value = value!!.replace(
//            it,
//            "${it.first()}${it.map { '*' }.joinToString("").drop(2)}${it.last()}",
//            true
//        )
//    }
//    return this
//}

//@JvmName("filterNonNullableBadWordsString")
//fun MutableState<String>.filterBadWords(): MutableState<String> {
//    value.split(' ').onEach {
//        if (
//            it.lowercase() in listOfBadEnglishWords
//                .plus(badItian)
//                .plus(badSpanish)
//                .plus(badGerman)
//                .plus(badFrench)
//        )
//        value = value.replace(
//            it,
//            "${it.first()}${it.map { '*' }.joinToString("").drop(2)}${it.last()}",
//            true
//        )
//    }
//    return this
//}

//fun MutableState<String?>.filterBadEmoji(): MutableState<String?> {
//    listOfBadEmojis.entries.forEach {
//        if (this.value?.contains(it.key) == true)
//            this.value = this.value!!.replace(it.key,it.value)
//    }
//    return this
//}

//
//fun MutableState<String?>.filterBadWebsites():MutableState<String?> {
//    findUrlLink(this.value)?.let {
//        if (URL(this@filterBadWebsites.value).host in listOfBadWebsites ) {
//            this@filterBadWebsites.value = "invalid subject!"
//            this
//        } else {
//            this
//        }
//    }
//    return this
//}

//

