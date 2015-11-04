package com.pmdm.register;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.pmdm.register.Register.*;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RegisterUnitTest {

    private static int _id = 0;
    private static String name = "";
    private static double value = 0.0;

    private static Register register;
    private static String json;


    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code
        _id = 1;
        name = "Aitor";
        value = 99.99;
        register = new Register(_id, name, value);
        json = "{_id:"+_id+",name:\""+name+"\",value:"+value+"}";
    }

    @Test
    public void createFromJSON_isCorrect() throws Exception {
        System.out.println("@Test - createFromJSON_isCorrect");
        Register r = Register.createFromJSON(json);
        assertEquals("Are the same", register.equals(r), true);
    }

    @Test
    public void getJSON_isCorrect() throws Exception {
        System.out.println("@Test - getJSON_isCorrect");
        JSONObject object = new JSONObject(register.getJSON());
        assertEquals("Same _id", object.getInt(REGISTER_ID), _id);
        assertEquals("Same name", object.getString(REGISTER_NAME), name);
        assertEquals("Same value", object.getDouble(REGISTER_VALUE), value, 0.0);
    }

}
