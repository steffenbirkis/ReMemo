package com.example.steffen.rememo;

import com.example.steffen.rememo.Logic.StringLogic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class LogicUnitTest {

    @Test
    public void StringLogic_Encode() throws Exception {
        assertEquals(",", StringLogic.EncodeString("."));
    }

    @Test
    public void StringLogic_formatString() throws Exception{
        assertEquals("Teste Tester Testet",StringLogic.formatString("tESTE tesTER TeSTet"));
    }
}