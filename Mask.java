import lombok.Data;

@Data
public class Mask {
    int maskValue;
    int maskPosition;

    Mask() {
        maskPosition = -1;
        maskValue = -1;
    }
}
