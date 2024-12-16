import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PlantRepository {
    public void save(String filename, Collection<PlantEntity> plants) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename, false);
             PrintWriter pw = new PrintWriter(fos)) {

            for (PlantEntity plant : plants) {
                pw.println(serializePlant(plant));
            }
        }
    }

    public List<PlantEntity> load(String filename) throws IOException, PlantException {
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr)) {

            ArrayList<PlantEntity> result = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                result.add(
                        parsePlant(line));
            }

            return result;
        }
    }

    private String serializePlant(PlantEntity plant) {
        List<String> columns = new ArrayList<>();
        columns.add(plant.getName());
        columns.add(plant.getNotes());
        columns.add(String.valueOf(plant.getFrequencyOfWatering()));
        columns.add(plant.getWatering().format(DateTimeFormatter.ISO_LOCAL_DATE));
        columns.add(plant.getPlanted().format(DateTimeFormatter.ISO_LOCAL_DATE));

        return String.join("\t", columns);
    }

    private PlantEntity parsePlant(String line) throws PlantException {
        String[] columns = line.split("\t");

        String name = columns[0];
        String description = columns[1];
        LocalDate planted = LocalDate.parse(columns[4], DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate watering = LocalDate.parse(columns[3], DateTimeFormatter.ISO_LOCAL_DATE);
        int frequencyOfWatering = Integer.parseInt(columns[2]);

        return new PlantEntity(
                name,
                description,
                planted,
                watering,
                frequencyOfWatering
        );
    }
}