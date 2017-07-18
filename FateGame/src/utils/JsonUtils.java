package utils;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils
{
	//boolean
	public static void putBoolean(JSONObject destination, String key, boolean value)
	{
		destination.put(key, value);
	}
	public static void putBoolean(JSONArray destination, boolean value)
	{
		destination.put(value);
	}
	public static boolean getBoolean(JSONObject source, String key)
	{
		return source.getBoolean(key);
	}
	public static boolean getBoolean(JSONArray source, int index)
	{
		return source.getBoolean(index);
	}
	
	//byte
	public static void putByte(JSONObject destination, String key, byte value)
	{
		destination.put(key, value);
	}
	public static void putByte(JSONArray destination, byte value)
	{
		destination.put(value);
	}
	public static byte getByte(JSONObject source, String key)
	{
		return (byte)source.getInt(key);
	}
	public static byte getByte(JSONArray source, int index)
	{
		return (byte)source.getInt(index);
	}
	
	//int
	public static void putInt(JSONObject destination, String key, int value)
	{
		destination.put(key, value);
	}
	public static void putInt(JSONArray destination, int value)
	{
		destination.put(value);
	}
	public static int getInt(JSONObject source, String key)
	{
		return source.getInt(key);
	}
	public static int getInt(JSONArray source, int index)
	{
		return source.getInt(index);
	}
	
	//float
	public static void putFloat(JSONObject destination, String key, float value)
	{
		destination.put(key, value);
	}
	public static void putFloat(JSONArray destination, float value)
	{
		destination.put(value);
	}
	public static float getFloat(JSONObject source, String key)
	{
		return (float)source.getDouble(key);
	}
	public static float getFloat(JSONArray source, int index)
	{
		return (float)source.getDouble(index);
	}
	
	//double
	public static void putDouble(JSONObject destination, String key, double value)
	{
		destination.put(key, value);
	}
	public static void putDouble(JSONArray destination, double value)
	{
		destination.put(value);
	}
	public static double getDouble(JSONObject source, String key)
	{
		return source.getDouble(key);
	}
	public static double getDouble(JSONArray source, int index)
	{
		return source.getDouble(index);
	}
	
	//long
	public static void putLong(JSONObject destination, String key, long value)
	{
		destination.put(key, value);
	}
	public static void putLong(JSONArray destination, long value)
	{
		destination.put(value);
	}
	public static long getLong(JSONObject source, String key)
	{
		return source.getLong(key);
	}
	public static long getLong(JSONArray source, int index)
	{
		return source.getLong(index);
	}
	
	//String
	public static void putString(JSONObject destination, String key, String value)
	{
		destination.put(key, value);
	}
	public static void putString(JSONArray destination, String value)
	{
		destination.put(value);
	}
	public static String getString(JSONObject source, String key)
	{
		return source.getString(key);
	}
	public static String getString(JSONArray source, int index)
	{
		return source.getString(index);
	}
	
	//JSONObject
	public static void putJSONObject(JSONObject destination, String key, JSONObject value)
	{
		destination.put(key, value);
	}
	public static void putJSONObject(JSONArray destination, JSONObject value)
	{
		destination.put(value);
	}
	public static JSONObject getJSONObject(JSONObject source, String key)
	{
		//return source.getJSONObject(key);
		return source.optJSONObject(key);
	}
	public static JSONObject getJSONObject(JSONArray source, int index)
	{
		//return source.getJSONObject(index);
		return source.optJSONObject(index);
	}
	
	//JSONArray
	public static void putJSONArray(JSONObject destination, String key, JSONArray value)
	{
		destination.put(key, value);
	}
	public static void putJSONArray(JSONArray destination, JSONArray value)
	{
		destination.put(value);
	}
	public static JSONArray getJSONArray(JSONObject source, String key)
	{
		//return source.getJSONArray(key);
		return source.optJSONArray(key);
	}
	public static JSONArray getJSONArray(JSONArray source, int index)
	{
		//return source.getJSONArray(index);
		return source.optJSONArray(index);
	}
	
	//Collection
	public static void putCollection(JSONObject destination, String key, Collection<?> value)
	{
		destination.put(key, value);
	}
	public static void putCollection(JSONArray destination, Collection<?> value)
	{
		destination.put(value);
	}
	public static JSONArray getCollection(JSONObject source, String key)
	{
		return source.optJSONArray(key);
	}
	public static JSONArray getCollection(JSONArray source, int index)
	{
		return source.optJSONArray(index);
	}
}
