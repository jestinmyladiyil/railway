package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ChunkedController {

    @PostMapping("/chunked")
    public ResponseBodyEmitter sendChunkedResponse(@RequestBody String request) {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // Start a new thread to handle the chunked response
        new Thread(() -> {
            try {
                // Define the JSON content with escape sequences
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
                    "\"SpecificData\" :" ;

                String jsonMiddle = "{\n" + "  \"DynamicProperties\": {\n" +
                    "    \"processCode\": \"COL_P_002\",\n" +
                    "    \"processName\": \"Bot_De_Analisis_De_Consumo\",\n" +
                    "    \"userCode\": \"<REDACTED>\",\n" + "    \"companyCode\": \"1\",\n" +
                    "    \"activityCode\": \"35\",\n" + "    \"activityStatus\": \"APERTA\",\n" +
                    "    \"stepCode\": 1,\n" + "    \"startingStepCode\": \"2.1\",\n" +
                    "    \"inputData\": \"{\\\"step2to4\\\":false,\\\"step2to5\\\":false}\",\n" +
                    "    \"computedParams\": \"{\\\"canalDeAtencion\\\":\\\"Telefónico (call center)\\\"}\",\n" +
                    "    \"result\": \"\"\n" + "  }\n" + "}\n";

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

                // Emitting chunks in parts
                emitter.send(jsonStart); // Start of the JSON
                Thread.sleep(100);
                emitter.send(jsonMiddle); // Middle part of the JSON
                Thread.sleep(100);
                emitter.send(jsonEnd); // End of the JSON

                emitter.complete(); // End the response

            } catch (IOException e) {
                emitter.completeWithError(e); // Handle error
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
        }).start();

        return emitter;
    }
}
