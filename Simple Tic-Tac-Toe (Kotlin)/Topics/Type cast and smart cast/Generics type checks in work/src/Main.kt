inline fun <reified T> countElementsOfType(list: List<Any>, clazz: Class<T>): Int = list.count { it is T }
