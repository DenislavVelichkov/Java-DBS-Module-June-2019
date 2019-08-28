package app.ccb.util;

import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String path) throws IOException {
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
}