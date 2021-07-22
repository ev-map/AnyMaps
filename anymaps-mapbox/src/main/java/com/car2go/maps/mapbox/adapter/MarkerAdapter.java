/*
 * Copyright (c) 2016 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox.adapter;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import com.car2go.maps.model.BitmapDescriptor;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.Marker;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;

/**
 * Adapts Mapbox Marker to AnyMap Marker
 */
public class MarkerAdapter implements Marker {

	private final com.mapbox.mapboxsdk.annotations.Marker marker;
	private final AnyMapAdapter anyMapAdapter;

	public MarkerAdapter(com.mapbox.mapboxsdk.annotations.Marker marker, AnyMapAdapter anyMapAdapter) {
		this.marker = marker;
		this.anyMapAdapter = anyMapAdapter;
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
		// not supported
	}
}
