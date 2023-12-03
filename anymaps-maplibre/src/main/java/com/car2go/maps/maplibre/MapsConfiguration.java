/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre;

import android.content.Context;

import com.car2go.maps.AnyMap;
import com.mapbox.mapboxsdk.Mapbox;

import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static java.util.EnumSet.of;

/**
 * Initializer for Mapbox maps.
 */
public class MapsConfiguration implements com.car2go.maps.MapsConfiguration {

	private static final MapsConfiguration instance = new MapsConfiguration();

	private MapsConfiguration() {
	}

	public static MapsConfiguration getInstance() {
		return instance;
	}

	@Override
	public void initialize(Context context) {
		Mapbox.getInstance(context);
	}

	@Override
	public Set<AnyMap.Feature> getSupportedFeatures() {
		return unmodifiableSet(of(AnyMap.Feature.MAP_TYPES, AnyMap.Feature.REVEALABLE));
	}


}
