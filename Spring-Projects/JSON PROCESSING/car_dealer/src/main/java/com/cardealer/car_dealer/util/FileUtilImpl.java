package com.cardealer.car_dealer.util;

import java.io.*;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFileContent(String path) throws IOException {
        File file = new File(path);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null){
            sb.append(line).append(System.lineSeparator());
        }
        
        reader.close();
        
        return sb.toString().trim();
    }

    @Override
    public void exportContentToJsonFile(String content, String path) {

        try {
            FileWriter file = new FileWriter(path);
            file.write(content);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
