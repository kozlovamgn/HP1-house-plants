import java.time.LocalDate;

public final class PlantEntity {
    private static final int DEFAULT_FREQUENCY_OF_WATERING = 7;

    private final String name;
    private final String notes;
    private final LocalDate planted;
    private LocalDate watering;
    private final int frequencyOfWatering;

    public PlantEntity(String name) throws PlantException {
        this(name, DEFAULT_FREQUENCY_OF_WATERING);
    }

    public PlantEntity(String name, int frequencyOfWatering) throws PlantException {
        this(name, "", LocalDate.now(), LocalDate.now(), frequencyOfWatering);
    }

    public PlantEntity(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0)
            throw new PlantException("Frequency of watering needs to be positive whole number");

        if (watering.isBefore(planted))
            throw new PlantException("Plant watering date must be after it was planted");

        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.watering = watering;
        this.frequencyOfWatering = frequencyOfWatering;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public String getWateringInfo() {
        return "Plant \"" + name + "\", watered " + watering + ", next watering planned at " + recommendedWatering();
    }

    public void doWateringNow() {
        watering = LocalDate.now();
    }

    private LocalDate recommendedWatering() {
        return watering.plusDays(frequencyOfWatering);
    }
}