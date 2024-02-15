package bitconsulting.Data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader {


    public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
        //we will use readFileToString method, that ask for the json file and the encoding format
        //teh result is a long string
        String jsonContent=
        FileUtils.readFileToString(new File(System.getProperty("user.id")+
                "\\src\\test\\java\\bitconsulting\\Data\\PurchaseOrder.json"), StandardCharsets.UTF_8);
        System.out.println(jsonContent);
        //we add dependency Jackson Databind to map jsonContent into HashMap
        //using this obj from class ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //we declare that we will return a List of hashmaps, dont forget 1 hashmap
        //contains user psw, product name
        List<  HashMap<String, String> > data =
                mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
                });
        return data;

    }
}
