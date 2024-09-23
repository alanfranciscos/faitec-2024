package com.eventify.eventify.util;

import java.io.IOException;

public interface ResourceFileService {
    String read(final String resourcePath) throws IOException;
}
