/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.google.adapter;

import android.Manifest;
import android.content.Context;
import android.view.View;

import com.car2go.maps.AnyMap;
import com.car2go.maps.BitmapDescriptorFactory;
import com.car2go.maps.CameraUpdate;
import com.car2go.maps.CameraUpdateFactory;
import com.car2go.maps.Projection;
import com.car2go.maps.UiSettings;
import com.car2go.maps.google.R;
import com.car2go.maps.model.CameraPosition;
import com.car2go.maps.model.Circle;
import com.car2go.maps.model.CircleOptions;
import com.car2go.maps.model.Marker;
import com.car2go.maps.model.MarkerOptions;
import com.car2go.maps.model.Polygon;
import com.car2go.maps.model.PolygonOptions;
import com.car2go.maps.model.Polyline;
import com.car2go.maps.model.PolylineOptions;
import com.google.android.libraries.maps.GoogleMap;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.MapStyleOptions;

import androidx.annotation.RequiresPermission;

/**
 * Implementation of {@link AnyMap} which works with {@link com.google.android.libraries.maps.GoogleMap}
 */
public class GoogleMapAdapter implements AnyMap {

	private final GoogleMap map;
	private final DrawableComponentFactory drawableComponentFactory;
	private final Context context;
	private final BitmapDescriptorFactory bitmapDescriptorFactory;

	public GoogleMapAdapter(GoogleMap map, Context context) {
		this.map = map;

		drawableComponentFactory = new DrawableComponentFactory(map);
		bitmapDescriptorFactory = new com.car2go.maps.google.BitmapDescriptorFactory(context);
		this.context = context;
	}

	@Override
	public void moveCamera(CameraUpdate cameraUpdate) {
		map.moveCamera(
				((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate
		);
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate) {
		map.animateCamera(
				((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate
		);
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate, CancelableCallback callback) {
		map.animateCamera(
				((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate,
				new CancellableCallbackAdapter(callback)
		);
	}

	@Override
	public void animateCamera(CameraUpdate cameraUpdate, int duration, final CancelableCallback callback) {
		map.animateCamera(
				((CameraUpdateAdapter) cameraUpdate).wrappedCameraUpdate,
				duration,
				new CancellableCallbackAdapter(callback)
		);
	}

	@Override
	public CameraPosition getCameraPosition() {
		return AnyMapAdapter.adapt(map.getCameraPosition());
	}

	@Override
	public Projection getProjection() {
		return AnyMapAdapter.adapt(map.getProjection());
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
		return AnyMapAdapter.adapt(map.getUiSettings());
	}

	@Override
	public void setOnMapClickListener(final OnMapClickListener listener) {
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				com.car2go.maps.model.LatLng anyLatLng = AnyMapAdapter.adapt(latLng);

				listener.onMapClick(anyLatLng);
			}
		});
	}

	@Override
	public void setOnMapLongClickListener(final OnMapLongClickListener listener) {
		map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			@Override
			public void onMapLongClick(LatLng latLng) {
				com.car2go.maps.model.LatLng anyLatLng = AnyMapAdapter.adapt(latLng);

				listener.onMapLongClick(anyLatLng);
			}
		});
	}

	@Override
	public void setOnCameraIdleListener(final OnCameraIdleListener listener) {
		map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
			@Override
			public void onCameraIdle() {
				listener.onCameraIdle();
			}
		});
	}

	@Override
	public void setOnCameraMoveListener(final OnCameraMoveListener listener) {
		map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
			@Override
			public void onCameraMove() {
				listener.onCameraMove();
			}
		});
	}

	@Override
	public void setOnCameraMoveStartedListener(final OnCameraMoveStartedListener listener) {
		map.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
			@Override
			public void onCameraMoveStarted(int i) {
				listener.onCameraMoveStarted(i);
			}
		});
	}

	@Override
	public void setOnMarkerClickListener(final OnMarkerClickListener listener) {
		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(com.google.android.libraries.maps.model.Marker marker) {
				Marker anyMarker = AnyMapAdapter.adapt(marker);

				return listener.onMarkerClick(anyMarker);
			}
		});
	}

	@Override
	public void setInfoWindowAdapter(final InfoWindowAdapter adapter) {
		map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
			@Override
			public View getInfoWindow(com.google.android.libraries.maps.model.Marker marker) {
				Marker anyMarker = AnyMapAdapter.adapt(marker);

				return adapter.getInfoWindow(anyMarker);
			}

			@Override
			public View getInfoContents(com.google.android.libraries.maps.model.Marker marker) {
				Marker anyMarker = AnyMapAdapter.adapt(marker);

				return adapter.getInfoContents(anyMarker);
			}
		});
	}

	@Override
	public void setTrafficEnabled(boolean enabled) {
		map.setTrafficEnabled(enabled);
	}

	@Override
	public void setIndoorEnabled(boolean enabled) {
		map.setIndoorEnabled(enabled);
	}

	@RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
	@Override
	public void setMyLocationEnabled(boolean enabled) {
		map.setMyLocationEnabled(enabled);
	}

	@Override
	public void setMapType(Type type) {
		int googleMapType;

		switch (type) {
			case SATELLITE:
				googleMapType = GoogleMap.MAP_TYPE_SATELLITE;
				break;
			case HYBRID:
				googleMapType = GoogleMap.MAP_TYPE_HYBRID;
				break;
			case TERRAIN:
				googleMapType = GoogleMap.MAP_TYPE_TERRAIN;
				break;
			case NORMAL:
			default:
				googleMapType = GoogleMap.MAP_TYPE_NORMAL;
				break;
		}

		map.setMapType(googleMapType);
	}

	@Override
	public void setMapStyle(Style style) {
		switch (style) {
			case DARK:
				map.setMapStyle(
						MapStyleOptions.loadRawResourceStyle(context, R.raw.maps_night_mode));
				break;
			case NORMAL:
			default:
				map.setMapStyle(null);
				break;
		}
	}

	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		map.setPadding(left, top, right, bottom);
	}

	@Override
	public void onUserLocationChanged(com.car2go.maps.model.LatLng location, float accuracy) {
		//Do nothing
	}

	@Override
	public BitmapDescriptorFactory getBitmapDescriptorFactory() {
		return bitmapDescriptorFactory;
	}

	@Override
	public CameraUpdateFactory getCameraUpdateFactory() {
		return com.car2go.maps.google.CameraUpdateFactory.getInstance();
	}

	/**
	 * Delegates callbacks from Google map to given AnyMap callback
	 */
	private static class CancellableCallbackAdapter implements GoogleMap.CancelableCallback {

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
