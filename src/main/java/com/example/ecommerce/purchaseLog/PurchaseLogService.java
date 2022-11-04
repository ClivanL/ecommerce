package com.example.ecommerce.purchaseLog;

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

    public void updatePurchaseLog(Long purchaseLogId, PurchaseLog purchaseLog) {
        PurchaseLog purchaseLogInSystem=purchaseLogRepository.findById(purchaseLogId).orElseThrow();
        purchaseLogInSystem.setItem(purchaseLog.getItem());
        purchaseLogInSystem.setUser(purchaseLog.getUser());
    }
}
