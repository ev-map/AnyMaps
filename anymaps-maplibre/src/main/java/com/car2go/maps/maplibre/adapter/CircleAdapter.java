/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.model.Circle;

import org.maplibre.android.plugins.annotation.CircleManager;

/**
 * Adapts Google Circle to AnyMap Circle
 */
public class CircleAdapter implements Circle {

	private final org.maplibre.android.plugins.annotation.Circle circle;
	private CircleManager manager;

	public CircleAdapter(org.maplibre.android.plugins.annotation.Circle circle, CircleManager manager) {
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
