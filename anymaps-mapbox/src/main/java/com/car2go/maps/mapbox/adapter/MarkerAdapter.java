/*
 * Copyright (c) 2016 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.car2go.maps.model.BitmapDescriptor;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.Marker;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Projection;

/**
 * Adapts Mapbox Marker to AnyMap Marker
 */
public class MarkerAdapter implements Marker, MapboxMap.OnCameraMoveListener {

	private DrawableComponentFactory drawableComponentFactory;
	private final com.mapbox.mapboxsdk.annotations.Marker marker;
	private final AnyMapAdapter anyMapAdapter;
	private final MapboxMap map;
	private com.mapbox.mapboxsdk.geometry.LatLng actualPosition;
	private float anchorU;
	private float anchorV;

	public MarkerAdapter(DrawableComponentFactory drawableComponentFactory, com.mapbox.mapboxsdk.annotations.Marker marker, MapboxMap map, AnyMapAdapter anyMapAdapter, float anchorU, float anchorV) {
		this.drawableComponentFactory = drawableComponentFactory;
		this.marker = marker;
		this.anyMapAdapter = anyMapAdapter;
		this.map = map;
		this.actualPosition = marker.getPosition();
		this.anchorU = anchorU;
		this.anchorV = anchorV;

		updateAnchor();
		map.addOnCameraMoveListener(this);
	}

	private void updateAnchor() {
		Projection projection = map.getProjection();
		PointF screenPos = projection.toScreenLocation(actualPosition);
		Icon icon = marker.getIcon();
		if (icon != null) {
			Bitmap bitmap = icon.getBitmap();
			int height = bitmap.getHeight();
			int width = bitmap.getWidth();

			screenPos.x -= width * (anchorU - 0.5);
			screenPos.y -= height * (anchorV - 0.5);

			marker.setPosition(projection.fromScreenLocation(screenPos));
		}
	}

	@Override
	public void setIcon(BitmapDescriptor icon) {
		BitmapDescriptorAdapter adapter = (BitmapDescriptorAdapter) icon;
		marker.setIcon(adapter.icon);
	}

	@Override
	public LatLng getPosition() {
		return anyMapAdapter.map(marker.getPosition());
	}

	@Override
	public void showInfoWindow() {
		// not supported
	}

	@Override
	public void setRotation(float rotation) {
		// not supported
	}

	@Override
	public void setVisible(boolean visible) {
		// not supported
	}

	@Override
	public void remove() {
		marker.remove();
		map.removeOnCameraMoveListener(this);
		drawableComponentFactory.markers.remove(this.marker.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MarkerAdapter)) return false;

		MarkerAdapter that = (MarkerAdapter) o;

		return marker.getId() == that.marker.getId();
	}

	@Override
	public int hashCode() {
		return Long.valueOf(marker.getId()).hashCode();
	}

	@Override
	public void setZ(int z) {
		// not supported
	}

	@Override
	public void setAnchor(float u, float v) {
		this.anchorU = u;
		this.anchorV = v;
		updateAnchor();
	}

	@Override
	public void onCameraMove() {
		updateAnchor();
	}
}
