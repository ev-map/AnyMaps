/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.google.adapter;

import com.car2go.maps.model.Polyline;

/**
 * Adapts Google Polyline to AnyMap Polyline
 */
public class PolylineAdapter implements Polyline {

	private final com.google.android.libraries.maps.model.Polyline polyline;

	public PolylineAdapter(com.google.android.libraries.maps.model.Polyline polyline) {
		this.polyline = polyline;
	}

	@Override
	public void setVisible(boolean visible) {
		polyline.setVisible(visible);
	}

	@Override
	public void remove() {
		polyline.remove();
	}

}
