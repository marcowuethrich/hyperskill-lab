fun concatenate(str1: String, str2: String, str3: String, separator: String = " ") =
    listOf(str1, str2, str3).joinToString(separator)
