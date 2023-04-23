package com.example.purchaseLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseLogService {
    private final PurchaseLogRepository purchaseLogRepository;

    @Autowired
    public PurchaseLogService(PurchaseLogRepository purchaseLogRepository){
        this.purchaseLogRepository=purchaseLogRepository;
    }

    public List<PurchaseLog> getPurchaseLog() {
        return purchaseLogRepository.findAll();
    }

    public void addPurchaseLog(PurchaseLog purchaseLog) {
        purchaseLogRepository.save(purchaseLog);
    }

    public void deletePurchaseLog(Long purchaseLogId) {
        purchaseLogRepository.deleteById(purchaseLogId);
    }

//    public void updatePurchaseLog(Long purchaseLogId, PurchaseLog purchaseLog) {
//        PurchaseLog purchaseLogInSystem=purchaseLogRepository.findById(purchaseLogId).orElseThrow();
//        purchaseLogInSystem.setItemId(purchaseLog.getItemId());
//        purchaseLogInSystem.setUserId(purchaseLog.getUserId());
//    }
    public List<PurchaseLog> getPurchaseLogsByUserId(Long userId){
        return purchaseLogRepository.findByUserId(userId);
    }
    public List<PurchaseLog> getPurchaseLogsByOwnerId(Long ownerId){
        return purchaseLogRepository.findByOwnerId(ownerId);
    }

    public List<PurchaseLog> getPurchaseLogsByItemId(Long itemId){
        return purchaseLogRepository.findByItemId(itemId);
    }

    public void markSentOut(Long id){
        Optional<PurchaseLog> logToUpdate=purchaseLogRepository.findById(id);
        if (logToUpdate.isPresent()){
            PurchaseLog confirmedLog=logToUpdate.get();
            confirmedLog.setSent(true);
            purchaseLogRepository.save(confirmedLog);
        }
        else{
            throw new IllegalStateException("Purchase Log id does not exist");
        }
    }

    public void markReceived(Long id){
        Optional<PurchaseLog> logToUpdate=purchaseLogRepository.findById(id);
        if (logToUpdate.isPresent()){
            PurchaseLog confirmedLog=logToUpdate.get();
            if (confirmedLog.getSent()!=true){
                throw new IllegalStateException("Item in purchase log has not been sent out.");
            }
            confirmedLog.setReceived(true);
            purchaseLogRepository.save(confirmedLog);
        }
        else{
            throw new IllegalStateException("Purchase Log id does not exist");
        }
    }
    @Transactional
    public ResponseEntity<String> reviewPurchase(Long id, PurchaseLog review){

        if (review.getRating()==0){
            throw new IllegalStateException("Review must minimally have a rating");
        }
        Optional<PurchaseLog> purchaseLogCheck=purchaseLogRepository.findById(id);
        if (purchaseLogCheck.isEmpty()){
            throw new IllegalStateException("Purchase Log does not exist");
        }
        PurchaseLog purchaseLogPresent=purchaseLogCheck.get();
        if (purchaseLogPresent.getReviewedAt()!=null){
            throw new IllegalStateException("Review has already been passed");
        }
        else if (purchaseLogPresent.getSent()!=true){
            throw new IllegalStateException("Item has not been sent out yet");
        }
        else if (purchaseLogPresent.getReceived()!=true){
            throw new IllegalStateException("Item has not been received");
        }

        purchaseLogPresent.setRating(review.getRating());
        purchaseLogPresent.setComments(review.getComments());
        purchaseLogPresent.setReviewedAt(LocalDateTime.now());

        RestTemplate restTemplate= new RestTemplate();
        String uri="http://item-server:8080/api/item/averageRatings/"+purchaseLogPresent.getItemId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(new PurchaseLog(review.getRating())),String.class);
        System.out.println(response.getStatusCodeValue());
        if (response.getStatusCodeValue()!=200){
            throw new IllegalStateException("Failed to update reviews in item");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Review successfully passed");
    }
}
