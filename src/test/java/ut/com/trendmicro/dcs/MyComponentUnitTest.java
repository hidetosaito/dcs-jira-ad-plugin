package ut.com.trendmicro.dcs;

import org.junit.Test;
import com.trendmicro.dcs.MyPluginComponent;
import com.trendmicro.dcs.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}