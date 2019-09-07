import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Hangar<T extends ArmorAirCraft> implements Serializable, Comparable<Hangar<T>>, Iterable<T>, Iterator<T> {
    private final int size;
    private HashMap<Integer, T> places;
    private int pictureWidth;
    private int pictureHeight;
    private int placeSizeWidth = 210;
    private int placeSizeHeight = 80;
    private int currentIndex;

    public Hangar(int size, int pictureWidth, int pictureHeight) {
        this.size = size;
        places = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public int addAirCraft(T airCraft) throws HangarOverflowException, HangarAlreadyHaveException {
        if (places.size() == this.size) {
            throw new HangarOverflowException();
        }
        for (int i = 0; i < this.size; i++) {
            if (!(places.containsKey(i))) {
                places.put(i, airCraft);
                places.get(i).setPosition(5 + i / 3 * (placeSizeWidth + 55) - 80, i % 3 * (placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
                return i;
            }
        }
        int index = places.size();
        for (int i = 0; i < places.size(); i++) {
            if (!(places.containsKey(i)))
                index = i;
            if (places.containsValue(airCraft))
                throw new HangarAlreadyHaveException();
        }
        if (index != places.size()) {
            places.put(index, airCraft);
            places.get(index).setPosition(5 + index / 3 * (placeSizeWidth + 55) - 80, index % 3 * (placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
            return index;
        }
        places.put(index, airCraft);
        places.get(index).setPosition(5 + index / 3 * (placeSizeWidth + 55) - 80, index % 3 * (placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
        return index - 1;
    }

    public T removeAirCraft(int index) throws HangarNotFoundException {
        if (places.containsKey(index)) {
            places.remove(index);
            return places.get(index);
        }
        throw new HangarNotFoundException(index);
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

    public void setT(int index, T value) throws HangarOccupiedPlaceException {
        if (!(places.containsKey(index))) {
            places.put(index, value);
            places.get(index).setPosition(5 + index / 10 * placeSizeWidth - 80, index % 5 * (placeSizeHeight + 145) + 60, pictureWidth, pictureHeight);
        } else throw new HangarOccupiedPlaceException(index);
    }

    public T getT(int index) throws HangarNotFoundException {
        return places.getOrDefault(index, null);
    }

    @Override
    public int compareTo(Hangar<T> other) {
        if (this.places.size() > other.places.size()) {
            return -1;
        } else if (this.places.size() < other.places.size()) {
            return 1;
        } else {
            Integer[] thisKeys = this.places.keySet().toArray(new Integer[0]);
            Integer[] otherKeys = other.places.keySet().toArray(new Integer[0]);
            for (int i = 0; i < this.places.size(); i++) {
                if (this.places.get(thisKeys[i]).getClass().equals(BaseArmorAirCraftImpl.class)
                        && other.places.get(otherKeys[i]).getClass().equals(AirCraftImpl.class)) {
                    return 1;
                }
                if (this.places.get(thisKeys[i]).getClass().equals(AirCraftImpl.class)
                        && other.places.get(otherKeys[i]).getClass().equals(BaseArmorAirCraftImpl.class)) {
                    return -1;
                }
                if (this.places.get(thisKeys[i]).getClass().equals(BaseArmorAirCraftImpl.class)
                        && other.places.get(otherKeys[i]).getClass().equals(BaseArmorAirCraftImpl.class)) {
                    return ((BaseArmorAirCraftImpl) this.places.get(thisKeys[i])).compareTo((BaseArmorAirCraftImpl) other.places.get(otherKeys[i]));
                }
                if (this.places.get(thisKeys[i]).getClass().equals(AirCraftImpl.class)
                        && other.places.get(otherKeys[i]).getClass().equals(AirCraftImpl.class)) {
                    return ((AirCraftImpl) this.places.get(thisKeys[i]))
                            .compareTo((AirCraftImpl) other.places.get(otherKeys[i]));
                }
            }
        }
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (currentIndex + 1 >= places.size()) {
            currentIndex = -1;
            return false;
        }
        currentIndex++;
        return true;
    }

    @Override
    public T next() {
        return places.get(currentIndex);
    }

    private void reset() {
        currentIndex = -1;
    }
}
