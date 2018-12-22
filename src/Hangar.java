import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Hangar<T extends IArmorAirCraft> implements Serializable, Comparable<Hangar<T>>,Iterable<T>, Iterator<T> {
    private HashMap<Integer, T> _places;
    private int maxCount;
    private int pictureWidth;
    private int pictureHeight;
    private int _placeSizeWidth = 210;
    private int _placeSizeHeight = 80;
    private int currentIndex;

    public Hangar(int sizes, int pictureWidth, int pictureHeight) {
        maxCount = sizes;
        _places = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public int addAirCraft(T airCraft) throws HangarOverflowException, HangarAlreadyHaveException {
        if (_places.size() == maxCount)
            throw new HangarOverflowException();
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
        if (!CheckFreePlace(index)) {
            T AirCraft = _places.get(index);
            _places.remove(index);
            return AirCraft;
        }
        throw new HangarNotFoundException(index);
    }

    private boolean CheckFreePlace(int index) {
        return !(_places.containsKey(index));
    }

    public void Draw(Graphics2D g) {
        DrawMarking(g);
        for (T airCraft : _places.values())
            airCraft.DrawAirCraft(g);
    }

    private void DrawMarking(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 20, 1200, 1024);
        for (int i = 0; i < maxCount / 3+1; i++) {
            for (int j = 0; j < 3; ++j)
                g.drawLine(i * _placeSizeWidth, j * (_placeSizeHeight + 145)+20, i * _placeSizeWidth + 160, j * (_placeSizeHeight + 145)+20);
            g.drawLine(i * (_placeSizeWidth + 53), 20, i * (_placeSizeWidth + 53), 675);
        }
    }

    public void setT(int index, T value) throws HangarOccupiedPlaceException {
        if (CheckFreePlace(index)) {
            _places.put(index, value);
            _places.get(index).SetPosition(5 + index / 10 * _placeSizeWidth - 80, index % 5 * (_placeSizeHeight + 145) + 60, pictureWidth, pictureHeight);
        }else throw new HangarOccupiedPlaceException(index);
    }

    public T getT(int index) throws HangarNotFoundException {
        if(_places.containsKey(index))  return _places.get(index);
        return null;
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
