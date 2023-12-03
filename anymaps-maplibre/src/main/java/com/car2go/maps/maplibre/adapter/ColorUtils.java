/*
 * Copyright (c) 2020 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.maplibre.adapter;

public class ColorUtils {
	public static String toHex(int intColor) {
		return String.format("#%06X", (0xFFFFFF & intColor));
	}
}
