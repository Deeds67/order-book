import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class OrderBookUnitTest : StringSpec({
    "Test case provided" {
        val orderBook = OrderBookImpl("test")

        val buyOrder1 = Order(100.0, 50, 1)
        val sellOrder1 = Order(101.50, 60, 2)
        val buyOrder2 = Order(100.25, 30, 3)
        val buyOrder3 = Order(99.50, 45, 4)
        val sellOrder2 = Order(99.75, 50, 2)

        orderBook.addOrder(buyOrder1, OrderType.BUY)
        orderBook.buyHeap.toList() shouldBe listOf(Order(100.0, 50, 1))
        orderBook.sellHeap.toList() shouldBe listOf()
        orderBook.addOrder(sellOrder1, OrderType.SELL)
        orderBook.buyHeap.toList() shouldBe listOf(Order(100.0, 50, 1))
        orderBook.sellHeap.toList() shouldBe listOf(Order(101.50, 60, 2))
        orderBook.addOrder(buyOrder2, OrderType.BUY)
        orderBook.buyHeap.toList() shouldContainAll listOf(Order(100.0, 50, 1), Order(100.25, 30, 3))
        orderBook.sellHeap.toList() shouldContainAll listOf(Order(101.50, 60, 2))
        orderBook.addOrder(buyOrder3, OrderType.BUY)
        orderBook.buyHeap.toList() shouldContainAll listOf(
            Order(100.0, 50, 1),
            Order(100.25, 30, 3),
            Order(99.50, 45, 4)
        )
        orderBook.sellHeap.toList() shouldContainAll listOf(Order(101.50, 60, 2))
        orderBook.addOrder(sellOrder2, OrderType.SELL)
        orderBook.buyHeap.toList() shouldContainAll listOf(
            Order(100.0, 30, 1),
            Order(99.50, 45, 4)
        )
        orderBook.sellHeap.toList() shouldBe listOf(Order(101.50, 60, 2))
    }
})