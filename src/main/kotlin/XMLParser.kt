import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


class XMLParser(val orderBooks: MutableMap<String, OrderBook>) {
    var docBuilderFactory = DocumentBuilderFactory.newInstance()
    var docBuilder = docBuilderFactory.newDocumentBuilder()

    fun parse(fileName: String) {
        var doc = docBuilder.parse(File("src/main/resources/$fileName"))

        val orders = doc.documentElement.childNodes
        val orderCount = orders.length

        for (i in 0 until orderCount) {
            val entry = orders.item(i)
            when (entry.nodeName) {
                "AddOrder" -> {
                    val attributes = entry.attributes
                    if (attributes != null) {
                        val book = attributes.getNamedItem("book")?.nodeValue
                        if (book != null) {
                            orderBooks.putIfAbsent(book, OrderBookImpl(book))
                            val orderbook = orderBooks[book]!!

                            val orderType = when (attributes.getNamedItem("operation").nodeValue) {
                                "SELL" -> OrderType.SELL
                                "BUY" -> OrderType.BUY
                                else -> OrderType.BUY
                            }
                            orderbook.addOrder(Order(
                                price = attributes.getNamedItem("price").nodeValue.toDouble(),
                                volume = attributes.getNamedItem("volume").nodeValue.toInt(),
                                id = attributes.getNamedItem("orderId").nodeValue.toInt(),
                            ), orderType)
                        }
                    }
                }
                "DeleteOrder" -> {
                    val attributes = entry.attributes
                    if (attributes != null) {
                        val book = attributes.getNamedItem("book")?.nodeValue
                        if (book != null) {
                            orderBooks[book]?.deleteOrder(attributes.getNamedItem("orderId").nodeValue.toInt())
                        }
                    }
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