import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiLevelHangar {

    ArrayList<Hangar<IArmorAirCraft>> hangarStages;
    private final int countPlaces = 20;
    private int pictureWidth;
    private int pictureHeight;

    public MultiLevelHangar(int countStages, int pictureWidth, int pictureHeight){
        hangarStages = new ArrayList<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
        for(int i=0;i<countStages;++i)
            hangarStages.add(new Hangar<IArmorAirCraft>(countPlaces,pictureWidth,pictureHeight));
    }

    public Hangar<IArmorAirCraft> getHangar(int index){
        if(index >-1 && index<hangarStages.size())
            return hangarStages.get(index);
        return null;
    }

    public void saveData(String path) throws HangarNotFoundException{
        File file = new File(path);
        if(file.exists())
            file.delete();
        try(FileWriter fw = new FileWriter(path,file.canWrite())){
            writeToFile("CountLevels:"+hangarStages.size()+System.lineSeparator(),fw);
            for (var level:hangarStages) {
                writeToFile("Level"+System.lineSeparator(),fw);
                for(int i=0;i<countPlaces;i++){
                    var airCraft = level.getT(i);
                    if(airCraft!=null) {
                        if (airCraft.getClass().getTypeName().equals("BaseArmorAirCraft"))
                            writeToFile(i + ":BaseArmorAirCraft:", fw);
                        if (airCraft.getClass().getTypeName().equals("AirCraft"))
                            writeToFile(i + ":AirCraft:", fw);
                        writeToFile(airCraft + System.lineSeparator(), fw);
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadData(String path) throws Exception {
        File file = new File(path);
        String textFromFile = "";
        if(!file.exists())  throw new FileNotFoundException();
        try(Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine())
                textFromFile += scanner.nextLine()+"\n";
        }catch(Exception e){
            e.printStackTrace();
        }
        var strs = textFromFile.split("\n");
        if(strs[0].contains("CountLevels")){
            int count = Integer.parseInt(strs[0].split(":")[1]);
            if(hangarStages!=null) hangarStages.clear();
            hangarStages = new ArrayList<>(count);
        }else throw new Exception("Неверный формат файла");
        int counter = -1;
        IArmorAirCraft airCraft = null;
        for(int i=1;i<strs.length;i++){
            if(strs[i].equals("Level")){
                counter++;
                hangarStages.add(new Hangar<>(countPlaces,pictureWidth,pictureHeight));
                continue;
            }
            if(isNullOrEmpty(strs[i]))  continue;
            if(strs[i].split(":")[1].equals("BaseArmorAirCraft"))
                airCraft = new BaseArmorAirCraft(strs[i].split(":")[2]);
            else if(strs[i].split(":")[1].equals("AirCraft"))
                airCraft = new AirCraft(strs[i].split(":")[2]);
            hangarStages.get(counter).setT(Integer.parseInt(strs[i].split(":")[0]),airCraft);
        }
    }

    private void writeToFile(String text, FileWriter fw)throws IOException{
        fw.write(text,0,text.length());
    }

    private boolean isNullOrEmpty(String text){
        return text==null || text.trim().length()==0;
    }
}
