/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter.factory.anymap;

import com.car2go.maps.maplibre.adapter.AnyMapAdapter;
import com.car2go.maps.maplibre.adapter.ProjectionAdapter;
import com.car2go.maps.maplibre.adapter.factory.Mapper;
import com.mapbox.mapboxsdk.maps.Projection;

/**
 * Maps Google Projection to AnyMap Projection
 */
public class ProjectionMapper implements Mapper<Projection, com.car2go.maps.Projection> {

	private final AnyMapAdapter anyMapAdapter;

	public ProjectionMapper(AnyMapAdapter anyMapAdapter) {
		this.anyMapAdapter = anyMapAdapter;
	}

	@Override
	public com.car2go.maps.Projection map(Projection input) {
		return new ProjectionAdapter(input, anyMapAdapter);
	}

}
