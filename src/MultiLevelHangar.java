import java.util.ArrayList;

public class MultiLevelHangar {

    ArrayList<Hangar<IArmorAirCraft>> hangarStages;
    private final int countPlaces = 20;

    public MultiLevelHangar(int countStages, int pictureWidth, int pictureHeight){
        hangarStages = new ArrayList<>();
        for(int i=0;i<countStages;++i)
            hangarStages.add(new Hangar<IArmorAirCraft>(countPlaces,pictureWidth,pictureHeight));
    }

    public Hangar<IArmorAirCraft> getHangar(int index){
        if(index >-1 && index<hangarStages.size())
            return hangarStages.get(index);
        return null;
    }
}
