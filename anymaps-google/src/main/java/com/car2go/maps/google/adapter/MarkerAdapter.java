/*
 * Copyright (c) 2016 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.google.adapter;

import com.car2go.maps.model.BitmapDescriptor;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.Marker;

/**
 * Adapts Google Marker to AnyMap Marker
 */
public class MarkerAdapter implements Marker {

	private final com.google.android.gms.maps.model.Marker marker;

	public MarkerAdapter(com.google.android.gms.maps.model.Marker marker) {
		this.marker = marker;
	}

	@Override
	public void setIcon(BitmapDescriptor icon) {
		BitmapDescriptorAdapter adapter = (BitmapDescriptorAdapter) icon;

		marker.setIcon(adapter.wrappedDescriptor);
	}

	@Override
	public LatLng getPosition() {
		return AnyMapAdapter.adapt(marker.getPosition());
	}

	@Override
	public void showInfoWindow() {
		marker.showInfoWindow();
	}

	@Override
	public void setRotation(float rotation) {
		marker.setRotation(rotation);
	}

	@Override
	public void setVisible(boolean visible) {
		marker.setVisible(visible);
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

		return marker.equals(that.marker);
	}

	@Override
	public int hashCode() {
		return marker.hashCode();
	}

	@Override
	public void setZ(int z) {
		marker.setZIndex(z);
	}

	@Override
	public void setAnchor(float u, float v) {
		marker.setAnchor(u, v);
	}
}
