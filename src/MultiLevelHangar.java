import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MultiLevelHangar {
    private List<Hangar<ArmorAirCraft>> hangarStages;
    private final int countPlaces = 9;
    private int pictureWidth;
    private int pictureHeight;

    public MultiLevelHangar(int countStages, int pictureWidth, int pictureHeight) {
        hangarStages = new ArrayList<>();
        this.pictureWidth = pictureWidth;
        this.pictureHeight = pictureHeight;
        IntStream.range(0, countStages).forEach(i -> hangarStages.add(new Hangar<>(countPlaces, pictureWidth, pictureHeight)));
    }

    public Hangar<ArmorAirCraft> getHangar(int index) {
        return index > -1 && index < hangarStages.size() ? hangarStages.get(index) : null;
    }

    public void saveData(String path) {
        File file = new File(path);
        if (file.exists())
            file.delete();
        try (FileWriter fw = new FileWriter(path, file.canWrite())) {
            writeToFile("CountLevels:" + hangarStages.size() + System.lineSeparator(), fw);
            for (var level : hangarStages) {
                writeToFile("Level" + System.lineSeparator(), fw);
                for (int i = 0; i < countPlaces; i++) {
                    ArmorAirCraft airCraft = level.getT(i);
                    if (airCraft != null) {
                        if (airCraft.getClass().getTypeName().equals("BaseArmorAirCraftImpl"))
                            writeToFile(i + ":BaseArmorAirCraftImpl:", fw);
                        if (airCraft.getClass().getTypeName().equals("AirCraftImpl"))
                            writeToFile(i + ":AirCraftImpl:", fw);
                        writeToFile(airCraft + System.lineSeparator(), fw);
                    }
                }
            }
        } catch (IOException | HangarNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String path) throws Exception {
        File file = new File(path);
        StringBuilder textFromFile = new StringBuilder();
        if (!file.exists()) throw new FileNotFoundException();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine())
                textFromFile.append(scanner.nextLine()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] strings = textFromFile.toString().split("\n");
        if (strings[0].contains("CountLevels")) {
            int count = Integer.parseInt(strings[0].split(":")[1]);
            if (hangarStages != null) {
                hangarStages.clear();
            }
            hangarStages = new ArrayList<>(count);
        } else throw new Exception("Неверный формат файла");
        int counter = -1;
        ArmorAirCraft airCraft = null;
        for (int i = 1; i < strings.length; i++) {
            if (strings[i].equals("Level")) {
                counter++;
                hangarStages.add(new Hangar<>(countPlaces, pictureWidth, pictureHeight));
                continue;
            }
            if (strings[i] == null || strings[i].trim().length() == 0) {
                continue;
            }
            if (strings[i].split(":")[1].equals("BaseArmorAirCraftImpl")) {
                airCraft = new BaseArmorAirCraftImpl(strings[i].split(":")[2]);
            } else if (strings[i].split(":")[1].equals("AirCraftImpl")) {
                airCraft = new AirCraftImpl(strings[i].split(":")[2]);
            }
            hangarStages.get(counter).setT(Integer.parseInt(strings[i].split(":")[0]), airCraft);
        }
    }

    private void writeToFile(String text, FileWriter fw) throws IOException {
        fw.write(text, 0, text.length());
    }

}
