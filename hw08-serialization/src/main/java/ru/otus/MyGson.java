package ru.otus;

import javax.json.*;
import java.lang.reflect.Field;
import java.util.*;

public class MyGson {

    public String toJson(Object obj) {
        if (obj == null) {
            return  "{}";
        }
        return toJson(obj, obj.getClass());
    }

    public String toJson(Object obj, Class<?> clazz) {

        Field[] fieldsAll = clazz.getDeclaredFields();

        final Map<String, Object> config = Collections.emptyMap();
        final JsonBuilderFactory factory = Json.createBuilderFactory(config);
        final JsonObjectBuilder jsonbuilder = factory.createObjectBuilder();

        Arrays.stream(fieldsAll).forEach((val) -> {

            val.setAccessible(true);

            try {

                addField(jsonbuilder, val.getName(), val.get(obj), false);

            } catch (IllegalAccessException e) {
                System.out.println("Нет доступа к полю: " + e.getMessage());
            }
        });

        javax.json.JsonObject actualJson = jsonbuilder.build();

        return actualJson.toString();

    }

    public static void addField(final JsonObjectBuilder builder, final String key, final Object value,
                                boolean allowNullValues) {
        if (value != null) {
            if (value instanceof String) {
                builder.add(key, (String) value);
            } else if (value instanceof Integer) {
                builder.add(key, (Integer) value);
            } else if (value instanceof Boolean) {
                builder.add(key, (Boolean) value);
            } else if (value instanceof Character) {
                builder.add(key, value.toString());
            } else if (value instanceof Byte) {
                builder.add(key, (Byte) value);
            } else if (value instanceof Short) {
                builder.add(key, (Short) value);
            } else if (value instanceof Double) {
                builder.add(key, (Double) value);
            } else if (value instanceof Float) {
                builder.add(key, (Float) value);
            } else if (value instanceof Long) {
                builder.add(key, (Long) value);
            } else if (value instanceof char[]) {
                JsonArrayBuilder charArryBuilder = charToJsonArray((char[])value);
                builder.add(key, charArryBuilder);
            } else if (value instanceof int[]) {
                JsonArrayBuilder intArryBuilder = intToJsonArray((int[])value);
                builder.add(key, intArryBuilder);
            } else if (value instanceof byte[]) {
                JsonArrayBuilder byteArryBuilder = byteToJsonArray((byte[])value);
                builder.add(key, byteArryBuilder);
            } else if (value instanceof short[]) {
                JsonArrayBuilder shortArryBuilder = shortToJsonArray((short[])value);
                builder.add(key, shortArryBuilder);
            } else if (value instanceof long[]) {
                JsonArrayBuilder longArryBuilder = longToJsonArray((long[])value);
                builder.add(key, longArryBuilder);
            } else if (value instanceof float[]) {
                JsonArrayBuilder floatArryBuilder = floatToJsonArray((float[])value);
                builder.add(key, floatArryBuilder);
            } else if (value instanceof double[]) {
                JsonArrayBuilder doubleArryBuilder = doubleToJsonArray((double[])value);
                builder.add(key, doubleArryBuilder);
            } else if (value instanceof boolean[]) {
                JsonArrayBuilder booleanArryBuilder = booleanToJsonArray((boolean[])value);
                builder.add(key, booleanArryBuilder);
            } else if (value instanceof Collection) {
                JsonArrayBuilder collArryBuilder = Json.createArrayBuilder((Collection) value);
                builder.add(key, collArryBuilder);
            } else {
                builder.add(key, value.toString());
            }
        } else if (allowNullValues) {
            builder.add(key, JsonValue.NULL);
        }
    }


    private static JsonArrayBuilder charToJsonArray(final char[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final char value : values) {
            builder.add(String.valueOf(value));
        }
        return builder;
    }

    private static JsonArrayBuilder intToJsonArray(final int[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final int value : values) {
            builder.add(value);
        }
        return builder;
    }

    private static JsonArrayBuilder byteToJsonArray(final byte[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final byte value : values) {
            builder.add(value);
        }
        return builder;
    }

    private static JsonArrayBuilder shortToJsonArray(final short[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final short value : values) {
            builder.add(value);
        }
        return builder;
    }

    private static JsonArrayBuilder longToJsonArray(final long[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final long value : values) {
            builder.add(value);
        }
        return builder;
    }

    private static JsonArrayBuilder floatToJsonArray(final float[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final float value : values) {
            builder.add(value);
        }
        return builder;
    }

    private static JsonArrayBuilder doubleToJsonArray(final double[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final double value : values) {
            builder.add(value);
        }
        return builder;
    }

    private static JsonArrayBuilder booleanToJsonArray(final boolean[] values) {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        for (final boolean value : values) {
            builder.add(value);
        }
        return builder;
    }

}
