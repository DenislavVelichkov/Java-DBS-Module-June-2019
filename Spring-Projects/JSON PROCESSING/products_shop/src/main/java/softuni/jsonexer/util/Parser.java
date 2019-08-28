package softuni.jsonexer.util;

import java.io.IOException;

public interface Parser {

    void exportDataToJson(Object obj, String path) throws IOException;

    <T> T parseDataFromJson(String str, Class<T> klass);
}
