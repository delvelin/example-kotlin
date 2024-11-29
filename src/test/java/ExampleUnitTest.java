import io.github.hangga.delvelin.Delvelin;
import io.github.hangga.delvelin.LogListener;

public class ExampleUnitTest {
    void vulnerabilityTest(){
        new Delvelin().setLogListener(new LogListener() {
            @Override
            public void onGetLog(String s) {

            }

            @Override
            public void onGetLog(StringBuffer stringBuffer) {

            }
        }).scan();
    }
}
