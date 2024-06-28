fun doubleFormat(value: Double, width: Int, precision: Int): String {
    return String.format("%,${width}.${precision}f", value)
}
