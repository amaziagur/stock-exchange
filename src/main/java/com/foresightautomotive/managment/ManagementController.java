package com.foresightautomotive.managment;

import com.foresightautomotive.stock.PublicStockDataPopulatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private PublicStockDataPopulatorService dataPopulatorService;

    public ManagementController(PublicStockDataPopulatorService dataPopulatorService){
        this.dataPopulatorService = dataPopulatorService;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Void> reset(){
        this.dataPopulatorService.init();
        return ResponseEntity.status(204).build();
    }

    @RequestMapping(method = RequestMethod.GET, path = "refresh")
    @ResponseBody
    public ResponseEntity<Void> refresh(){
        this.dataPopulatorService.refreshPrices();
        return ResponseEntity.status(200).build();
    }
}
