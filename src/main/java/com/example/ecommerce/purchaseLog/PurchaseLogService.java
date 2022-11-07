package com.example.ecommerce.purchaseLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseLogService {
    private final PurchaseLogRepository purchaseLogRepository;
    private final PurchaseLogMapper purchaseLogMapper;

    @Autowired
    public PurchaseLogService(PurchaseLogRepository purchaseLogRepository, PurchaseLogMapper purchaseLogMapper){
        this.purchaseLogRepository=purchaseLogRepository;
        this.purchaseLogMapper=purchaseLogMapper;
    }

    public List<PurchaseLog> getPurchaseLog() {
        return purchaseLogRepository.findAll();
    }

    public void addPurchaseLog(PurchaseLogDTO purchaseLogDTO) {
        PurchaseLog purchaseLog= purchaseLogMapper.toEntity(purchaseLogDTO);
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
