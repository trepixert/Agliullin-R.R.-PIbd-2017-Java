import java.awt.*;
import java.util.HashMap;

public class Hangar<T extends IArmorAirCraft> {
    private HashMap<Integer, T> _places;
    private int maxCount;
    private int pictureWidth;
    private int pictureHeight;
    private int _placeSizeWidth = 210;
    private int _placeSizeHeight = 80;

    public Hangar(int sizes, int pictureWidth, int pictureHeight) {
        maxCount = sizes;
        _places = new HashMap<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
    }

    public int addAirCraft(T airCraft) throws HangarOverflowException {
        if (_places.size() == maxCount)
            throw new HangarOverflowException();
        for (int i = 0; i < maxCount; i++) {
            if (CheckFreePlace(i)) {
                _places.put(i, airCraft);
                _places.get(i).SetPosition(5 + i / 10 * _placeSizeWidth - 80, i % 5 * (_placeSizeHeight + 145) + 60, pictureWidth, pictureHeight);
                return i;
            }
        }
        return -1;
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
        g.drawRect(0, 20, (maxCount / 5) * _placeSizeWidth + 500, 1024);
        for (int i = 0; i < maxCount / 5; i++) {
            for (int j = 0; j < 4; ++j)
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
}
