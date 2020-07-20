/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.google;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.car2go.maps.google.adapter.BitmapDescriptorAdapter;
import com.car2go.maps.model.BitmapDescriptor;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * Creates instances of {@link com.car2go.maps.model.BitmapDescriptor}
 */
public class BitmapDescriptorFactory implements com.car2go.maps.BitmapDescriptorFactory {

	private final Context context;

	public BitmapDescriptorFactory(Context context) {
		this.context = context;
	}

	@Override
	public BitmapDescriptor fromBitmap(Bitmap bitmap) {
		return new BitmapDescriptorAdapter(
				com.google.android.libraries.maps.model.BitmapDescriptorFactory.fromBitmap(bitmap)
		);
	}

	@Override
	public BitmapDescriptor fromResource(@DrawableRes int resourceId) {
		Drawable drawable = context.getResources().getDrawable(resourceId);
		Bitmap bitmap = getBitmapFromDrawable(drawable);
		return fromBitmap(bitmap);
	}

	/**
	 * Extract an underlying bitmap from a drawable
	 *
	 * @param sourceDrawable The source drawable
	 * @return The underlying bitmap
	 */
	@Nullable
	private static Bitmap getBitmapFromDrawable(@Nullable Drawable sourceDrawable) {
		if (sourceDrawable == null) {
			return null;
		}

		if (sourceDrawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) sourceDrawable).getBitmap();
		} else {
			//copying drawable object to not manipulate on the same reference
			Drawable.ConstantState constantState = sourceDrawable.getConstantState();
			if (constantState == null) {
				return null;
			}
			Drawable drawable = constantState.newDrawable().mutate();

			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
					Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		}
	}

}
