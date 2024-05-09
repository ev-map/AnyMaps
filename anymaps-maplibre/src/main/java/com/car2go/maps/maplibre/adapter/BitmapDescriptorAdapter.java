/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

import com.car2go.maps.model.BitmapDescriptor;
import com.mapbox.mapboxsdk.annotations.Icon;

/**
 * Adapts Mapbox BitmapDescriptor to AnyMap BitmapDescriptor
 */
public class BitmapDescriptorAdapter implements BitmapDescriptor {

	public final Icon icon;

	public BitmapDescriptorAdapter(Icon icon) {
		this.icon = icon;
	}

}
