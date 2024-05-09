/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.anymap;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.CircleAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.mapbox.mapboxsdk.plugins.annotation.Circle;

/**
 * Maps Google Circle to AnyMap Circle
 */
public class CircleMapper implements Mapper<Circle, com.car2go.maps.model.Circle> {

	private final AnyMapAdapter anyMapAdapter;

	public CircleMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.model.Circle map(Circle input) {
		return new CircleAdapter(input, anyMapAdapter.drawableComponentFactory.circleManager);
	}

}
