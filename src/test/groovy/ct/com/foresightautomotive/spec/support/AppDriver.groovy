package ct.com.foresightautomotive.spec.support

import com.foresightautomotive.Application
import groovyx.net.http.RESTClient
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext

class AppDriver {
    private static final String CONTEXT_PATH = "stock-exchange-service/"
    RESTClient client
    ConfigurableApplicationContext service

    def start(String... args) {
        service = SpringApplication.run(Application.class, args)
        client = new RESTClient("http://localhost:8080/")
        cleanup()
    }

    def getServiceProperty(String propName) {
        return service.environment.getProperty(propName)
    }

    def stop() {
        service.close()
    }

    def cleanup(){
        def res = client.delete([
                path: CONTEXT_PATH + "management",
                contentType: "application/json"
        ])

        assert res.status == 204

    }

    def search(searchString) {
        def res = client.post([
                path: CONTEXT_PATH + "market/search",
                body: searchString,
                contentType: "application/json"
        ])

        assert res.status == 200

        return res.responseData
    }

    def buy(buyRequest) {
        def res = client.post([
                path: CONTEXT_PATH + "market/buy",
                body: buyRequest,
                contentType: "application/json"
        ])

        assert res.status == 200

        return res.responseData
    }

    def getPortfolio() {
        def res = client.get([
                path: CONTEXT_PATH + "portfolio",
                contentType: "application/json"
        ])

        assert res.status == 200

        return res.responseData
    }

    def sell(sellRequest) {
        def res = client.post([
                path: CONTEXT_PATH + "market/sell",
                body: sellRequest,
                contentType: "application/json"
        ])

        assert res.status == 200

        return res.responseData
    }

    def updatePrices() {
        def res = client.get([
                path: CONTEXT_PATH + "management/refresh",
                contentType: "application/json"
        ])

        assert res.status == 200
    }
}
