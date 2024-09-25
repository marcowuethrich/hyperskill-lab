class TableBuilder {
    private val rows = mutableListOf<TableRowBuilder>()

    fun row(block: TableRowBuilder.() -> Unit) {
        val rowBuilder = TableRowBuilder()
        rowBuilder.block()
        rows.add(rowBuilder)
    }

    fun build(): String {
        val tableContent = buildString {
            append("<table>")
            for (row in rows) {
                append(row.build())
            }
            append("</table>")
        }
        return tableContent
    }
}

class TableRowBuilder {
    private val cells = mutableListOf<String>()

    fun cell(value: String) {
        cells.add(value)
    }

    fun build(): String {
        val rowContent = buildString {
            append("<tr>")
            for (cell in cells) {
                append("<td>")
                append(cell)
                append("</td>")
            }
            append("</tr>")
        }
        return rowContent
    }
}

fun table(block: TableBuilder.() -> Unit): String {
    val tableBuilder = TableBuilder()
    tableBuilder.block()
    return tableBuilder.build()
}
