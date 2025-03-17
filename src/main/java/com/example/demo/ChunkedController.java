package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ChunkedController {

    @PostMapping("/chunked/{delay}")
    public ResponseEntity<ResponseBodyEmitter> sendChunkedResponse(@PathVariable("delay") Integer delay, @RequestBody String request) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        new Thread(() -> {
            try {
                // Define JSON chunks
                String jsonStart = "{\n" +
                    "\"@odata.context\": \"https://uiorch-pro.enelint.global/odata/$metadata#QueueItems/$entity\",\n" +
                    "\"QueueDefinitionId\": 2591,\n" +
                    "\"Encrypted\": false,\n" +
                    "\"OutputData\": null,\n" +
                    "\"AnalyticsData\": null,\n" +
                    "\"Status\": \"New\",\n" +
                    "\"ReviewStatus\": \"None\",\n" +
                    "\"ReviewerUserId\": null,\n" +
                    "\"Key\": \"<REDACTED>\",\n" +
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
                    "\"SpecificData\": {";

                String jsonMiddle = "\"DynamicProperties\": {\n" +
                    "    \"processCode\": \"COL_P_002\",\n" +
                    "    \"processName\": \"Bot_De_Analisis_De_Consumo\",\n" +
                    "    \"userCode\": \"<REDACTED>\",\n" +
                    "    \"companyCode\": \"1\",\n" +
                    "    \"activityCode\": \"35\",\n" +
                    "    \"activityStatus\": \"APERTA\",\n" +
                    "    \"stepCode\": 1,\n" +
                    "    \"startingStepCode\": \"2.1\",\n" +
                    "    \"inputData\": \"{\\\"step2to4\\\":false,\\\"step2to5\\\":false}\",\n" +
                    "    \"computedParams\": \"{\\\"canalDeAtencion\\\":\\\"Telefónico (call center)\\\"}\",\n" +
                    "    \"result\": \"\"\n" +
                    "  }\n" +
                    "}";

                String jsonEnd = ",\n\"CreationTime\": \"2025-03-07T09:15:17.9973192Z\",\n" +
                    "\"Progress\": null,\n" +
                    "\"RowVersion\": \"AAAAABK6D9g=\",\n" +
                    "\"OrganizationUnitId\": 33,\n" +
                    "\"OrganizationUnitFullyQualifiedName\": null,\n" +
                    "\"Id\": 66745974,\n" +
                    "\"ProcessingException\": null,\n" +
                    "\"SpecificContent\": {\n" +
                    "  \"processCode\": \"COL_P_002\",\n" +
                    "  \"processName\": \"Bot_De_Analisis_De_Consumo\",\n" +
                    "  \"userCode\": \"<REDACTED>\",\n" +
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

                // Send response in chunks
                if (delay != null) Thread.sleep(delay);
                emitter.send(jsonStart);
                if (delay != null) Thread.sleep(delay);
                emitter.send(jsonMiddle);
                if (delay != null) Thread.sleep(delay);
                emitter.send(jsonEnd);

                emitter.complete(); // Complete the response

            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e);
            }
        }).start();

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/json; odata.metadata=minimal; odata.streaming=true"))
            .body(emitter);
    }
}

