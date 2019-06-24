package Array;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;


public class Main {
    private static final int size = 10000000;
    private static int finalControl = 0;
    private static int sectorLenght, cores;
    private static float[] arr;
    private static Float[][] chunks;
    private static Thread[] threads;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    public static void main(String[] args) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("+3"));

        cores = Runtime.getRuntime().availableProcessors();
        arr = new float[size];
        sectorLenght = size / cores;

        System.out.println("Stage 01 run now!");
        createMassive();

        try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}

        System.out.println("Stage 02 run now!");

        System.out.println("Test with cores = real cpu counts:");
        cores = Runtime.getRuntime().availableProcessors(); sectorLenght = size / cores;
        createSplitMassive();
    }

    static void createMassive() {
        Arrays.fill(arr, 1);

        long a1 = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (
                    arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2)
            );
        }

        System.err.println("Stage 01 complite with time: " + dateFormat.format(System.currentTimeMillis() - a1) + "\n");
    }

    static void createSplitMassive() {
        Arrays.fill(arr, 1);
        System.out.println("PC cores: " + cores);
        System.out.println("Massive size is:\t" + size);
        System.out.println("Chunks size is:\t" + sectorLenght);

        finalControl = 0;
        threads = new Thread[cores];
        chunks = new Float[cores][sectorLenght];

        //создаем арраи и потоки в количестве процессоров ПК:
        for (int i = 0; i < cores; i++) {threads[i] = new Thread(new cRunn(i));}
        System.out.println("\nTimer start...");


        //начинаем отсчет времени:
        long a2 = System.currentTimeMillis();

        {
            System.err.println("Methode 01...");
            //заполняем каждый аррай разбивкой массива "arr":
            for (int k, i = 0; i < cores; i++) {
                for (k = 0; k < sectorLenght; k++) {chunks[i][k] = arr[k + (sectorLenght * i)];}
            }
            System.err.println("Methode 01 complite with time: " + dateFormat.format(System.currentTimeMillis() - a2));
        }


        {
            System.err.println("\nMethode 02...");

            //запускаем потоки для счета значений по формуле:
            for (int t = 0; t < threads.length; t++) {threads[t].start();}

            while (finalControl < cores) {Thread.yield();}
            System.err.println("Methode 02 complite with time: " + dateFormat.format(System.currentTimeMillis() - a2));
        }


        {
            System.err.println("\nFinal...");
            //склеиваем все обратно в общий массив:
            for (int u = 0; u < cores; u++) {
                for (int i = 0; i < sectorLenght; i++) {arr[i + (sectorLenght * u)] = chunks[u][i];}
            }

            //Выводим ответы, подводим итоги (опционально):
            System.err.println("Stage 02 complite with time: " + dateFormat.format(System.currentTimeMillis() - a2));
        }


        System.err.println("\nTest...");
        //Проверка на оставшиеся единицы (ошибка выполнения):
        Boolean errors = false;
        int errorAddress = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1.0f) {
                errors = true;
                errorAddress = i;
                break;
            }
        }

        if (errors) {System.err.println("ERROR: arr[" + errorAddress + "] = " + arr[errorAddress]);} else {System.err.println("NO ERRORS!");}
        System.err.println("__________________________________________\n");
    }


    static class cRunn implements Runnable {
        int m = 0;
        cRunn(int m) {this.m = m;}

        @Override
        public void run() {
            for (int i = 0; i < sectorLenght; i++) {
                chunks[m][i] = (float) (
                        chunks[m][i] *
                                Math.sin(0.2f + (i + sectorLenght * m) / 5) *
                                Math.cos(0.2f + (i + sectorLenght * m) / 5) *
                                Math.cos(0.4f + (i + sectorLenght * m) / 2)
                );
            }

//		Выводим ответы, подводим итоги (опционально):
            System.out.println("Thread " + Thread.currentThread().getName() + " is complite!\t");
            finalControl++;
        }
    }
}