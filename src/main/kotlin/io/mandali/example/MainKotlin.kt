package io.mandali.example

import io.tubrux.Tubrux

fun main() {
    //        String token = "iOiAiSldUIn0.eyJzdWIiOiAiMTIzNDU2Nzg5MC";
    Tubrux().setShowDate(true).setDetectSensitiveData(true).scan()
}
