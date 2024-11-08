package io.mandali.example;

import io.tubrux.Tubrux;

public class Main {

    public static void main(String[] args) {
        String api_key = "";
        new Tubrux().setShowDate(true)
            .setDetectSensitiveData(true)
            .scan();
    }
}
