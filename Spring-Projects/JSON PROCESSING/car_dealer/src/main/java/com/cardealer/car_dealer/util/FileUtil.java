package com.cardealer.car_dealer.util;

import java.io.IOException;

public interface FileUtil {

    String readFileContent(String path) throws IOException;

    void exportContentToJsonFile(String content, String path);
}
