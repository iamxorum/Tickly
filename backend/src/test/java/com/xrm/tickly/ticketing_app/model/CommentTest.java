package com.xrm.tickly.ticketing_app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    @Test
    void testComment() {
        Comment comment = new Comment();
        
         
        comment.setId(1L);
        comment.setContent("This is a test comment.");
        
         
        assertEquals(1L, comment.getId());
        assertEquals("This is a test comment.", comment.getContent());
    }
}