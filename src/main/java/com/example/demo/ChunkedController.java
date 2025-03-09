package com.jm.learn.test;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class ChunkedController {

    @GetMapping(value = "/chunked", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBodyEmitter getChunkedJsonResponse() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Simulated chunks
                String jsonResponse = "{\n" +
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
                    "\"SpecificData\": \"{\\\"DynamicProperties\\\":{\\\"processCode\\\":\\\"COL_P_002\\\",\\\"processName\\\":\\\"Bot_De_Analisis_De_Consumo\\\",\\\"userCode\\\":\\\"<REDACTED>\\\",\\\"companyCode\\\":\\\"1\\\",\\\"activityCode\\\":\\\"35\\\",\\\"activityStatus\\\":\\\"APERTA\\\",\\\"stepCode\\\":1,\\\"startingStepCode\\\":\\\"2.1\\\",\\\"inputData\\\":\\\"{\\\\\\\"step2to4\\\\\\\":false,\\\\\\\"step2to5\\\\\\\":false}\\\",\\\"computedParams\\\":\\\"{\\\\\\\"canalDeAtencion\\\\\\\":\\\\\\\"Telefónico (call center)\\\\\\\"}\\\",\\\"result\\\":\\\"\\\"}}\",\n" +
                    "\"CreationTime\": \"2025-03-07T09:15:17.9973192Z\",\n" +
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

                byte[] jsonBytes = jsonResponse.getBytes("UTF-8");
                int chunkSize = 1024; // 1 KB chunks
                int totalLength = jsonBytes.length;

                // Sending the JSON in chunks
                for (int i = 0; i < totalLength; i += chunkSize) {
                    int remainingLength = totalLength - i;
                    int size = Math.min(remainingLength, chunkSize);
                    emitter.send(new String(jsonBytes, i, size));  // Send each chunk as a string
                }

                emitter.complete();  // Mark the emitter as complete

            } catch (IOException e) {
                emitter.completeWithError(e);  // In case of an error
            }
        });

        return emitter;
    }
}
