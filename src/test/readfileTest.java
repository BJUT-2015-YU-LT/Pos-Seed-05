import static org.junit.Assert.*;

/**
 * Created by mrcai on 2016/1/11.
 */
public class readfileTest {
    readfile r;
    @org.junit.Before
    public void setUp() throws Exception {
        r=new readfile();
    }

    @org.junit.Test
    public void testReadRequirement() throws Exception {
        assertEquals(0,r.readRequirement("../requirement4-index.txt","index"));
        assertEquals(0,r.readRequirement("../requirement4-list.txt","list"));
    }

    @org.junit.Test
    public void testOutList() throws Exception {
       // assertEquals(0,r.OutList(""));
    }

    @org.junit.Test
    public void testOutIndex() throws Exception {

    }

    @org.junit.Test
    public void testTotal() throws Exception {
    }

    @org.junit.Test
    public void testSave() throws Exception {
        assertEquals(0,r.readRequirement("../requirement4-list.txt","list"));
    }
}