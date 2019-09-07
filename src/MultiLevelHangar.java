import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MultiLevelHangar {
    private List<Hangar<ArmorAirCraft>> hangarStages;
    private final int countPlaces = 9;

    public MultiLevelHangar(int countStages, int pictureWidth, int pictureHeight) {
        hangarStages = new ArrayList<>();
        IntStream.range(0, countStages).forEach(i -> hangarStages.add(new Hangar<>(countPlaces, pictureWidth, pictureHeight)));
    }

    public Hangar<ArmorAirCraft> getHangar(int index) {
        return index > -1 && index < hangarStages.size() ? hangarStages.get(index) : null;
    }
}
