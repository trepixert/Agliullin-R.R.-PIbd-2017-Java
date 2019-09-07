import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Hangar<T extends ArmorAirCraft> implements Serializable, Comparable<Hangar<T>>,Iterable<T>, Iterator<T> {
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

    public int addAirCraft(T airCraft) throws HangarOverflowException {
        if (places.size() == this.size) {
            throw new HangarOverflowException();
        }
        for (int i = 0; i < this.size; i++) {
            if (!(places.containsKey(i))) {
                places.put(i, airCraft);
                places.get(i).setPosition(5 + i / 3 * (placeSizeWidth + 55) - 80, i % 3 * (placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
                return i;
            }
        int index = _places.size();
        for(int i=0;i<_places.size();i++){
            if(CheckFreePlace(i))
                index = i;
            if(_places.containsValue(airCraft))
                throw new HangarAlreadyHaveException();
        }
        if(index != _places.size()){
            _places.put(index,airCraft);
            _places.get(index).SetPosition(5 + index / 3 * (_placeSizeWidth+55) - 80, index % 3 * (_placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
            return index;
        }
        _places.put(index,airCraft);
        _places.get(index).SetPosition(5 + index / 3 * (_placeSizeWidth+55) - 80, index % 3 * (_placeSizeHeight + 145) + 57, pictureWidth, pictureHeight);
        return index-1;
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
        if (this._places.size() > other._places.size()) {
            return -1;
        } else if (this._places.size() < other._places.size()) {
            return 1;
        } else {
            Integer[] thisKeys = this._places.keySet().toArray(new Integer[this._places.size()]);
            Integer[] otherKeys = other._places.keySet().toArray(new Integer[other._places.size()]);
            for (int i = 0; i < this._places.size(); i++) {
                if (this._places.get(thisKeys[i]).getClass().equals(BaseArmorAirCraft.class)
                        && other._places.get(otherKeys[i]).getClass().equals(AirCraft.class)) {
                    return 1;
                }
                if (this._places.get(thisKeys[i]).getClass().equals(AirCraft.class)
                        && other._places.get(otherKeys[i]).getClass().equals(BaseArmorAirCraft.class)) {
                    return -1;
                }
                if (this._places.get(thisKeys[i]).getClass().equals(BaseArmorAirCraft.class)
                        && other._places.get(otherKeys[i]).getClass().equals(BaseArmorAirCraft.class)) {
                    return ((BaseArmorAirCraft) this._places.get(thisKeys[i])).compareTo((BaseArmorAirCraft) other._places.get(otherKeys[i]));
                }
                if (this._places.get(thisKeys[i]).getClass().equals(AirCraft.class)
                        && other._places.get(otherKeys[i]).getClass().equals(AirCraft.class)) {
                    return ((AirCraft) this._places.get(thisKeys[i]))
                            .compareTo((AirCraft) other._places.get(otherKeys[i]));
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
        if (currentIndex + 1 >= _places.size()) {
            currentIndex = -1;
            return false;
        }
        currentIndex++;
        return true;
    }

    @Override
    public T next() {
        return (T) _places.get(currentIndex);
    }

    private void reset() {
        currentIndex = -1;
    }
}
