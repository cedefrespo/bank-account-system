package com.example.bank.middleware;

import com.example.bank.events.DepositEvent;
import com.example.bank.events.DepositEventHandler;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LargeDepositInterceptor implements HandlerInterceptor {

    @Autowired
    private DepositEventHandler depositEventHandler;

    private static final Logger logger = LoggerFactory.getLogger(LargeDepositInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Amount: "+request.getParameter("amount"));
        String amountString = request.getParameter("amount");
        if (amountString != null) {
            double amount = Double.parseDouble(amountString);

            if (amount > 10000) {
                logger.info("Large deposit detected: " + amount);

                // Obtener el accountId como String
                String accountId = request.getParameter("accountId");

                // Crear el evento y manejarlo
                DepositEvent depositEvent = new DepositEvent(
                    accountId, // accountId como String
                    new BigDecimal(amount),
                    "deposit"
                );

                // Emite el evento
                depositEventHandler.handleDepositEvent(depositEvent);
            }
        }
        return true;
    }
}
