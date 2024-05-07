/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre;

import android.content.Context;
import android.graphics.Bitmap;

import com.car2go.maps.maplibre.adapter.BitmapDescriptorAdapter;
import com.car2go.maps.model.BitmapDescriptor;

import org.maplibre.android.annotations.IconFactory;
import org.maplibre.android.maps.MapLibreMap;
import org.maplibre.android.utils.BitmapUtils;

import androidx.annotation.DrawableRes;

/**
 * Creates instances of {@link com.car2go.maps.model.BitmapDescriptor}
 */
public class BitmapDescriptorFactory implements com.car2go.maps.BitmapDescriptorFactory {

	private final Context context;
	private MapLibreMap map;
	private IconFactory iconFactory;

	public BitmapDescriptorFactory(Context context, MapLibreMap map) {
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
		Bitmap bitmap = BitmapUtils.getBitmapFromDrawable(
				BitmapUtils.getDrawableFromRes(context, resourceId));
		return fromBitmap(bitmap);
	}

}
