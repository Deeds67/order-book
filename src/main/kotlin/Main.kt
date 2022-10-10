import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main(args: Array<String>) {
    val parser = XMLParser(mutableMapOf())
    println(measureTime { parser.parse("orders.xml") })
//    val orderBook = OrderBookImpl("ticker")
//
//    val buyOrder1 = Order(100.0, 50, 1)
//    val sellOrder1 = Order(101.50, 60, 2)
//    val buyOrder2 = Order(100.25, 30, 3)
//    val buyOrder3 = Order(99.50, 45, 4)
//    val sellOrder2 = Order(99.75, 50, 2)
//
//    orderBook.addOrder(buyOrder1, OrderType.BUY)
//    orderBook.printBuyHeap()
//    orderBook.printSellHeap()
//    orderBook.addOrder(sellOrder1, OrderType.SELL)
//    orderBook.printBuyHeap()`
//    orderBook.printSellHeap()
//    orderBook.addOrder(buyOrder2, OrderType.BUY)
//    orderBook.printBuyHeap()
//    orderBook.printSellHeap()
//    orderBook.addOrder(buyOrder3, OrderType.BUY)
//    orderBook.printBuyHeap()
//    orderBook.printSellHeap()
//    orderBook.addOrder(sellOrder2, OrderType.SELL)
//    orderBook.printBuyHeap()
//    orderBook.printSellHeap()
}