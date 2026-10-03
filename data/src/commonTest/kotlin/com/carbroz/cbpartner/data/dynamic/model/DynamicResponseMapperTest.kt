package com.carbroz.cbpartner.data.dynamic.model

import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DynamicResponseMapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun mapsDynamicResponseStructureAndActionPayload() {
        val response = json.decodeFromString<DynamicResponseDto>(
            """
            {
              "status": 200,
              "code": "SUCCESS",
              "message": "Partner login screen fetched successfully.",
              "data": {
                "screenId": "partner_login",
                "schemaVersion": "3.0.0",
                "targetApp": "PARTNER",
                "template": {
                  "id": "tpl_7K2M9Q",
                  "type": "form_template",
                  "properties": {
                    "orientation": "vertical",
                    "fillMaxSize": true
                  },
                  "components": [
                    {
                      "id": "login_content",
                      "type": "stack_component",
                      "properties": {},
                      "sections": [
                        {
                          "id": "action_section",
                          "type": "stack_section",
                          "properties": {},
                          "elements": [
                            {
                              "id": "continue_button",
                              "type": "button",
                              "properties": {
                                "text": "Continue"
                              },
                              "actions": {
                                "onClick": {
                                  "type": "request",
                                  "payload": {
                                    "method": "POST",
                                    "endpoint": "/api/v1/partner/auth/send_otp",
                                    "body": {
                                      "phoneNumber": {
                                        "$binding": "mobileNumber"
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          ]
                        }
                      ]
                    }
                  ]
                }
              },
              "traceId": "req-1"
            }
            """,
        )

        val domain = DynamicResponseMapper().map(response)

        assertEquals("partner_login", domain.data.screenId)
        assertEquals("form_template", domain.data.template.type)

        val button = domain.data.template.components
            .single()
            .sections
            .single()
            .elements
            .single()

        val action = assertNotNull(button.actions["onClick"])
        assertEquals("request", action.type)
        assertEquals("POST", action.payload["method"]?.let { it as com.carbroz.cbpartner.domain.model.dynamic.DynamicValue.StringValue }?.value)
        assertNotNull(action.payload["body"])
        assertEquals("req-1", domain.traceId)
    }
}
