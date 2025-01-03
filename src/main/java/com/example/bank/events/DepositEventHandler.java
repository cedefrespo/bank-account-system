package com.example.bank.events;

import org.springframework.stereotype.Component;

@Component
public class DepositEventHandler {
    // Aquí puedes añadir lógica para manejar el evento, como guardar el evento en un Event Store
    public void handleDepositEvent(DepositEvent event) {
        System.out.println("Handling Deposit Event: " + event.getAmount() + " for account " + event.getAccountId());
        // Aquí podrías agregar lógica de Event Store, enviar un mensaje, etc.
    }
}
