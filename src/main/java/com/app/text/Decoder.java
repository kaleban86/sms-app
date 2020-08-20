package com.app.text;

import java.io.IOException;

public interface Decoder {

    public String decode(String text) throws IOException, InterruptedException;
}
