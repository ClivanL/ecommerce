package com.example.purchaseLog;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchaselog")
public class PurchaseLogController {
    private final PurchaseLogService purchaseLogService;

    @Autowired
    public PurchaseLogController(PurchaseLogService purchaseLogService){

        this.purchaseLogService=purchaseLogService;

    }

    @GetMapping
    public List<PurchaseLog> getPurchaseLog(){
        return purchaseLogService.getPurchaseLog();
    }
    @GetMapping(path="sale/{ownerId}")
    public List<PurchaseLog> getPurchaseLogsByOwnerId(@PathVariable("ownerId")Long ownerId){
        return purchaseLogService.getPurchaseLogsByOwnerId(ownerId);
    }
    @GetMapping(path="{userId}")
    public List<PurchaseLog> getPurchaseLogsByUserId(@PathVariable("userId")Long userId){
        return purchaseLogService.getPurchaseLogsByUserId(userId);
    }

    @PostMapping
    public void addPurchaseLog(@RequestBody PurchaseLog purchaseLog){
        purchaseLogService.addPurchaseLog(purchaseLog);
    }
    @DeleteMapping(path="{purchaseLogId}")
    public void deletePurchaseLog(@PathVariable("purchaseLogId") Long purchaseLogId){
        purchaseLogService.deletePurchaseLog(purchaseLogId);
    }
    @PutMapping(path="{purchaseLogId}")
    public void updatePurchaseLog(@PathVariable("purchaseLogId") Long purchaseLogId, @RequestBody PurchaseLog purchaseLog){
        purchaseLogService.updatePurchaseLog(purchaseLogId, purchaseLog);
    }
}