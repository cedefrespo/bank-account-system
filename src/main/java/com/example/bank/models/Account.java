package com.example.bank.models;

import lombok.Data;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
public class Account {
    @Id
    private String id;
    private String name;
    private String accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;
}
