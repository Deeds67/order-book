import java.util.*

interface OrderBook {
    val ticker: String
    fun addOrder(order: Order, orderType: OrderType)
    fun deleteOrder(orderId: Int)
    fun printBuyHeap()
    fun printSellHeap()
}
enum class OrderType { BUY, SELL }
data class Order(val price: Double, var volume: Int, val id: Int)
class OrderBookImpl(override val ticker: String) : OrderBook {
    private val buyComparator = compareByDescending<Order> { it.price }.thenBy { it.id }
    val buyHeap = PriorityQueue(buyComparator)

    private val sellComparator = compareBy<Order> { it.price }.thenBy { it.id }
    val sellHeap = PriorityQueue(sellComparator)


    override fun addOrder(order: Order, orderType: OrderType) {
        when (orderType) {
            OrderType.BUY -> {
                if (sellHeap.isEmpty()) {
                    buyHeap.add(order)
                } else {
                    while (order.price >= (sellHeap.peek()?.price ?: Double.MAX_VALUE) && order.volume > 0) {
                        val lowestSell = sellHeap.poll()
                        if (lowestSell.volume - order.volume > 0) { // partial fill
                            lowestSell.volume = lowestSell.volume - order.volume
                            order.volume = 0
                            sellHeap.add(lowestSell)
                        } else {
                            // full fill
                            order.volume = order.volume - lowestSell.volume
                        }
                    }

                    if (order.volume > 0) {
                        buyHeap.add(order)
                    }
                }

            }
            OrderType.SELL -> {
                if (buyHeap.isEmpty()) {
                    sellHeap.add(order)
                } else {
                    while (order.price <= (buyHeap.peek()?.price ?: Double.MIN_VALUE) && order.volume > 0) {
                        val highestBuy = buyHeap.poll()
                        if (highestBuy.volume - order.volume > 0) { // partial fill
                            highestBuy.volume = highestBuy.volume - order.volume
                            order.volume = 0
                            buyHeap.add(highestBuy)
                        } else {
                            order.volume = order.volume - highestBuy.volume
                        }
                    }

                    if (order.volume > 0) {
                        sellHeap.add(order)
                    }
                }
            }
        }
    }

    override fun deleteOrder(orderId: Int) {
        sellHeap.find { it.id == orderId}.let { sellHeap.remove(it) }
        buyHeap.find { it.id == orderId}.let { buyHeap.remove(it) }
    }

    override fun printBuyHeap() {
        println("buy Heap" + buyHeap.toList())
    }

    override fun printSellHeap() {
        println("sell Heap" + sellHeap.toList())
    }

}