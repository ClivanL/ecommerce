package com.example.purchaseLog;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.TimeZone;

@Configuration
public class PurchaseLogConfig {
    @Bean
    CommandLineRunner commandLineRunner(PurchaseLogRepository purchaseLogRepository){
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        return args -> {
            PurchaseLog purchaselog= new PurchaseLog(1L,1L);
            purchaseLogRepository.saveAll(List.of(purchaselog));
        };
    }
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);

        return xStream;
    }
}
