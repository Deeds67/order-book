
import org.w3c.dom.Node
import java.util.concurrent.LinkedBlockingQueue


class OrderBookRunner(val orderBook: OrderBook) {
    private val myTaskProcessingQueue: LinkedBlockingQueue<Node> = LinkedBlockingQueue<Node>()

    init {
        MyTaskWorker().start()
    }

    fun processTask(node: Node) {
        myTaskProcessingQueue.put(node)
    }

    private inner class MyTaskWorker : Thread() {
        override fun run() {
            while (true) {
                try {
                    processMyTask(myTaskProcessingQueue.take())
                } catch (ie: InterruptedException) {
                    // handle it
                }
            }
        }

        private fun processMyTask(entry: Node) {
            when (entry.nodeName) {
                "AddOrder" -> {
                    val attributes = entry.attributes
                    val book = attributes.getNamedItem("book")?.nodeValue
                    if (book != null) {

                        val orderType = when (attributes.getNamedItem("operation").nodeValue) {
                            "SELL" -> OrderType.SELL
                            "BUY" -> OrderType.BUY
                            else -> OrderType.BUY
                        }
                        orderBook.addOrder(Order(
                            price = attributes.getNamedItem("price").nodeValue.toDouble(),
                            volume = attributes.getNamedItem("volume").nodeValue.toInt(),
                            id = attributes.getNamedItem("orderId").nodeValue.toInt(),
                        ), orderType)
                    }
                }
                "DeleteOrder" -> {
                    val attributes = entry.attributes
                    val book = attributes.getNamedItem("book")?.nodeValue
                    if (book != null) {
                        orderBook.deleteOrder(attributes.getNamedItem("orderId").nodeValue.toInt())
                    }
                }
            }
        }
    }
}