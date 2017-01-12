import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by btow on 12.01.2017.
 */
public class Task1ImplTest {

    @Test
    public void testSort() throws Exception {

        final List<String[]> expectedValue_rows = new ArrayList<>();
        //rous 0
        String[] expectedValue_arrayString0 = {null, null, null};
        expectedValue_rows.add(expectedValue_arrayString0);
        //rous 1
        String[] expectedValue_arrayString1 = {"", "", ""};
        expectedValue_rows.add(expectedValue_arrayString1);
        //rous 2
        String[] expectedValue_arrayString2 = {"123aa45", "123aa45", "123aa45"};
        expectedValue_rows.add(expectedValue_arrayString2);
        //rous 3
        String[] expectedValue_arrayString3 = {"123ab45", "123ab45", "123ab45"};
        expectedValue_rows.add(expectedValue_arrayString3);
        //rous 4
        expectedValue_rows.add(expectedValue_arrayString3);
        //rous 5
        String[] expectedValue_arrayString5 = {"aa123bb45", "aa123bb45", "aa123bb45"};
        expectedValue_rows.add(expectedValue_arrayString5);
        //rous 6
        String[] expectedValue_arrayString6 = {"ab123bb45", "ab123bb45", "ab123bb45"};
        expectedValue_rows.add(expectedValue_arrayString6);

        final List<String[]> inputValue_rows = new ArrayList<>();
        //rous 0
        String[] inputValue_arrayString1 = {"", "", ""};
        inputValue_rows.add(inputValue_arrayString1);
        //rous 1
        String[] inputValue_arrayString0 = {null, null, null};
        inputValue_rows.add(inputValue_arrayString0);
        //rous 2
        String[] inputValue_arrayString3 = {"123ab45", "123ab45", "123ab45"};
        inputValue_rows.add(inputValue_arrayString3);
        //rous 3
        String[] inputValue_arrayString2 = {"123aa45", "123aa45", "123aa45"};
        inputValue_rows.add(inputValue_arrayString2);
        //rous 4
        String[] inputValue_arrayString5 = {"aa123bb45", "aa123bb45", "aa123bb45"};
        inputValue_rows.add(inputValue_arrayString5);
        //rous 5
        String[] inputValue_arrayString6 = {"ab123bb45", "ab123bb45", "ab123bb45"};
        inputValue_rows.add(inputValue_arrayString6);
        //rous 6
        inputValue_rows.add(inputValue_arrayString3);

        Task1Impl task1 = new Task1Impl();
        task1.sort(inputValue_rows, 0);

        assertEquals(expectedValue_rows, inputValue_rows);

    }

}

