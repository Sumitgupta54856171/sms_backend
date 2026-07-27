package com.example.schoolsystem.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/Webhook")
@RequiredArgsConstructor
public class Webhookcontroller {

    @Value("${whatsapp.verifycode}")
    private String verifyToken;

    /**
     * Verification endpoint called by Meta when configuring the webhook
     * in the WhatsApp Business API dashboard.
     *
     * Meta sends a GET request with:
     *  - hub.mode          : should be "subscribe"
     *  - hub.verify_token  : must match your configured verify token
     *  - hub.challenge     : must be echoed back in the response
     */
    @GetMapping
    public ResponseEntity<String> verifyWebhook(
            @RequestParam(value = "hub.mode", required = false) String mode,
            @RequestParam(value = "hub.verify_token", required = false) String token,
            @RequestParam(value = "hub.challenge", required = false) String challenge) {

        log.info("Webhook verification request received: mode={}, token={}, challenge={}", mode, token, challenge);

        if (mode == null || token == null || challenge == null) {
            log.warn("Missing required verification parameters");
            return ResponseEntity.badRequest().body("Missing required parameters");
        }

        if ("subscribe".equalsIgnoreCase(mode) && verifyToken.equals(token)) {
            log.info("Webhook verified successfully. Returning challenge: {}", challenge);
            return ResponseEntity.ok(challenge);
        }

        log.warn("Webhook verification failed. Mode={}, token mismatch={}", mode, !verifyToken.equals(token));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verification failed");
    }

    /**
     * Receives incoming webhook events from WhatsApp Cloud API.
     * Meta sends a POST request with the message/notification payload in JSON.
     *
     * This endpoint should return 200 OK quickly so Meta doesn't retry.
     * Process the payload asynchronously in production.
     */
    @PostMapping
    public ResponseEntity<String> receiveWebhook(@RequestBody String payload) {
        log.info("Received WhatsApp webhook payload: {}", payload);
        // TODO: parse the payload and handle messages, status updates, etc.
        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}
