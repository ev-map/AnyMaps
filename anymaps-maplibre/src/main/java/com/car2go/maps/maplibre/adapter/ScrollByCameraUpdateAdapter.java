/*
 * Copyright (c) 2024 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.CameraUpdate;

public class ScrollByCameraUpdateAdapter implements CameraUpdate {
	public final float distanceX, distanceY;

	public ScrollByCameraUpdateAdapter(float distanceX, float distanceY) {
		this.distanceX = distanceX;
		this.distanceY = distanceY;
	}
}
