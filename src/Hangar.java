import java.awt.*;
import java.util.HashMap;

public class Hangar<T extends ArmorAirCraft> {
    private final int size;
    private HashMap<Integer, T> places;
    private int pictureWidth;
    private int pictureHeight;
    private int placeSizeWidth = 210;
    private int placeSizeHeight = 80;

    public Hangar(int size, int pictureWidth, int pictureHeight) {
        this.size = size;
        places = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public int addAirCraft(T airCraft) {
        if (places.size() == this.size) {
            return -1;
        }
        for (int i = 0; i < this.size; i++) {
            if (!(places.containsKey(i))) {
                places.put(i, airCraft);
                places.get(i).setPosition(5 + i / 3 * (placeSizeWidth + 55) - 80, i % 3 * (placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
                return i;
            }
        }
        return -1;
    }

    public T removeAirCraft(int index) {
        if (places.containsKey(index)) {
            places.remove(index);
            return places.get(index);
        }
        return null;
    }

    public void draw(Graphics2D g) {
        drawMarking(g);
        places.values().forEach(airCraft -> airCraft.drawAirCraft(g));
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

    public void setT(int index, T value) {
        if (CheckFreePlace(index)) {
            _places.put(index, value);
            _places.get(index).SetPosition(5 + index / 10 * _placeSizeWidth - 80, index % 5 * (_placeSizeHeight + 145) + 60, pictureWidth, pictureHeight);
        }
    }

    public T getT(int index){
        if(_places.containsKey(index))  return _places.get(index);
        return null;
    }
}
