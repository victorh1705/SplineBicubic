import fileManager.ExcelCreatorTest;
import fileManager.FileManagerTest;
import library.DerivativeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileManagerTest.class,
        DerivativeTest.class,
//        PivotingTest.class,
        ExcelCreatorTest.class
})
public class TestSuite {
}
