package com.example.purchaseLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void updatePurchaseLog(Long purchaseLogId, PurchaseLog purchaseLog) {
        PurchaseLog purchaseLogInSystem=purchaseLogRepository.findById(purchaseLogId).orElseThrow();
        purchaseLogInSystem.setItemId(purchaseLog.getItemId());
        purchaseLogInSystem.setUserId(purchaseLog.getUserId());
    }
    public List<PurchaseLog> getPurchaseLogsByUserId(Long userId){
        return purchaseLogRepository.findByUserId(userId);
    }
    public List<PurchaseLog> getPurchaseLogsByOwnerId(Long ownerId){
        return purchaseLogRepository.findByOwnerId(ownerId);
    }
}
