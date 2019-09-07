import java.awt.*;
import java.util.stream.IntStream;
import java.util.HashMap;

public class Hangar<T extends ArmorAirCraft> {
    private final int size;
    private HashMap<Integer,T> places;
    private int pictureWidth;
    private int pictureHeight;
    private int placeSizeWidth = 210;
    private int placeSizeHeight = 80;

    public Hangar(int size, int pictureWidth, int pictureHeight){
        maxCount = size;
        places = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
        this.size = size;
    }

    public int addAirCraft (T airCraft){
        if(places.size()==this.size)
            return -1;
        for(int i=0;i<maxCount;i++){
            if(checkFreePlace(i)){
                places.put(i, airCraft);
                places.get(i).setPosition(5 + i / 10 * _placeSizeWidth - 80, i % 5 * (_placeSizeHeight + 145) + 40, pictureWidth, pictureHeight);
                return i;
            }
        }
    }

    public T removeAirCraft (int index){
        if (!checkFreePlace(index)) {
            T airCraft = places.get(index);
            places.remove(index);
            return airCraft;
        }
        return null;
    }

    private boolean CheckFreePlace(int index){
        return !(_places.containsKey(index));
    }

    public void Draw(Graphics2D g){
        DrawMarking(g);
        for(T airCraft : _places.values())
            airCraft.DrawAirCraft(g);
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
