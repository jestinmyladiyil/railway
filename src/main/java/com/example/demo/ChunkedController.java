package com.jm.learn.test;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api")
public class ChunkedController {

    @GetMapping(value = "/chunked-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBodyEmitter getChunkedJsonResponse() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Simulated chunks
                String[] jsonChunks = {
                    "{\"@odata.context\":\"https://uiorch-pro.enelint.global/odata/$metadata#QueueItems/$entity\",",
                    "\"QueueDefinitionId\":2591,\"Encrypted\":false,\"OutputData\":null,",
                    "\"AnalyticsData\":null,\"Status\":\"New\",\"ReviewStatus\":\"None\",",
                    "\"ReviewerUserId\":null,\"Key\":\"<REDACTED>\",\"Reference\":\"TEST\",",
                    "\"ProcessingExceptionType\":null,\"DueDate\":null,\"RiskSlaDate\":null,",
                    "\"Priority\":\"High\",\"DeferDate\":null,\"StartProcessing\":null,",
                    "\"EndProcessing\":null,\"SecondsInPreviousAttempts\":0,\"AncestorId\":null,",
                    "\"RetryNumber\":0,\"SpecificData\":\"{\\\"DynamicProperties\\\":{",
                    "\\\"processCode\\\":\\\"COL_P_002\\\",\\\"processName\\\":\\\"Bot_De_Analisis_De_Consumo\\\",",
                    "\\\"userCode\\\":\\\"<REDACTED>\\\",\\\"companyCode\\\":\\\"1\\\",",
                    "\\\"activityCode\\\":\\\"35\\\",\\\"activityStatus\\\":\\\"APERTA\\\",",
                    "\\\"stepCode\\\":1,\\\"startingStepCode\\\":\\\"2.1\\\",",
                    "\\\"inputData\\\":\\\"{\\\\\\\"step2to4\\\\\\\":false,\\\\\\\"step2to5\\\\\\\":false}\\\"\",",
                    "\\\"computedParams\\\":\\\"{\\\\\\\"canalDeAtencion\\\\\\\":\\\\\\\"Telef\\u00f3nico (call center)\\\\\\\"}\\\"\",",
                    "\\\"result\\\":\\\"\\\"}}\",\"CreationTime\":\"2025-03-07T09:15:17.9973192Z\",",
                    "\"Progress\":null,\"RowVersion\":\"AAAAABK6D9g=\",\"OrganizationUnitId\":33,",
                    "\"OrganizationUnitFullyQualifiedName\":null,\"Id\":66745974,\"ProcessingException\":null,",
                    "\"SpecificContent\":{\"processCode\":\"COL_P_002\",\"processName\":\"Bot_De_Analisis_De_Consumo\",",
                    "\"userCode\":\"<REDACTED>\",\"companyCode\":\"1\",\"activityCode\":\"35\",",
                    "\"activityStatus\":\"APERTA\",\"stepCode\":1,\"startingStepCode\":\"2.1\",",
                    "\"inputData\":\"{\\\"step2to4\\\":false,\\\"step2to5\\\":false,\\\"step3to5\\\":false,",
                    "\\\"goToEnd\\\":false,\\\"enable\\\":false,\\\"processNextStep\\\":2}\",",
                    "\"computedParams\":\"{\\\"canalDeAtencion\\\":\\\"Telef\\u00f3nico (call center)\\\"}\",",
                    "\"result\":\"\"},\"Output\":null,\"Analytics\":null}\n"
                };

                for (String chunk : jsonChunks) {
                    emitter.send(chunk);
                    Thread.sleep(50); // Simulate streaming delay
                }

                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}
