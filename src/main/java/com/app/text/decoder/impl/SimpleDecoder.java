package com.app.text.decoder.impl;

import com.app.text.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class SimpleDecoder implements Decoder {

    @Override
    public String decode(String text) throws IOException, InterruptedException {

        return text;
    }
}
