package com.example.backend.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/magnifier")
public class MagnifierController {
    // Endpoint to trigger magnifier actions
    @PostMapping("/activate")
    public ResponseEntity<Map<String, Object>> triggerMagnifier() {
        System.setProperty("java.awt.headless", "false");
        Map<String, Object> response = new HashMap<>();
        try {
            Robot robot = new Robot();

            // Press and hold the Windows key
            robot.keyPress(KeyEvent.VK_WINDOWS);

            // Press the "+" key
            robot.keyPress(KeyEvent.VK_ADD);
            robot.keyRelease(KeyEvent.VK_ADD);

            // Release the Windows key
            robot.keyRelease(KeyEvent.VK_WINDOWS);

            // Construct a JSON response
            response.put("success", true);
            response.put("message", "Magnifier triggered successfully!");
            return ResponseEntity.ok(response);
        } catch (AWTException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to trigger magnifier: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
