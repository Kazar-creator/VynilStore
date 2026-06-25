package VynilStore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    public static void vinile2JSONFile(ArrayList<Vinile> elements) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("vinili.json")) {
            gson.toJson(elements, writer);
            System.out.println("File JSON creato con successo!");
        } catch (IOException e) {
            System.err.println("ERRORE nella scrittura del file JSON: " + e.getMessage());
        }
    }

    public static ArrayList<Vinile> fromJSONFile2ArrayListOfVinili(String jsonFileName) {
        Gson gson = new Gson();
        ArrayList<Vinile> vinili = null;
        try (FileReader reader = new FileReader(jsonFileName)) {
            Type listType = new TypeToken<ArrayList<Vinile>>() {}.getType();
            vinili = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("ERRORE nella lettura del file JSON: " + e.getMessage());
        }
        return vinili;
    }
}