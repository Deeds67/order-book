import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


class XMLParser(val orderBooks: MutableMap<String, OrderBook>) {
    var docBuilderFactory = DocumentBuilderFactory.newInstance()
    var docBuilder = docBuilderFactory.newDocumentBuilder()

    val bookRunners = mutableMapOf<String, OrderBookRunner>()

    fun parse(fileName: String) {

        var doc = docBuilder.parse(File("src/main/resources/$fileName"))

        val orders = doc.documentElement.childNodes
        val orderCount = orders.length

        for (i in 0 until orderCount) {
            val entry: Node = orders.item(i)
            entry.attributes?.let { attributes ->
                val book = attributes.getNamedItem("book")?.nodeValue
                if (book != null) {
                    val orderBook =  orderBooks[book]?: orderBooks.putIfAbsent(book, OrderBookImpl(book)) ?: orderBooks[book]!!

                    val runner = bookRunners[book]?: bookRunners.putIfAbsent(
                        book,
                        OrderBookRunner(orderBook)
                    ) ?: bookRunners[book]!!
                    runner.processTask(entry)
                }
            }
        }

        orderBooks.forEach { (k, v) ->
            println("Book $k:")
            v.printBuyHeap()
            v.printSellHeap()
        }
    }
}