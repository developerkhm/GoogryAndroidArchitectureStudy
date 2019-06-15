package sample.nackun.com.studyfirst

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.ticker_item.view.*
import sample.nackun.com.studyfirst.market.Ticker

class TickerAdapter() : RecyclerView.Adapter<TickerViewHolder>() {

    private var items: ArrayList<Ticker> = arrayListOf()

    fun setItems(tickers: ArrayList<Ticker>) {
        items.clear()
        items.addAll(tickers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TickerViewHolder {
        return TickerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ticker_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(tickerViewHolder: TickerViewHolder, p1: Int) {
        tickerViewHolder.bind(items[p1])
    }
}

class TickerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tickerName = view.tickerName
    val currentPrice = view.currentPrice
    val comparePrice = view.comparePrice
    val changePrice = view.changePrice

    fun bind(item: Ticker) {
        tickerName.text = item.market.substring(
            item.market.indexOf("-") + 1,
            item.market.length
        )
        when (item.market.substring(0, item.market.indexOf("-"))) {
            "KRW" -> {
                currentPrice.text = String.format("%,d", item.tradePrice.toInt())
                comparePrice.text = compareToBefore(
                    item.tradePrice,
                    item.prevClosingPrice
                )
                changePrice.text = String.format(
                    "%,d",
                    (item.accTradePrice24h / 1000000).toInt()
                ) + " M"
            }
            "BTC" -> {
                currentPrice.text = String.format("%,.8f", item.tradePrice)
                comparePrice.text = compareToBefore(
                    item.tradePrice,
                    item.prevClosingPrice
                )
                changePrice.text = String.format(
                    "%,.3f",
                    item.accTradePrice24h
                )
            }
            "ETH" -> {
                if (item.tradePrice > 1) {
                    currentPrice.text = String.format(
                        "%,.2f",
                        item.tradePrice
                    )
                } else {
                    currentPrice.text = String.format("%,.8f", item.tradePrice)
                }
                comparePrice.text = compareToBefore(
                    item.tradePrice,
                    item.prevClosingPrice
                )
                changePrice.text = String.format(
                    "%,.3f",
                    item.accTradePrice24h
                )
            }
            "USDT" -> {
                if (item.tradePrice < 1) {
                    currentPrice.text = String.format("%,.8f", item.tradePrice)
                } else {
                    currentPrice.text = String.format("%,.2f", item.tradePrice)
                }
                if (currentPrice.text.endsWith("0"))
                    currentPrice.text =
                        currentPrice.text.substring(0, currentPrice.text.length - 1)

                comparePrice.text = compareToBefore(
                    item.tradePrice,
                    item.prevClosingPrice
                )
                if (item.accTradePrice24h > 1000000) {
                    changePrice.text = String.format(
                        "%,d",
                        (item.accTradePrice24h / 1000).toInt()
                    ) + " K"
                } else {
                    changePrice.text = String.format(
                        "%,d",
                        item.accTradePrice24h.toInt()
                    )
                }
            }
        }
    }

    fun compareToBefore(currentPrice: Double, beforePrice: Double): String {
        var percent = Math.abs(currentPrice - beforePrice) / beforePrice
        percent *= 100
        if (currentPrice - beforePrice > 0) {
            comparePrice.setTextColor(Color.RED)
            return String.format("%.2f", percent) + "%"
        } else if (currentPrice == beforePrice) {
            comparePrice.setTextColor(Color.BLACK)
            return String.format("%.2f", percent) + "%"
        } else {
            comparePrice.setTextColor(Color.BLUE)
            return "-" + String.format("%.2f", percent) + "%"
        }
    }
}