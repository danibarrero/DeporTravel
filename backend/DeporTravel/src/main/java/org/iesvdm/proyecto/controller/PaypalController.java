package org.iesvdm.proyecto.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iesvdm.proyecto.dto.PaypalRequest;
import org.iesvdm.proyecto.dto.PaypalResponse;
import org.iesvdm.proyecto.service.PaypalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;

    @PostMapping("/payment/create")
    public PaypalResponse createPayment(@RequestBody PaypalRequest paypalRequest
    ) throws JsonProcessingException {

        String cancelUrl = "http://localhost:4200/payment/cancel";
        String successUrl = "http://localhost:4200/payment/success";

        return paypalService.createOrder("CAPTURE",
                paypalRequest.getCurrency(),
                paypalRequest.getAmount().toPlainString(),
                successUrl, cancelUrl);

    }

    @GetMapping("/payment/success")
    public ResponseEntity<String> paymentSuccess(
            @RequestParam("orderId") String orderId
    ) throws JsonProcessingException {

        return this.paypalService.showOrderDetails(orderId);

    }


}
