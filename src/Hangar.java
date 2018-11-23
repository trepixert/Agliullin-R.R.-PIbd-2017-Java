import java.awt.*;
import java.util.ArrayList;

public class Hangar<T extends IArmorAirCraft> {
    private ArrayList<T> _places;
    private int PictureWidth;
    private int PictureHeight;
    private int _placeSizeWidth = 210;
    private int _placeSizeHeight = 80;

    public Hangar(int sizes, int pictureWidth, int pictureHeight){
        _places = new ArrayList<>();
        PictureWidth = pictureWidth;
        PictureHeight = pictureHeight;
        for(int i=0;i<sizes;i++)
            _places.add(null);
    }

    public int addAirCraft (T AirCraft){
        for(int i=0;i< _places.size();i++){
            if(CheckFreePlace(i)){
                _places.add(i, AirCraft);
                _places.get(i).SetPosition(5 + i / 10 * _placeSizeWidth - 80,i%5*(_placeSizeHeight+145)+40, PictureWidth,PictureHeight);
                return i;
            }
        }
        return -1;
    }

    public T removeAirCraft (int index){
        if (index < 0 || index > _places.size())
            return null;
        if (!CheckFreePlace(index))
        {
            T AirCraft = _places.get(index);
            _places.set(index,null);
            return AirCraft;
        }
        return null;
    }

    private boolean CheckFreePlace(int index){
        return (_places.get(index)==null);
    }

    public void Draw(Graphics2D g){
        DrawMarking(g);
        for(int i=0;i<_places.size();i++){
            if(!CheckFreePlace(i))
                _places.get(i).DrawAirCraft(g);
        }
    }

    private void DrawMarking(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawRect( 0, 0, (_places.size() / 5) * _placeSizeWidth+500, 1024);
        for(int i = 0; i < _places.size() / 5; i++) {
            for (int j = 0; j < 4; ++j)
                g.drawLine( i * _placeSizeWidth, j * (_placeSizeHeight+145), i * _placeSizeWidth + 160, j * (_placeSizeHeight+145));
            g.drawLine( i * (_placeSizeWidth+53), 0, i * (_placeSizeWidth+53), 675);
        }
    }
}
