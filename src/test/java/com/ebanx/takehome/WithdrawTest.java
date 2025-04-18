package com.ebanx.takehome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class WithdrawTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void withdrawOriginAccountDoesNotExist() throws Exception {
        mockMvc.perform(post("/reset")).andExpect(status().isOk());

        String transferJson = """
                    {
                        "type": "withdraw",
                        "origin": "200",
                        "amount": 30
                    }
                """;

        MvcResult result = mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transferJson)
                )
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("0", responseBody);
    }

    @Test
    void withdrawNegativeAmount() throws Exception {
        String json = """
                    {
                        "type": "withdraw",
                        "origin": "100",
                        "amount": -10
                    }
                """;

        MvcResult result = mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("invalid params", responseBody);
    }

    @Test
    void withdrawPositiveAmount() throws Exception {
        mockMvc.perform(post("/reset")).andExpect(status().isOk());

        String depositJson = """
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 20
                    }
                """;

        mockMvc.perform(
                post("/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(depositJson)
        ).andExpect(status().isCreated());


        String json = """
                    {
                        "type": "withdraw",
                        "origin": "100",
                        "amount": 10
                    }
                """;

        mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin.id").value("100"))
                .andExpect(jsonPath("$.origin.balance").value(10));
    }

    @Test
    void withdrawInsufficientBalance() throws Exception {
        mockMvc.perform(post("/reset")).andExpect(status().isOk());

        String depositJson = """
                    {
                        "type": "deposit",
                        "destination": "100",
                        "amount": 10
                    }
                """;

        mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(depositJson)
                )
                .andExpect(status().isCreated());

        String transferJson = """
                    {
                        "type": "withdraw",
                        "origin": "100",
                        "destination": "200",
                        "amount": 30
                    }
                """;

        MvcResult result = mockMvc.perform(
                        post("/event")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transferJson)
                )
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("0", responseBody);
    }
}
