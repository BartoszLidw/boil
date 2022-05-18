import java.util.Arrays;

public class Boil {

    public int[][] re_cen = new int[3][4];
    public int[][] rozlorzenie = new int[3][4];
    public int[] Op = new int[4];
    public int[] Pp = new int[3];

    public Boil (int[] O, int[] P, int[][] O_P) {
        Arrays.fill(rozlorzenie[0], -1);
        Arrays.fill(rozlorzenie[1], -1);
        Arrays.fill(rozlorzenie[2], -1);

        //Przetworzenie danych i chuj
        for (int i = 0; i < 2; i++) {
            Pp[i] = P[i];
            Op[3] += P[i];
        }


        for (int i = 0; i < 3; i++) {
            Op[i] = O[i];
            Pp[2] += O[i];
        }
        int all = Pp[0] + Pp[1] + Pp[2];
        for (int i = 0; i < 2; i++) {
            System.arraycopy(O_P[i], 0, re_cen[i], 0, 3);
        }
        for (int i = 2; i < 3; i++) {
            for (int j = 3; j < 4; j++) {
                re_cen[i][j] = 0;
            }
        }
        System.out.println(Arrays.deepToString(re_cen));
        System.out.println(Arrays.toString(Pp));
        System.out.println(Arrays.toString(Op));
        // Pierwsza iteracja
        while (all != 0) {
            int max = -100;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (rozlorzenie[i][j] == -1) {
                        if (re_cen[i][j] > max) {
                            max = re_cen[i][j];
                        }
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (max == re_cen[i][j]) {
                        if (Op[j] <= Pp[i]) {
                            for (int k = 0; k < 3; k++) {
                                if (k == i) {
                                    rozlorzenie[i][j] = Op[j];
                                } else {
                                    if (rozlorzenie[k][j] == -1) {
                                        rozlorzenie[k][j] = 0;
                                    }
                                }
                            }
                            all -= Op[j];
                            Pp[i] = Pp[i] - Op[j];
                            Op[j] = 0;


                        } else {
                            for (int k = 0; k < 4; k++) {
                                if (k == i) {
                                    rozlorzenie[i][j] = Pp[i];
                                } else {
                                    if (rozlorzenie[i][k] == -1) {
                                        rozlorzenie[i][k] = 0;
                                    }
                                }
                            }
                            all -= Pp[i];
                            Op[j] = Op[j] - Pp[i];
                            Pp[i] = 0;

                        }

                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(rozlorzenie));
        while (true) {
//Obliczanie równań liniowych;

            Integer[] alfa = new Integer[3];
            alfa[0] = 0;
            alfa[1] = null;
            alfa[2] = null;
            Integer[] beta = new Integer[4];
            beta[0] = null;
            beta[1] = null;
            beta[2] = null;
            beta[3] = null;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (rozlorzenie[i][j] != 0 && (alfa[i] == null || beta[j] == null)) {
                        if (alfa[i] != null && beta[j] == null)
                        {
                            beta[j] = re_cen[i][j] - alfa[i];

                        } else if (beta[j] != null && alfa[j] == null)
                        {
                            alfa[i] = re_cen[i][j] - beta[j];
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (rozlorzenie[i][j] != 0 && (alfa[i] == null && beta[j] == null))
                    {
                        beta[i] = re_cen[i][j];
                        alfa[j] = 0;
                    }
                }
            }
            System.out.println("Alfa" + Arrays.toString(alfa));
            System.out.println("Beta" + Arrays.toString(beta));
            //tabela sprawdzająca opłacalność
            Integer[][] tabela_oplacalnosci = new Integer[3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (rozlorzenie[i][j] == 0)
                    {
                        tabela_oplacalnosci[i][j] = re_cen[i][j] - alfa[i] - beta[j];
                    }
                }
            }
            boolean xd = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 4; j++) {
                    if (tabela_oplacalnosci[i][j] != null)
                    {
                        if(tabela_oplacalnosci[i][j] > 0)//teraz trzeba znaleść pasujące elementy
                        {
                            xd = true;//potencjalna zamiana miejsc
                            for (int m = 0; m < 3; m++) {
                                for (int n = 0; n < 4; n++) {
                                        if(tabela_oplacalnosci[i][n] == null && tabela_oplacalnosci[m][j] == null & tabela_oplacalnosci[m][n] == null)
                                        {
                                            if(rozlorzenie[i][n] <= rozlorzenie[m][j])
                                            {
                                                rozlorzenie[i][j] = rozlorzenie[i][n];
                                                rozlorzenie[m][j] += rozlorzenie[i][n];
                                                rozlorzenie[m][n] -= rozlorzenie[i][n];
                                                rozlorzenie[i][n] = 0;
                                            }
                                            else
                                            {
                                                rozlorzenie[i][n] -= rozlorzenie[m][j];
                                                rozlorzenie[i][j] += rozlorzenie[m][j];
                                                rozlorzenie[m][n] += rozlorzenie[i][n];
                                                rozlorzenie[m][j] = 0;

                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
            System.out.println("opłacalność"+Arrays.deepToString(tabela_oplacalnosci));

            if(!xd)
            {
                break;
            }
        }
        System.out.println("Ostateczny wynik" + Arrays.deepToString(rozlorzenie));
    }
}



