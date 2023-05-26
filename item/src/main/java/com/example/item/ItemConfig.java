package com.example.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.serializer.Serializer;

import java.util.List;

@Configuration
public class ItemConfig {


    @Bean
    CommandLineRunner commandLineRunner(ItemRepository repository){
        return args -> {
            Item chocolates = new Item("Chocolates", 3.50, "cadburry",
                    "www.image.com", 22, "Candies", 1L
            );
            Item dimsum = new Item("Dim Sum", 0.50, "Dim Sum",
                    "www.image.com", 22, "Snacks", 1L
            );
            Item csb = new Item("csb", 2.50, "csb",
                    "www.image.com", 220, "Toys", 1L
            );
            repository.saveAll(
                    List.of(chocolates, dimsum,csb)
            );
        };
    }
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);

        return xStream;
    }
}