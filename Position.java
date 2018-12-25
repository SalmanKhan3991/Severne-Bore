import java.util.HashMap;

public class Position {

    private static HashMap<String, Integer> positionMap = new HashMap<String, Integer>();

    public static void initializePositions() {

        String position;
        String[] columns = {"A","B","C","D","E"};
        int integerRepresentation = 16777216;

        for(int i=5; i>=1; i--) {
            for(int j=0; j<5; j++) {
                position = columns[j];
                String key = position + String.valueOf(i);
                positionMap.put(key, integerRepresentation);
                integerRepresentation = (integerRepresentation>>1);
            }
        }
        System.out.println(positionMap);
    }
    public int getPosition(String position) {
        return positionMap.get(position);
    }
}
