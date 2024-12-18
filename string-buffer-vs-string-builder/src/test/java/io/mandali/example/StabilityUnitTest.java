package io.mandali.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import io.github.hangga.delvelin.Delvelin;
import io.github.hangga.delvelin.properties.OutputFileFormat;

public class StabilityUnitTest {

    String apikey = "";
//    String token = "JHbjhgug44^%$%$6";

    void append(Appendable appendable, String string) {
        try {
            appendable.append(string);
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            System.out.println(appendable.getClass()
                .getTypeName());
        }
    }

    @RepeatedTest(100)
    public void shouldIndicateStringBuilderIsNotThreadSafe() throws InterruptedException {
        StringBuilder sharedBuilder = new StringBuilder();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                append(sharedBuilder, "1");
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                append(sharedBuilder, "2");
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                append(sharedBuilder, "A");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("sharedBuilder.length():" + sharedBuilder.length() + (sharedBuilder.length() != 3000 ? "❌" : ""));
    }

    @RepeatedTest(100)
    public void shouldVerifyStringBufferIsThreadSafe() throws InterruptedException {
        StringBuffer sharedBuffer = new StringBuffer();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                append(sharedBuffer, "1");
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                append(sharedBuffer, "2");
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                append(sharedBuffer, "3");
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("sharedBuffer.length():" + sharedBuffer.length() + (sharedBuffer.length() != 3000 ? "❌" : ""));
        Assertions.assertEquals(3000, sharedBuffer.length());
    }

    @Test
    void vulnerabilityTest(){
        new Delvelin()
            .setOutputFormat(OutputFileFormat.HTML)
            .setAutoLaunchBrowser(true) // auto open browser for HTML format
            .setShowDate(true) // for Console LOG format
            .scan();
    }
}
