import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public final class PlantService {
    private final String fileIn;
    private final String fileOut;

    private final PlantRepository plantRepository = new PlantRepository();

    private List<PlantEntity> plants = new ArrayList<>();

    public PlantService(String fileIn, String fileOut) {
        this.fileIn = fileIn;
        this.fileOut = fileOut;
    }

    public void addPlant(PlantEntity plant) {
        plants.add(plant);
    }

    public void removePlant(int index) throws PlantServiceException {
        try {
            plants.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new PlantServiceException("Plant " + index + " as it does not exist", e);
        }
    }

    public Collection<PlantEntity> getPlants() {
        return Collections.unmodifiableList(plants);
    }

    public PlantEntity getPlant(int index) {
        return plants.get(index);
    }

    public void printWateringInfo() {
        for (int i = 0; i < plants.size(); i++) {
            PlantEntity plant = plants.get(i);
            System.out.println("[" + i + "] " + plant.getWateringInfo());
        }
    }

    public void sortPlantsByName() {
        plants.sort(Comparator.comparing(PlantEntity::getName));
    }

    public void sortPlantsByWatering() {
        plants.sort(Comparator.comparing(PlantEntity::getWatering));
    }

    public List<PlantEntity> getPlantsThatNeedsWatering() {
        List<PlantEntity> result = new ArrayList<>();

        for (PlantEntity plant : plants) {
            LocalDate nextDayOfWatering = plant.getWatering().plusDays(plant.getFrequencyOfWatering());

            if (nextDayOfWatering.isBefore(LocalDate.now()))
                result.add(plant);
        }

        return result;
    }

    public void load() throws PlantServiceException {
        try {
            plants = plantRepository.load(fileIn);
        } catch (Exception e) {
            throw new PlantServiceException("Failed to load plants", e);
        }
    }

    public void save() throws PlantServiceException {
        try {
            plantRepository.save(fileOut, plants);
        } catch (IOException e) {
            throw new PlantServiceException("Failed to save plants", e);
        }
    }

}