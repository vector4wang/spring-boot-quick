import com.quick.spark.WordCountTask;
import org.junit.Test;

import java.net.URISyntaxException;

public class WordCountTaskTest {
    @Test
    public void test() throws URISyntaxException {
        String inputFile = getClass().getResource("loremipsum.txt").toURI().toString();
        new WordCountTask().run(inputFile);
    }
}