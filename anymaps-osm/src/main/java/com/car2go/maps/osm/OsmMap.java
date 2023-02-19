/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.osm;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.car2go.maps.AnyMap;
import com.car2go.maps.BitmapDescriptorFactory;
import com.car2go.maps.CameraUpdate;
import com.car2go.maps.CameraUpdateFactory;
import com.car2go.maps.Projection;
import com.car2go.maps.UiSettings;
import com.car2go.maps.model.CameraPosition;
import com.car2go.maps.model.Circle;
import com.car2go.maps.model.CircleOptions;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.Marker;
import com.car2go.maps.model.MarkerOptions;
import com.car2go.maps.model.Polygon;
import com.car2go.maps.model.PolygonOptions;
import com.car2go.maps.model.Polyline;
import com.car2go.maps.model.PolylineOptions;
import com.car2go.maps.osm.drawable.DrawableComponentFactory;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.views.MapView;

import static com.car2go.maps.osm.util.OsmUtils.toLatLng;

/**
 * Implementation of {@link AnyMap} which works with Open Street Maps
 */
class OsmMap implements AnyMap {

    private final org.osmdroid.views.MapView map;

    private final CameraUpdateHandler cameraUpdateHandler;
    private final MyLocationHandler myLocationHandler;
    private final DrawableComponentFactory drawableComponentFactory;
    private Context context;
    private final UiSettings uiSettings;

    private OnMapClickListener onMapClickListener = OnMapClickListener.NULL;
    private OnMapLongClickListener onMapLongClickListener = OnMapLongClickListener.NULL;

    private boolean mapEnabled = true;

    OsmMap(MapView map, Context context) {
        this.map = map;

        cameraUpdateHandler = new CameraUpdateHandler(map);
        myLocationHandler = new MyLocationHandler(map);
        drawableComponentFactory = new DrawableComponentFactory(map);
        this.context = context;
        uiSettings = new OsmUiSettings();

        map.setOnTouchListener(new MapTouchListener(map.getContext()));
        setMapStyle(Style.NORMAL);
    }

    @Override
    public void moveCamera(CameraUpdate cameraUpdate) {
        cameraUpdateHandler.moveCamera(cameraUpdate);
    }

    @Override
    public void animateCamera(CameraUpdate cameraUpdate) {
        cameraUpdateHandler.animateCamera(cameraUpdate);
    }

    @Override
    public void animateCamera(CameraUpdate cameraUpdate, CancelableCallback callback) {
        cameraUpdateHandler.animateCamera(cameraUpdate, callback);
    }

    @Override
    public void animateCamera(CameraUpdate cameraUpdate, int duration, CancelableCallback callback) {
        cameraUpdateHandler.animateCamera(cameraUpdate, duration, callback);
    }

    @Override
    public CameraPosition getCameraPosition() {
        return currentCameraPosition();
    }

    @Override
    public Projection getProjection() {
        org.osmdroid.views.Projection projection = map.getProjection();

        return new OsmProjection(projection);
    }

    @Override
    public Marker addMarker(MarkerOptions options) {
        return drawableComponentFactory.addMarker(options);
    }

    @Override
    public Circle addCircle(CircleOptions options) {
        return drawableComponentFactory.addCircle(options);
    }

    @Override
    public Polygon addPolygon(PolygonOptions options) {
        return drawableComponentFactory.addPolygon(options);
    }

    @Override
    public Polyline addPolyline(PolylineOptions options) {
        return drawableComponentFactory.addPolyline(options);
    }

    @Override
    public UiSettings getUiSettings() {
        return uiSettings;
    }

    @Override
    public void setOnMapClickListener(OnMapClickListener listener) {
        onMapClickListener = (listener == null)
                ? OnMapClickListener.NULL
                : listener;
    }

    @Override
    public void setOnMapLongClickListener(OnMapLongClickListener listener) {
        onMapLongClickListener = (listener == null)
                ? OnMapLongClickListener.NULL
                : listener;
    }

    @Override
    public void setOnCameraIdleListener(OnCameraIdleListener listener) {
        map.addMapListener(new DelayedMapListener(new OsmMapListener(listener), 50));
    }

    @Override
    public void setOnCameraMoveListener(OnCameraMoveListener listener) {
        map.addMapListener(new OsmMapMoveListener(listener));
    }

    @Override
    public void setOnCameraMoveStartedListener(OnCameraMoveStartedListener listener) {
        map.addMapListener(new OsmCameraMoveStartedListener(listener));
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        drawableComponentFactory.setOnMarkerClickListener(listener);
    }

    @Override
    public void setInfoWindowAdapter(InfoWindowAdapter adapter) {
        // Do nothing
    }

    @Override
    public void setTrafficEnabled(boolean enabled) {
        // Do nothing
    }

    @Override
    public void setIndoorEnabled(boolean enabled) {
        // Do nothing
    }

    @Override
    public void setMyLocationEnabled(boolean enabled) {
        myLocationHandler.setMyLocationEnabled(enabled);
    }

    @Override
    public void setMapType(Type type) {
        // Do nothing
    }

    @Override
    public void setMapStyle(Style style) {
        switch (style) {
            case DARK:
                map.setTileSource(getTileSource(true));
                break;
            case NORMAL:
            default:
                map.setTileSource(getTileSource(false));
                break;
        }

    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        map.setTranslationY((top - bottom) / 2f);
        map.setTranslationX((left - right) / 2f);
    }

    @Override
    public void onUserLocationChanged(LatLng location, float accuracy) {
        //Do nothing
    }

    @Override
    public BitmapDescriptorFactory getBitmapDescriptorFactory() {
        return com.car2go.maps.osm.BitmapDescriptorFactory.getInstance();
    }

    @Override
    public CameraUpdateFactory getCameraUpdateFactory() {
        return com.car2go.maps.osm.CameraUpdateFactory.getInstance();
    }

    private XYTileSource getTileSource(boolean dark) {
        int density = (int) Math.ceil(context.getResources().getDisplayMetrics().density);
        density = Math.min(density, 8); // maximum supported size is 8x

        String suffix = density > 1 ? "@" + density + "x.png" : ".png";
        int tileSize = density * 256;

        String style = dark ? "dark_all" : "voyager";
        return new XYTileSource("Carto", 1, 19, tileSize, suffix, new String[]{
                "https://a.basemaps.cartocdn.com/rastertiles/" + style + "/",
                "https://b.basemaps.cartocdn.com/rastertiles/" + style + "/",
                "https://c.basemaps.cartocdn.com/rastertiles/" + style + "/",
        }, "Map data © OpenStreetMap | Tiles © Carto");
    }

    private CameraPosition currentCameraPosition() {
        final IGeoPoint center = map.getMapCenter();
        final int zoomLevel = map.getZoomLevel();

        return new CameraPosition(
                new LatLng(center.getLatitude(), center.getLongitude()),
                zoomLevel
        );
    }

    /**
     * Detects clicks and long-clicks on map
     */
    private class MapTouchListener implements View.OnTouchListener, GestureDetector.OnGestureListener {

        private GestureDetector gestureDetector;

        public MapTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, this);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!mapEnabled) {
                return true;
            }

            gestureDetector.onTouchEvent(event);

            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onMapClickListener.onMapClick(touchPoint(e));

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            onMapLongClickListener.onMapLongClick(touchPoint(e));
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        private LatLng touchPoint(MotionEvent e) {
            IGeoPoint geoPoint = map.getProjection().fromPixels(
                    (int) e.getX(),
                    (int) e.getY()
            );
            return toLatLng(geoPoint);
        }

    }

    /**
     * Listens for map position changes and delegates them
     * to {@link com.car2go.maps.AnyMap.OnCameraIdleListener}
     */
    private class OsmMapListener implements MapListener {

        private final OnCameraIdleListener listener;

        private OsmMapListener(OnCameraIdleListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onScroll(ScrollEvent event) {
            notifyListener();
            return false;
        }

        @Override
        public boolean onZoom(ZoomEvent event) {
            notifyListener();
            return false;
        }

        private void notifyListener() {
            listener.onCameraIdle();
        }

    }

    /**
     * Listens for map position changes and delegates them
     * to {@link com.car2go.maps.AnyMap.OnCameraIdleListener}
     */
    private class OsmMapMoveListener implements MapListener {

        private final OnCameraMoveListener listener;

        private OsmMapMoveListener(OnCameraMoveListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onScroll(ScrollEvent event) {
            notifyListener();
            return false;
        }

        @Override
        public boolean onZoom(ZoomEvent event) {
            notifyListener();
            return false;
        }

        private void notifyListener() {
            listener.onCameraMove();
        }

    }

    /**
     * Listens for map position changes and delegates them
     * to {@link com.car2go.maps.AnyMap.OnCameraMoveStartedListener}
     */
    private class OsmCameraMoveStartedListener implements MapListener {

        private final OnCameraMoveStartedListener listener;

        private OsmCameraMoveStartedListener(OnCameraMoveStartedListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onScroll(ScrollEvent event) {
            notifyListener();
            return false;
        }

        @Override
        public boolean onZoom(ZoomEvent event) {
            notifyListener();
            return false;
        }

        private void notifyListener() {
            listener.onCameraMoveStarted(OnCameraMoveStartedListener.REASON_GESTURE);
        }

    }

    /**
     * UI settings for OpenStreetMap
     */
    private class OsmUiSettings implements UiSettings {

        @Override
        public void setAllGesturesEnabled(final boolean enabled) {
            mapEnabled = enabled;
        }

        @Override
        public void setTiltGesturesEnabled(boolean enabled) {
            // Do nothing
        }

        public void setRotateGesturesEnabled(boolean enabled) {
            // Do nothing
        }

        @Override
        public void setMyLocationButtonEnabled(boolean enabled) {
            // Do nothing
        }

        @Override
        public void setMapToolbarEnabled(boolean enabled) {
            // Do nothing
        }

        @Override
        public void setIndoorLevelPickerEnabled(boolean enabled) {
            // Do nothing
        }
    }

}
