import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static final int count = 3;
    public static final int arraySize = 5;
    private static int[] generatingArrays() {
        int[] array = new int[Main.arraySize];
        for (int i = 0; i < Main.arraySize; i++) {
            array[i] = (int) (Math.random() * 7);
        }
        return array;
    }

    private static List<int[]> incomeGeneratingStores() {
        List<int[]> shopsIncomes = new ArrayList<>();
        for (int i = 0; i < Main.count; i++) {
            shopsIncomes.add(generatingArrays());
        }
        return shopsIncomes;
    }

    private static List<Thread> generateThreads(List<int[]> shopsIncomes, LongAdder longAdder) {
        List<Thread> shopThreads = new ArrayList<>();
        for (int i = 0; i < shopsIncomes.size(); i++) {
            final int finalI = i;
            shopThreads.add(new Thread(null, () -> Arrays.stream(shopsIncomes.get(finalI)).forEach(longAdder::add), "Thread " + i));
            shopThreads.get(i).start();
        }
        return shopThreads;
    }

    public static void main(String[] args) throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<int[]> shopsIncomes = incomeGeneratingStores();
        List<Thread> shopThreads = generateThreads(shopsIncomes, longAdder);

        for (Thread t : shopThreads) {
            t.join();
        }

        System.out.println("Выручка всех магазинов: " + longAdder.sum());
    }
}
