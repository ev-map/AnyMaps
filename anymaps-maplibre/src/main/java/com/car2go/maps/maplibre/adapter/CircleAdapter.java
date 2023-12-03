/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.model.Circle;
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;

/**
 * Adapts Google Circle to AnyMap Circle
 */
public class CircleAdapter implements Circle {

	private final com.mapbox.mapboxsdk.plugins.annotation.Circle circle;
	private CircleManager manager;

	public CircleAdapter(com.mapbox.mapboxsdk.plugins.annotation.Circle circle, CircleManager manager) {
		this.circle = circle;
		this.manager = manager;
	}

	@Override
	public void setVisible(boolean visible) {
		circle.setCircleStrokeOpacity(visible ? 1f : 0f);
		circle.setCircleOpacity(visible ? 1f : 0f);
	}

	@Override
	public void remove() {
		manager.delete(circle);
	}

}
