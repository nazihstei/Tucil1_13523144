package utility;

public class ColorText {
    public static final String RESET = "\u001B[0m"; // Reset ke default

    // Mapping warna
    public static final String[] COLORS = {
        "\\u001B[38;5;127m", "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m",
        "\u001B[35m", "\u001B[36m", "\u001B[37m", "\u001B[90m", "\u001B[91m",
        "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m",
        "\u001B[97m", "\u001B[38;5;208m", "\u001B[38;5;200m", "\u001B[38;5;39m",
        "\u001B[38;5;120m", "\u001B[38;5;45m", "\u001B[38;5;183m", "\u001B[38;5;220m",
        "\u001B[38;5;242m", "\u001B[38;5;130m", "\u001B[38;5;30m", "\u001B[30m"
    };
    public static final String[] COLOR_NAMES = {
        "Dark Magenta", "Red", "Green", "Yellow", "Blue", "Purple", "Cyan", "White",
        "Bright Black", "Bright Red", "Bright Green", "Bright Yellow", "Bright Blue",
        "Bright Purple", "Bright Cyan", "Bright White", "Orange", "Pink", "Light Blue",
        "Light Green", "Turquoise", "Lavender", "Gold", "Gray", "Brown", "Teal", "Black"
    };

    public static void colorPrint(String text, String color) {
        int index = -1;
        // Cari indeks warna yang sesuai
        for (int i = 0; i < COLOR_NAMES.length; i++) {
            if (COLOR_NAMES[i].equalsIgnoreCase(color)) {
                index = i;
                break;
            }
        }
        // Jika warna tidak ditemukan, gunakan default (putih)
        if (index == -1) {
            System.out.println(text);
        } else {
            System.out.println(COLORS[index] + text + RESET);
        }
    }
    public static void colorPrint(String text, int colorIndex) {
        // Jika warna tidak ditemukan, gunakan default (putih)
        if (0<colorIndex || colorIndex>25) {
            System.out.println(text);
        } else {
            System.out.println(COLORS[colorIndex] + text + RESET);
        }
    }
}
