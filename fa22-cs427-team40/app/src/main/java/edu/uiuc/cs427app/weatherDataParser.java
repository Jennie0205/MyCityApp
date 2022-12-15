package edu.uiuc.cs427app;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * https://openweathermap.org/current
 * The class is for getting data of weather
 */
public class weatherDataParser {
    private String mTemperature, mcity,mWeatherType, icon;
    private String mCondition;
    private String mHumidity;
    private int offset;

    /**
     * The method for weatherData
     * @param jsonObject
     * @return weatherData of temperature, city name, and weather condition
     */
    public static weatherDataParser fromJson(JSONObject jsonObject)
    {

        try
        {
            weatherDataParser weatherD=new weatherDataParser();
            weatherD.mcity=jsonObject.getString("name");

            weatherD.mWeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            weatherD.icon=jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");

            weatherD.mCondition=String.valueOf(jsonObject.getJSONObject("wind").getDouble("speed"));

            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.mTemperature=Integer.toString(roundedValue);

            weatherD.mHumidity=Double.toString(jsonObject.getJSONObject("main").getDouble("humidity"));
            weatherD.offset=jsonObject.getInt("timezone");
            return weatherD;
        }

        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Getting temperature
     * @return temperature in String
     */
    public String getmTemperature() {
        return mTemperature+"°C";
    }

    /**
     * Getting City name
     * @return city name in String
     */
    public String getMcity() {
        return mcity;
    }

    /**
     * Getting weather type
     * @return weather type in String
     */
    public String getmWeatherType() {
        return mWeatherType;
    }

    /**
     * Getting weather icon
     * @return weather icon code as a String
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Gets humidity
     * @return humidity as a String
     */
    public String getmHumidity() {
        return mHumidity;
    }

    /**
     * Get Offset
     * @return offset as int
     */
    public int getOffset() {return offset;}

    /**
     * Get wind condition
     * @return wind condition as a String
     */
    public String getWindCondition() {
        return mCondition;
    }

}
