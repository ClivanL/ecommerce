package com.example.purchaseLog;

import org.apache.coyote.Response;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path="review/{itemId}/{rating}")
    public List<PurchaseLog> getPurchaseLogsByItemIdAndRating(@PathVariable("itemId") Long itemId, @PathVariable("rating") String rating){
        return purchaseLogService.getPurchaseLogsByItemIdAndRating(itemId, rating);
    }

    @GetMapping(path="sentOut/{id}")
    public ResponseEntity<String> markSentOut(@PathVariable("id") Long id){
        try{
            purchaseLogService.markSentOut(id);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Item has been updated as sent out");
    }
    @GetMapping(path="received/{id}")
    public ResponseEntity<String> markReceived(@PathVariable("id") Long id){
        try{
            purchaseLogService.markReceived(id);
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Item has been updated as received");
    }

    @PostMapping
    public void addPurchaseLog(@RequestBody PurchaseLog purchaseLog){
        purchaseLogService.addPurchaseLog(purchaseLog);
    }
    @DeleteMapping(path="{purchaseLogId}")
    public void deletePurchaseLog(@PathVariable("purchaseLogId") Long purchaseLogId){
        purchaseLogService.deletePurchaseLog(purchaseLogId);
    }

    @PutMapping(path="review/{id}")
    public ResponseEntity<String> reviewPurchase(@PathVariable("id")Long id, @RequestBody PurchaseLog review){
        System.out.println("review comments"+review.getComments());
        System.out.println("review ratings"+review.getRating());
        ResponseEntity<String> response;
        try{
            response=purchaseLogService.reviewPurchase(id, review);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }
//    @PutMapping(path="{purchaseLogId}")
//    public void updatePurchaseLog(@PathVariable("purchaseLogId") Long purchaseLogId, @RequestBody PurchaseLog purchaseLog){
//        purchaseLogService.updatePurchaseLog(purchaseLogId, purchaseLog);
//    }
}