package ct.com.foresightautomotive.spec

import ct.com.foresightautomotive.spec.support.AppDriver
import ct.com.foresightautomotive.spec.support.BuyRequest
import ct.com.foresightautomotive.spec.support.SearchRequest
import ct.com.foresightautomotive.spec.support.SellRequest
import spock.lang.Shared
import spock.lang.Specification

class StockExchangeSpec extends Specification {

    @Shared
    AppDriver app = new AppDriver()

    def setupSpec() {
        app.start()
    }

    def cleanup(){
        app.cleanup()
    }

    def cleanupSpec() {
        app.stop()
    }

    def "should search stock by letter" (){
        when:
        def data = new SearchRequest(searchString: "ama")
        def results = app.search(data)

        then:
        assert results.stocks.get(0).name == "Amazon"
    }

    def "should buy stock" (){
        when:
        def data = new SearchRequest(searchString: "ama")
        def results = app.search(data)
        def stockSymbol = results.stocks.get(0).symbol

        and:
        def res = app.buy(new BuyRequest(stockSymbol: stockSymbol, stockQuantity: 2));
        assert res.funds == 10000 - 119.58*2

        then:
        assert app.getPortfolio().funds == 10000 - 119.58*2
        assert app.getPortfolio().myStocks.size() == 1
        assert app.getPortfolio().myStocks.get(0).quantity == 2
        assert app.getPortfolio().myStocks.get(0).purchasePrice == 119.58
    }

    def "should sell stock" (){
        when:
        def data = new SearchRequest(searchString: "go")
        def results = app.search(data)
        def stockSymbol = results.stocks.get(0).symbol

        and:
        def res = app.buy(new BuyRequest(stockSymbol: stockSymbol, stockQuantity: 2));
        assert Math.round(res.funds) == Math.round(10000 - 1085.09*2)

        then:
        def fundsRes = app.sell(new SellRequest(stockSymbol: stockSymbol))
        assert fundsRes.funds == 10000.0
    }

    def "should update stock prices" (){
        when:
        def data = new SearchRequest(searchString: "go")
        def results = app.search(data)

        and:
        app.updatePrices()

        then:
        def newData = new SearchRequest(searchString: "go")
        def newResults = app.search(newData)

        assert results.stocks.get(0).currentPrice != newResults.stocks.get(0).currentPrice
    }
}
