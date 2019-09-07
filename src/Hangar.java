import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Hangar<T extends ArmorAirCraft> {
    private final int size;
    private List<T> places;
    private int pictureWidth;
    private int pictureHeight;
    private int placeSizeWidth = 210;
    private int placeSizeHeight = 80;

    public Hangar(int size, int pictureWidth, int pictureHeight) {
        places = new ArrayList<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
        this.size = size;
        IntStream.range(0, this.size).forEach(i -> places.add(null));
    }

    public void addAirCraft(T airCraft) {
        for (int i = 0; i < this.size; i++) {
            if ((places.get(i) == null)) {
                places.add(i, airCraft);
                places.get(i).setPosition(5 + i / 3 * (placeSizeWidth + 55) - 80, i % 3 * (placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
                return;
            }
        }
    }

    public T removeAirCraft(int index) {
        if (index < 0 || index > this.size)
            return null;
        if (!(places.get(index) == null)) {
            places.set(index, null);
            return places.get(index);
        }
        return null;
    }

    public void draw(Graphics2D g) {
        drawMarking(g);
        places.stream().filter(place -> !(place == null)).forEach(place -> place.drawAirCraft(g));
    }

    private void drawMarking(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 1200, 1024);
        for (int i = 0; i < size / 3 + 1; i++) {
            for (int j = 0; j < 3; ++j)
                g.drawLine(i * placeSizeWidth, j * (placeSizeHeight + 145) + 20, i * placeSizeWidth + 160, j * (placeSizeHeight + 145) + 20);
            g.drawLine(i * (placeSizeWidth + 53), 20, i * (placeSizeWidth + 53), 675);
        }
    }
}
