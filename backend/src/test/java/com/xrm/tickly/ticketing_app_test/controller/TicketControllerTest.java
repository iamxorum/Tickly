package com.xrm.tickly.ticketing_app_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xrm.tickly.ticketing_app.controller.TicketController;
import com.xrm.tickly.ticketing_app.dto.TicketDTO;
import com.xrm.tickly.ticketing_app.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService ticketService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenCreateTicket_thenReturn200() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTitle("Test Ticket");

        when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticketDTO);

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(status().isOk());
    }
}