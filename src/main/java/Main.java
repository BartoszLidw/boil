public class Main {
    public static void main(String[] args) {
         int[] dostawca = {20, 30};
         int[] odbiorca = {10, 28, 27};
         int[][] ceny = {{12, 1, 3}, {6, 4, -1}};
         Boil a = new Boil(odbiorca, dostawca, ceny);
    }
}
