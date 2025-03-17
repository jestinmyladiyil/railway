package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ChunkedController {

    @PostMapping("/chunked/{delay}")
    public ResponseEntity<ResponseBodyEmitter> sendChunkedResponse(@RequestBody String request, @PathVariable("delay") Integer delay) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        new Thread(() -> {
            try {
                // Start of JSON response
                String jsonStart = "{\n" +
                    "\"@odata.context\": \"https://uiorch-pro.enelint.global/odata/$metadata#QueueItems/$entity\",\n" +
                    "\"QueueDefinitionId\": 2591,\n" +
                    "\"Encrypted\": false,\n" +
                    "\"OutputData\": null,\n" +
                    "\"AnalyticsData\": null,\n" +
                    "\"Status\": \"New\",\n" +
                    "\"ReviewStatus\": \"None\",\n" +
                    "\"ReviewerUserId\": null,\n" +
                    "\"Key\": \"e37b7864-4ec1-48d0-9fb6-e50aeff90150\",\n" +
                    "\"Reference\": \"TEST\",\n" +
                    "\"ProcessingExceptionType\": null,\n" +
                    "\"DueDate\": null,\n" +
                    "\"RiskSlaDate\": null,\n" +
                    "\"Priority\": \"High\",\n" +
                    "\"DeferDate\": null,\n" +
                    "\"StartProcessing\": null,\n" +
                    "\"EndProcessing\": null,\n" +
                    "\"SecondsInPreviousAttempts\": 0,\n" +
                    "\"AncestorId\": null,\n" +
                    "\"RetryNumber\": 0,\n" +
                    "\"SpecificData\": ";

                // Middle of JSON response
                String jsonMiddle = "{\n" +
                    "  \"DynamicProperties\": {\n" +
                    "    \"processCode\": \"COL_P_002\",\n" +
                    "    \"processName\": \"Bot_De_Analisis_De_Consumo\",\n" +
                    "    \"userCode\": \"carmine.mele@atos.net\",\n" +
                    "    \"companyCode\": \"1\",\n" +
                    "    \"activityCode\": \"35\",\n" +
                    "    \"activityStatus\": \"APERTA\",\n" +
                    "    \"stepCode\": 1,\n" +
                    "    \"startingStepCode\": \"2.1\",\n" +
                    "    \"inputData\": \"{\\\"step2to4\\\":false,\\\"step2to5\\\":false,\\\"step3to5\\\":false,\\\"goToEnd\\\":false,\\\"enable\\\":false,\\\"processNextStep\\\":2}\",\n" +
                    "    \"computedParams\": \"{\\\"canalDeAtencion\\\":\\\"Telefónico (call center)\\\"}\",\n" +
                    "    \"result\": \"\"\n" +
                    "  }\n" +
                    "}";

                // End of JSON response
                String jsonEnd = ",\n" +
                    "\"CreationTime\": \"2025-01-14T13:45:49.8061535Z\",\n" +
                    "\"Progress\": null,\n" +
                    "\"RowVersion\": \"AAAAABEt4wY=\",\n" +
                    "\"OrganizationUnitId\": 33,\n" +
                    "\"OrganizationUnitFullyQualifiedName\": null,\n" +
                    "\"Id\": 59965045,\n" +
                    "\"ProcessingException\": null,\n" +
                    "\"SpecificContent\": {\n" +
                    "  \"processCode\": \"COL_P_002\",\n" +
                    "  \"processName\": \"Bot_De_Analisis_De_Consumo\",\n" +
                    "  \"userCode\": \"carmine.mele@atos.net\",\n" +
                    "  \"companyCode\": \"1\",\n" +
                    "  \"activityCode\": \"35\",\n" +
                    "  \"activityStatus\": \"APERTA\",\n" +
                    "  \"stepCode\": 1,\n" +
                    "  \"startingStepCode\": \"2.1\",\n" +
                    "  \"inputData\": \"{\\\"step2to4\\\":false,\\\"step2to5\\\":false,\\\"step3to5\\\":false,\\\"goToEnd\\\":false,\\\"enable\\\":false,\\\"processNextStep\\\":2}\",\n" +
                    "  \"computedParams\": \"{\\\"canalDeAtencion\\\":\\\"Telefónico (call center)\\\"}\",\n" +
                    "  \"result\": \"\"\n" +
                    "},\n" +
                    "\"Output\": null,\n" +
                    "\"Analytics\": null\n" +
                    "}";

                // Sending chunks in rapid succession to mimic the original API
                if(delay != null) Thread.sleep(delay);
                emitter.send(jsonStart);
                if(delay != null) Thread.sleep(delay);
                emitter.send(jsonMiddle);
                if(delay != null) Thread.sleep(delay);
                emitter.send(jsonEnd);

                // Mark response as complete
                emitter.complete();

            } catch (IOException e) {
                emitter.completeWithError(e);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
        }).start();

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "application/json;odata.metadata=minimal;odata.streaming=true")
            .header(HttpHeaders.CONNECTION, "keep-alive")
            .header("X-Correlation-ID", UUID.randomUUID().toString()) // Simulate unique request tracking
            .header("Strict-Transport-Security", "max-age=31536000; includeSubDomains")
            .body(emitter);
    }
}

