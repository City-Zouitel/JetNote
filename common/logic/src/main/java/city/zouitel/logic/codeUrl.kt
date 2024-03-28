package city.zouitel.logic

//
val codeUrl: (String?) -> String?
    get() = {
        it?.replace(
            '\u002f',// -> /
            '\u2204' // -> ∄
        )
            ?.replace(
                '\u003f',// -> ?
                '\u2203' // -> 	∃
            )
    }

val decodeUrl: (String?) -> String?
    get() = {
        it?.replace(
            '\u2204', // -> ∄
            '\u002f' // -> /
        )
            ?.replace(
                '\u2203', // -> 	∃
                '\u003f' // -> ?
            )
    }