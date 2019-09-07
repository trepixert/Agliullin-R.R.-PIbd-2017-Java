public class HangarOccupiedPlaceException extends Exception {
    public HangarOccupiedPlaceException(int index) {
        super("На месте " + index + "уже стоит самолет");
    }
}
