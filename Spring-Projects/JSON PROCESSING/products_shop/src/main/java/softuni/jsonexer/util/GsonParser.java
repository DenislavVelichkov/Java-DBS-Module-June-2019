package softuni.jsonexer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class GsonParser implements Parser {
    static Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void exportDataToJson(Object obj, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(path));

        try {
            fileWriter.write(gson.toJson(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }

    @Override
    public <T> T parseDataFromJson(String str, Class<T> klass) {
        return gson.fromJson(str, klass);
    }
}
