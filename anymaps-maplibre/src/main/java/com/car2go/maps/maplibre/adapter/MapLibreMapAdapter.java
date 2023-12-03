/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;

import com.car2go.maps.AnyMap;
import com.car2go.maps.BitmapDescriptorFactory;
import com.car2go.maps.CameraUpdate;
import com.car2go.maps.CameraUpdateFactory;
import com.car2go.maps.Projection;
import com.car2go.maps.UiSettings;
import com.car2go.maps.maplibre.R;
import com.car2go.maps.model.CameraPosition;
import com.car2go.maps.model.Circle;
import com.car2go.maps.model.CircleOptions;
import com.car2go.maps.model.Marker;
import com.car2go.maps.model.MarkerOptions;
import com.car2go.maps.model.Polygon;
import com.car2go.maps.model.PolygonOptions;
import com.car2go.maps.model.Polyline;
import com.car2go.maps.model.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.RasterLayer;
import com.mapbox.mapboxsdk.style.sources.RasterSource;
import com.mapbox.mapboxsdk.style.sources.TileSet;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

/**
 * Implementation of {@link AnyMap} which works with {@link MapboxMap}
 */
public class MapLibreMapAdapter implements AnyMap, Style.OnStyleLoaded {

	private final MapboxMap map;
	private final MapView mapView;
	private DrawableComponentFactory drawableComponentFactory;
	private final com.car2go.maps.maplibre.BitmapDescriptorFactory bitmapDescriptorFactory;
	private CameraUpdateFactory cameraUpdateFactory;
	private final Context context;
	private final AnyMapAdapter anyMapAdapter;
	private Type mapType = Type.NORMAL;
	private Style mapStyle = Style.NORMAL;
	private boolean location = false;
	public com.mapbox.mapboxsdk.maps.Style.OnStyleLoaded callback = null;
	private final String jawgAccessToken;
	private final String arcgisAccessToken;

	public MapLibreMapAdapter(MapboxMap map, MapView mapView, Context context) {
		this.map = map;
		this.mapView = mapView;

		bitmapDescriptorFactory = new com.car2go.maps.maplibre.BitmapDescriptorFactory(context, map);
		this.context = context;
		this.anyMapAdapter = new AnyMapAdapter(context, bitmapDescriptorFactory, map);
		this.cameraUpdateFactory = new com.car2go.maps.maplibre.CameraUpdateFactory(anyMapAdapter);

		map.getUiSettings().setCompassGravity(Gravity.START | Gravity.TOP);

		ApplicationInfo app;
		try {
			app = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException(e);
		}
		Bundle metadata = app.metaData;
		this.jawgAccessToken = metadata.getString("io.jawg.ACCESS_TOKEN");
		this.arcgisAccessToken = metadata.getString("com.arcgis.ACCESS_TOKEN");

		updateMapStyle();
	}

	@Override
	public void moveCamera(CameraUpdate cameraUpdate) {
		if (cameraUpdate instanceof ScrollByCameraUpdateAdapter) {
			map.scrollBy(((ScrollByCameraUpdateAdapter) cameraUpdate).distanceX, ((ScrollByCameraUpdateAdapter) cameraUpdate).distanceY);
		} else {
			map.moveCamera(
					((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate
			);
		}
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate) {
		if (cameraUpdate instanceof ScrollByCameraUpdateAdapter) {
			map.scrollBy(((ScrollByCameraUpdateAdapter) cameraUpdate).distanceX, ((ScrollByCameraUpdateAdapter) cameraUpdate).distanceY, 300);
		} else {
			map.animateCamera(
					((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate
			);
		}
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate, CancelableCallback callback) {
		if (cameraUpdate instanceof ScrollByCameraUpdateAdapter) {
			map.scrollBy(((ScrollByCameraUpdateAdapter) cameraUpdate).distanceX, ((ScrollByCameraUpdateAdapter) cameraUpdate).distanceY, 300);
		} else {
			map.animateCamera(
					((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate,
					new CancellableCallbackAdapter(callback)
			);
		}
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate, int duration, final CancelableCallback callback) {
		if (cameraUpdate instanceof ScrollByCameraUpdateAdapter) {
			map.scrollBy(((ScrollByCameraUpdateAdapter) cameraUpdate).distanceX, ((ScrollByCameraUpdateAdapter) cameraUpdate).distanceY, duration);
		} else {
			map.animateCamera(
					((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate,
					duration,
					new CancellableCallbackAdapter(callback)
			);
		}
	}

	@Override
	public CameraPosition getCameraPosition() {
		return anyMapAdapter.map(map.getCameraPosition());
	}

	@Override
	public Projection getProjection() {
		return anyMapAdapter.map(map.getProjection());
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
		return anyMapAdapter.map(map.getUiSettings());
	}

	@Override
	public void setOnMapClickListener(final OnMapClickListener listener) {
		map.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
			@Override
			public boolean onMapClick(@NonNull LatLng latLng) {
				com.car2go.maps.model.LatLng anyLatLng = anyMapAdapter.map(latLng);

				listener.onMapClick(anyLatLng);
				return true;
			}
		});
	}

	@Override
	public void setOnMapLongClickListener(final OnMapLongClickListener listener) {
		map.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
			@Override
			public boolean onMapLongClick(@NonNull LatLng latLng) {
				com.car2go.maps.model.LatLng anyLatLng = anyMapAdapter.map(latLng);

				listener.onMapLongClick(anyLatLng);
				return true;
			}
		});
	}

	@Override
	public void setOnCameraIdleListener(final OnCameraIdleListener listener) {
		map.addOnCameraIdleListener(new MapboxMap.OnCameraIdleListener() {
			@Override
			public void onCameraIdle() {
				listener.onCameraIdle();
			}
		});
	}

	@Override
	public void setOnCameraMoveListener(final OnCameraMoveListener listener) {
		map.addOnCameraMoveListener(new MapboxMap.OnCameraMoveListener() {
			@Override
			public void onCameraMove() {
				listener.onCameraMove();
			}
		});
	}

	@Override
	public void setOnCameraMoveStartedListener(final OnCameraMoveStartedListener listener) {
		map.addOnCameraMoveStartedListener(new MapboxMap.OnCameraMoveStartedListener() {
			@Override
			public void onCameraMoveStarted(int reason) {
				listener.onCameraMoveStarted(reason);
			}
		});
	}

	@Override
	public void setOnMarkerClickListener(final OnMarkerClickListener listener) {
		map.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(@NonNull com.mapbox.mapboxsdk.annotations.Marker marker) {
				Marker m = drawableComponentFactory.markers.get(marker.getId());
				return listener.onMarkerClick(m);
			}
		});
	}

	@Override
	public void setInfoWindowAdapter(final InfoWindowAdapter adapter) {
		// do nothing
	}

	@Override
	public void setTrafficEnabled(boolean enabled) {
		// do nothing
	}

	private void updateMapStyle() {
		String style = getStyle();
		map.setStyle(style, this);
	}

	private String getStyle() {
		String style;
		switch (this.mapType) {
			case SATELLITE:
			case HYBRID:
				style = "https://tile.jawg.io/jawg-satellite-streets.json";
				break;
			case TERRAIN:
				style = "https://tile.jawg.io/jawg-terrain.json";
				break;
			case NORMAL:
			default:
				if (mapStyle == Style.DARK) {
					style = "https://tile.jawg.io/jawg-dark.json";
				} else {
					style = "https://tile.jawg.io/jawg-streets.json";
				}
				break;
		}
		style += "?access-token=" + jawgAccessToken;
		return style;
	}

	@Override
	public void setIndoorEnabled(boolean enabled) {
		// do nothing
	}

	@RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
	@Override
	public void setMyLocationEnabled(boolean enabled) {
		location = enabled;
		if (enabled) {
			enableLocation();
		} else {
			disableLocation();
		}
	}

	@RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
	private void disableLocation() {
		if (map.getLocationComponent().isLocationComponentActivated()) {
			map.getLocationComponent().setLocationComponentEnabled(false);
		}
	}

	@RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
	private void enableLocation() {
		if (map.getStyle() != null && map.getStyle().isFullyLoaded()) {
			LocationComponentActivationOptions options = LocationComponentActivationOptions
					.builder(context, map.getStyle()).build();
			map.getLocationComponent().activateLocationComponent(options);
			map.getLocationComponent().setLocationComponentEnabled(true);
		}
	}

	@Override
	public void setMapType(Type type) {
		this.mapType = type;
		updateMapStyle();
	}

	@Override
	public void setMapStyle(Style style) {
		this.mapStyle = style;
		updateMapStyle();
	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		// emulate Google Maps behavior: default padding + custom padding
		int base = context.getResources().getDimensionPixelSize(R.dimen.maplibre_four_dp);
		map.getUiSettings().setCompassMargins(base, base + top, base + right, base);
		map.getUiSettings().setAttributionMargins(base + left, base, base, base + bottom);
		map.getUiSettings().setLogoMargins(base + left, base, base, base + bottom);
	}

	@Override
	public void onUserLocationChanged(com.car2go.maps.model.LatLng location, float accuracy) {
		//Do nothing
	}

	@Override
	public void setOnMapLoadedCallback(OnMapLoadedCallback callback) {
		// not supported
	}

	@Override
	public BitmapDescriptorFactory getBitmapDescriptorFactory() {
		return bitmapDescriptorFactory;
	}

	@Override
	public CameraUpdateFactory getCameraUpdateFactory() {
		return cameraUpdateFactory;
	}

	@Override
	public void onStyleLoaded(@NonNull com.mapbox.mapboxsdk.maps.Style style1) {
		com.mapbox.mapboxsdk.maps.Style style = map.getStyle();
		if (style == null || !style.isFullyLoaded()) return;

		if (style.getSource("satellite-v1") != null && arcgisAccessToken != null) {
			// Jawg Satellite imagery only supports a few countries. Replace it with ArcGIS imagery which has worldwide coverage.
			String tileUri = "https://ibasemaps-api.arcgis.com/arcgis/rest/services/World_Imagery/MapServer/tile/{z}/{y}/{x}";
			tileUri += "?token=" + arcgisAccessToken;
			TileSet tileset = new TileSet("{\n" +
					"  \"minzoom\":0,\n" +
					"  \"maxzoom\":23,\n" +
					"}", tileUri);
			tileset.setAttribution("<a href='http://goto.arcgisonline.com/maps/World_Imagery'>&copy; Esri, Maxar, GeoEye, Earthstar Geographics, CNES/Airbus DS, USDA, USGS, AeroGRID, IGN, and the GIS User Community</a>");
			RasterSource source = new RasterSource("satellite2", tileset, 256);
			style.addSource(source);

			style.removeLayer("satellite");
			style.addLayerAbove(new RasterLayer("satellite", "satellite2").withSourceLayer("satellite2"), "water");
		}

		if (this.drawableComponentFactory == null) {
			this.drawableComponentFactory = new DrawableComponentFactory(this.anyMapAdapter, map, mapView, style);
			anyMapAdapter.drawableComponentFactory = this.drawableComponentFactory;
		}

		if (location && (!map.getLocationComponent().isLocationComponentActivated()
				|| !map.getLocationComponent().isLocationComponentEnabled())) {
			enableLocation();
		}

		if (callback != null) {
			callback.onStyleLoaded(style);
			callback = null;
		}
	}

	/**
	 * Delegates callbacks from Google map to given AnyMap callback
	 */
	private static class CancellableCallbackAdapter implements MapboxMap.CancelableCallback {

		private final CancelableCallback callback;

		public CancellableCallbackAdapter(CancelableCallback callback) {
			this.callback = callback;
		}

		@Override
		public void onFinish() {
			callback.onFinish();
		}

		@Override
		public void onCancel() {
			callback.onCancel();
		}

	}
}
