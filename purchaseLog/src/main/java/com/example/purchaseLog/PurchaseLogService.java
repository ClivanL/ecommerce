package com.example.purchaseLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
