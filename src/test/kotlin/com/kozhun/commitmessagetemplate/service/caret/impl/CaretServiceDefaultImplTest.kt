package com.kozhun.commitmessagetemplate.service.caret.impl

import com.kozhun.commitmessagetemplate.service.caret.impl.CaretServiceDefaultImpl.Companion.CARET_POSITION_ANCHOR
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CaretServiceDefaultImplTest {

    private val caretService = CaretServiceDefaultImpl()

    @Test
    fun `should return pair of original message and length when message is empty`() {
        val message = ""
        val result = caretService.getCaretOffsetByAnchor(message)
        assertEquals(message to message.length, result)
    }

    @Test
    fun `should return pair of original message and length when caret anchor not present`() {
        val message = "Hello world"
        val result = caretService.getCaretOffsetByAnchor(message)
        assertEquals(message to message.length, result)
    }

    @Test
    fun `should return pair of message without caret anchor and index of caret anchor when present`() {
        val message = "Hello $CARET_POSITION_ANCHOR world"
        val result = caretService.getCaretOffsetByAnchor(message)
        assertEquals("Hello  world" to 6, result)
    }

    @Test
    fun `should return pair of message without caret anchor and its index at start when caret anchor is at the beginning of the message`() {
        val message = "$CARET_POSITION_ANCHOR Hello world"
        val result = caretService.getCaretOffsetByAnchor(message)
        assertEquals(" Hello world" to 0, result)
    }

    @Test
    fun `should return pair of message without caret anchor and its index at end when caret anchor is at the end of the message`() {
        val message = "Hello world $CARET_POSITION_ANCHOR"
        val result = caretService.getCaretOffsetByAnchor(message)
        assertEquals("Hello world " to 12, result)
    }
}
