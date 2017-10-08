package com.mapbox.reactnativemapboxgl;

import com.mapbox.mapboxsdk.maps.MapboxMap;

abstract class RNMGLLayer {
    String _layerId = null;
    boolean _isAddedToMap = false;

    String GetId() { return _layerId; }

    public abstract void addToMap(MapboxMap map);
    public abstract void removeFromMap(MapboxMap map);
}
