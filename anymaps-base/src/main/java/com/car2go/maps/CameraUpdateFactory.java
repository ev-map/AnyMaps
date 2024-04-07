/*
 * Copyright (c) 2017 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps;

import android.graphics.Point;

import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.LatLngBounds;

/**
 * Creates {@link CameraUpdate} objects which can be used to update map camera
 */
public interface CameraUpdateFactory {

	/**
	 * @return {@link CameraUpdate} which moves camera to given position with given zoom level.
	 */
	CameraUpdate newLatLngZoom(LatLng latLng, float zoomLevel);

	/**
	 * @return {@link CameraUpdate} which moves camera to given position
	 */
	CameraUpdate newLatLng(LatLng latLng);

	/**
	 * @return {@link CameraUpdate} which moves camera so it displays given bounds with given padding.
	 */
	CameraUpdate newLatLngBounds(LatLngBounds bounds, int padding);

	/**
	 * @return {@link CameraUpdate} which zooms camera to given zoom level.
	 */
	CameraUpdate zoomTo(float zoomLevel);

	CameraUpdate scrollBy(float distanceX, float distanceY);

	CameraUpdate zoomBy(float amount);

	CameraUpdate zoomBy(float amount, Point focus);
}
