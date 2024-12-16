import java.time.LocalDate;

public final class MainClass {
    public static void main(String[] args) throws PlantServiceException, PlantException {

        PlantService plantService = new PlantService("resources/kvetiny.txt", "resources/kvetiny-out.txt");

        try {
            plantService.load();
        } catch (PlantServiceException e) {
            System.out.println("Couldn't load plants from plants.txt, continuing with empty list.");
        }

        plantService.printWateringInfo();

        plantService.addPlant(new PlantEntity(
                "Smrdutka nechutna",
                "Nema rada nic",
                LocalDate.of(2020, 11, 20),
                LocalDate.now().minusDays(3),
                10
        ));

        for (int i = 0; i < 10; i++) {
            plantService.addPlant(new PlantEntity(
                    "Tulipán na prodej " + i,
                    "",
                    LocalDate.now(),
                    LocalDate.now(),
                    14
            ));
        }

        plantService.removePlant(3);

        System.out.println("------");
        plantService.printWateringInfo();

        plantService.sortPlantsByWatering();

        System.out.println("------");
        plantService.printWateringInfo();

        plantService.save();

    }
}