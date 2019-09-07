public class HangarNotFoundException extends Exception {
    public HangarNotFoundException(int index) {
        super("Не найден самолет по месту: " + index);
    }
}
