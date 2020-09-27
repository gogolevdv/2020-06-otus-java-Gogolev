package ru.otus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jdk.jfr.Description;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestMyGson {

    @Test
    public void testAddField1() {

        String string = "StringValue";
        Integer integer = 10;
        Boolean boolval = true;
        Long longval = 101L;
        int intvalue = 42;
        char charvalue = 's';
        double doubleval = 12.3D;
        short shortval = 1;
        float floatval = 33.44F;



        final Map<String, Object> config = Collections.emptyMap();
        final JsonBuilderFactory factory = Json.createBuilderFactory(config);
        final JsonObjectBuilder builder = factory.createObjectBuilder();

        MyGson.addField(builder,"TestKeyString",string,true);
        MyGson.addField(builder,"TestKeyInteger",integer,true);
        MyGson.addField(builder,"TestKeyBoolean",boolval,true);
        MyGson.addField(builder,"TestKeyLong",longval,true);
        MyGson.addField(builder,"TestKeyInt",intvalue,true);
        MyGson.addField(builder,"TestKeyChar",charvalue,true);
        MyGson.addField(builder,"TestKeyDouble",doubleval,true);
        MyGson.addField(builder,"TestKeyShort",shortval,true);
        MyGson.addField(builder,"TestKeyFloat",floatval,true);
        javax.json.JsonObject actualJson = builder.build();
        String expectedjsonString = "{" +
                "\"TestKeyString\": \"StringValue\"," +
                "\"TestKeyInteger\": 10," +
                "\"TestKeyBoolean\": true," +
                "\"TestKeyLong\": 101," +
                "\"TestKeyInt\": 42," +
                "\"TestKeyChar\": \"s\"," +
                "\"TestKeyDouble\": 12.3," +
                "\"TestKeyShort\": 1," +
                "\"TestKeyFloat\": 33.439998626708984" +
                "}";
        JsonObject expectedJson = new Gson().fromJson(expectedjsonString, JsonObject.class);
        assertEquals(expectedJson.toString(), actualJson.toString());
    }

    @Test
    public void testAddField2() {

        char[] charArray = {'w','r','6','8','n','K'};
        boolean[] booleanArray = {true,false,true,false,true,false};


        final Map<String, Object> config = Collections.emptyMap();
        final JsonBuilderFactory factory = Json.createBuilderFactory(config);
        final JsonObjectBuilder builder = factory.createObjectBuilder();

        MyGson.addField(builder,charArray.getClass().getName(),charArray,true);
        MyGson.addField(builder,booleanArray.getClass().getName(),booleanArray,true);
        javax.json.JsonObject actualJson = builder.build();
        String expectedjsonString = "{" +
                "\"[C\":[\"w\",\"r\",\"6\",\"8\",\"n\",\"K\"]," +
                "\"[Z\":[true,false,true,false,true,false]" +
                "}";
        JsonObject expectedJson = new Gson().fromJson(expectedjsonString, JsonObject.class);
        assertEquals(expectedJson.toString(), actualJson.toString());


    }

    @Test
    public void testAddField3() {

        var arrList1 = new ArrayList<String>();
        var arrList2 = new ArrayList<Integer>();
        var arrList3 = new ArrayList<Boolean>();

        arrList1.add("sadfas");
        arrList1.add("sdfasfd");
        arrList1.add("sfasdfsa");
        arrList1.add("sdfasdfa");

        arrList2.add(13);
        arrList2.add(15);
        arrList2.add(24);
        arrList2.add(42);

        arrList3.add(true);
        arrList3.add(false);
        arrList3.add(true);
        arrList3.add(false);

        final Map<String, Object> config = Collections.emptyMap();
        final JsonBuilderFactory factory = Json.createBuilderFactory(config);
        final JsonObjectBuilder builder = factory.createObjectBuilder();

        MyGson.addField(builder,"TestArrayString",arrList1,true);
        MyGson.addField(builder,"TestArrayInteger",arrList2,true);
        MyGson.addField(builder,"TestArrayBoolean",arrList3,true);
        javax.json.JsonObject actualJson = builder.build();
        String expectedjsonString = "{" +
                "\"TestArrayString\":[\"sadfas\",\"sdfasfd\",\"sfasdfsa\",\"sdfasdfa\"]," +
                "\"TestArrayInteger\":[13,15,24,42]," +
                "\"TestArrayBoolean\":[true,false,true,false]" +
                "}";
        JsonObject expectedJson = new Gson().fromJson(expectedjsonString, JsonObject.class);
        assertEquals(expectedJson.toString(), actualJson.toString());


    }

    @Test
    public void test() {
        var gson = new Gson();
        MyGson serializer = new MyGson();
        assertEquals(gson.toJson(null), serializer.toJson(null));
        assertEquals(gson.toJson((byte)1), serializer.toJson((byte)1));
        assertEquals(gson.toJson((short)1f), serializer.toJson((short)1f));
        assertEquals(gson.toJson(1), serializer.toJson(1));
        assertEquals(gson.toJson(1L), serializer.toJson(1L));
        assertEquals(gson.toJson(1f), serializer.toJson(1f));
        assertEquals(gson.toJson(1d), serializer.toJson(1d));
        assertEquals(gson.toJson("aaa"), serializer.toJson("aaa"));
        assertEquals(gson.toJson('a'), serializer.toJson('a'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), serializer.toJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2 ,3)), serializer.toJson(List.of(1, 2 ,3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.toJson(Collections.singletonList(1)));
    }
}
