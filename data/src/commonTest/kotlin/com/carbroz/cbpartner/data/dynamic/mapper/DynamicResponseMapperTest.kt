package com.carbroz.cbpartner.data.dynamic.mapper

import com.carbroz.cbpartner.data.dynamic.model.DynamicResponseDto
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DynamicResponseMapperTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @Test
    fun mapsDynamicResponseTreeAndActionPayload() {
        val dto = json.decodeFromString<DynamicResponseDto>(
            """
            {
              "status": 200,
              "code": "SUCCESS",
              "message": "OK",
              "data": {
                "screenId": "partner_login",
                "schemaVersion": "3.0.0",
                "targetApp": "PARTNER",
                "template": {
                  "id": "tpl_login",
                  "type": "form_template",
                  "properties": {"padding": 16},
                  "components": [{
                    "id": "component",
                    "type": "stack_component",
                    "properties": {},
                    "sections": [{
                      "id": "section",
                      "type": "stack_section",
                      "properties": {},
                      "groups": [{
                        "id": "group",
                        "type": "stack_group",
                        "properties": {},
                        "elements": [{
                          "id": "login_button",
                          "type": "button",
                          "properties": {"text": "Login"},
                          "actions": {
                            "onClick": {
                              "type": "request",
                              "payload": {
                                "method": "POST",
                                "endpoint": "/login",
                                "enabled": true
                              }
                            }
                          }
                        }]
                      }]
                    }]
                  }]
                }
              },
              "traceId": "trace-1"
            }
            """.trimIndent(),
        )

        val response = DynamicResponseMapper().map(dto)
        val button = response.data.template.components
            .single().sections.single().groups.single().elements.single()
        val action = button.actions["onClick"]

        assertEquals("partner_login", response.data.screenId)
        assertEquals("form_template", response.data.template.type)
        assertEquals("request", action?.type)
        assertEquals(
            DynamicValue.StringValue("/login"),
            action?.payload?.get("endpoint"),
        )
        assertEquals(
            DynamicValue.BooleanValue(true),
            action?.payload?.get("enabled"),
        )
        assertNotNull(action)
    }
}
