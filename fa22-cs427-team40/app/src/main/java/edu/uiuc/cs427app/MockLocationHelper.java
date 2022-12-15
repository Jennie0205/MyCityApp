package edu.uiuc.cs427app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.provider.ProviderProperties;
import android.os.Build;
import android.os.SystemClock;

public class MockLocationHelper {
    String provider;
    Context ctx;

    public MockLocationHelper(String name, Context ctx) {
        this.provider = name;
        this.ctx = ctx;

        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        try
        {
            lm.addTestProvider(provider, false, false, false, false, false,
                    true, true, ProviderProperties.POWER_USAGE_LOW, ProviderProperties.ACCURACY_COARSE);
            lm.setTestProviderEnabled(provider, true);
        } catch(SecurityException e) {
            throw new SecurityException("Need permission to run mock location");
        }
    }

    public void updateLocation(double lat, double lon) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);

        Location mockLocation = new Location(provider);
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setAccuracy(3F);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mockLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        lm.setTestProviderLocation(provider, mockLocation);
    }

    public void shutdown() {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        lm.removeTestProvider(provider);
    }
}
