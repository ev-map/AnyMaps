/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.mapbox;

import android.content.Context;
import android.graphics.Bitmap;

import com.car2go.maps.mapbox.adapter.BitmapDescriptorAdapter;
import com.car2go.maps.model.BitmapDescriptor;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.exceptions.TooManyIconsException;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.util.HashMap;

import androidx.annotation.DrawableRes;

/**
 * Creates instances of {@link com.car2go.maps.model.BitmapDescriptor}
 */
public class BitmapDescriptorFactory implements com.car2go.maps.BitmapDescriptorFactory {

	private final Context context;
	private MapboxMap map;
	private IconFactory iconFactory;

	public BitmapDescriptorFactory(Context context, MapboxMap map) {
		this.context = context;
		this.map = map;
		this.iconFactory = IconFactory.getInstance(context);
	}

	@Override
	public BitmapDescriptor fromBitmap(Bitmap bitmap) {
		return new BitmapDescriptorAdapter(iconFactory.fromBitmap(bitmap));
	}

	@Override
	public BitmapDescriptor fromResource(@DrawableRes int resourceId) {
		return new BitmapDescriptorAdapter(iconFactory.fromResource(resourceId));
	}

}
