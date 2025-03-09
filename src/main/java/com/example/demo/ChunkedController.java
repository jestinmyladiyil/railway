package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class ChunkedController {

    @GetMapping(value = "/chunked/{count}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseBodyEmitter getChunkedResponse(@PathVariable("count") int count) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                for (int i = 1; i <= count; i++) {
                    emitter.send("Chunk " + i + "\n");
                    Thread.sleep(100); // Simulate delay
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
