import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        AtomicInteger three = new AtomicInteger(0);
        IntStream.range(0, texts.length)
                .forEach(i -> executorService.submit(() -> {
                    if (texts[i].length() == 3 || texts[i].equals("aaa") || texts[i].equals("bbb") || texts[i].equals("ccc")) {
                        if (isPalindrome(texts[i]) || sortedText(texts[i])) {
                            three.addAndGet(1);
                        }
                    }
                }));

        AtomicInteger four = new AtomicInteger(0);
        IntStream.range(0, texts.length)
                .forEach(i -> executorService.submit(() -> {
                    if (texts[i].length() == 4 || texts[i].equals("aaaa") || texts[i].equals("bbbb") || texts[i].equals("cccc")) {
                        if (isPalindrome(texts[i]) || sortedText(texts[i])) {
                            four.addAndGet(1);
                        }
                    }
                }));

        AtomicInteger five = new AtomicInteger(0);
        IntStream.range(0, texts.length)
                .forEach(i -> executorService.submit(() -> {
                    if (texts[i].length() == 5 || texts[i].equals("aaaaa") || texts[i].equals("bbbbb") || texts[i].equals("ccccc")) {
                        if (isPalindrome(texts[i]) || sortedText(texts[i])) {
                            five.addAndGet(1);
                        }
                    }
                }));

        executorService.awaitTermination(3, TimeUnit.SECONDS);

        System.out.println("Красивых слов с длиной 3: " + three.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + four.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + five.get() + " шт.");

        executorService.shutdown();

    }

    public static boolean isPalindrome(String text) {

        String reverseText = new StringBuilder(text).reverse().toString();

        boolean result = text.equals(reverseText);

        return result;

    }

    public static boolean sortedText(String text) {

        char[] ar = text.toCharArray();
        Arrays.sort(ar);
        String sorted = String.valueOf(ar);

        return text.equals(sorted);

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}
