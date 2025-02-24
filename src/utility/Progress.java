package utility;

public class Progress {
    public static long countTotalCombination(int loop1, int loop2, int startIdx, int stopIdx) {
        return (long) Math.pow((loop1 * loop2), (stopIdx-startIdx+1));
    }

    public static void displayProgress(long current, long total) {
        int percentage = (int) (((double) current / total) * 100);
        CustomString.clearTerminal();
        System.out.println("");
        System.out.println("[=======[ Welcome To IQ Puzzler ]=======]");
        System.out.println("");
        System.out.printf("[Program] Progress   : %d\n", current);
        System.out.printf("[Program] Total      : %d\n", total);
        System.out.printf("[Program] Percentage : %d %%\n", percentage);
        System.out.println("");
        System.out.println("[=======================================]");
    }
    
}
