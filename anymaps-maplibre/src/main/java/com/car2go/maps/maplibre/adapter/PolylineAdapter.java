/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.model.Polyline;

import org.maplibre.android.plugins.annotation.Line;
import org.maplibre.android.plugins.annotation.LineManager;

/**
 * Adapts Google Polyline to AnyMap Polyline
 */
public class PolylineAdapter implements Polyline {

	private final Line polyline;
	private final LineManager manager;

	public PolylineAdapter(Line input, LineManager manager) {
		this.polyline = input;
		this.manager = manager;
	}

	@Override
	public void setVisible(boolean visible) {
		polyline.setLineOpacity(visible ? 1f : 0f);
	}

	@Override
	public void remove() {
		manager.delete(polyline);
	}

}
