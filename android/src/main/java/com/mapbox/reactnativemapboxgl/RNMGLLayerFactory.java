package com.mapbox.reactnativemapboxgl;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.NoSuchLayerException;
import com.mapbox.mapboxsdk.style.layers.RasterLayer;
import com.mapbox.mapboxsdk.style.sources.NoSuchSourceException;
import com.mapbox.mapboxsdk.style.sources.RasterSource;
import com.mapbox.mapboxsdk.style.sources.TileSet;

class RNMGLRasterLayer extends RNMGLLayer {
    private String _sourceId;
    private String _tileURL;

    private final static String TAG = "RNMGLLayerFactory";

    RNMGLRasterLayer(ReadableMap layer) {
        String baseId = layer.getString("id");
        _sourceId = baseId + "-source";
        _layerId = baseId + "-layer";

        ReadableMap properties = layer.getMap("properties");
        if (properties != null) {
            _tileURL = properties.getString("url");
        }
    }

    @Override
    public void addToMap(MapboxMap map) {
        if (map == null || _tileURL == null) {
            return;
        }

        // no way to update properties directly, so remove and re-add
        if (_isAddedToMap) {
            removeFromMap(map);
        }

        RasterSource rasterSource = new RasterSource(_sourceId, new TileSet("tileset", _tileURL));
        RasterLayer rasterLayer = new RasterLayer(_layerId, _sourceId);
        map.addSource(rasterSource);
        map.addLayer(rasterLayer);
        _isAddedToMap = true;
    }

    @Override
    public void removeFromMap(MapboxMap map) {
        if (!_isAddedToMap) {
            return;
        }

        try {
            map.removeSource(_sourceId);
        } catch (NoSuchSourceException e) {
            Log.d(TAG, "removeFromMap: " + e.getMessage());
        }
        try {
            map.removeLayer(_layerId);
        } catch (NoSuchLayerException e) {
            Log.d(TAG, "removeFromMap: " + e.getMessage());
        }
        _isAddedToMap = false;
    }
}

class RNMGLLayerFactory {
    static RNMGLLayer getLayerFromJS(ReadableMap layer) {
        String type = layer.getString("type");
        if (type.equals("raster")) {
            return new RNMGLRasterLayer(layer);
        }
        // TODO: Circle, Background, Fill, Line, Symbol, GeoJson...

        return null;
    }
}
