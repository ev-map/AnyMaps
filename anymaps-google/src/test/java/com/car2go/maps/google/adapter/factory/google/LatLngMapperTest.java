/*
 * Copyright (c) 2015 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.maps.google.adapter.factory.google;

import com.car2go.maps.google.adapter.TestUtil;
import com.car2go.maps.model.LatLng;

import org.junit.Test;

public class LatLngMapperTest {

	@Test
	public void testMap() throws Exception {
		// Given
		LatLngMapper mapper = new LatLngMapper();

		LatLng input = new LatLng(10, 10);

		// When
		com.google.android.libraries.maps.model.LatLng output = mapper.map(input);

		// Then
		TestUtil.assertEquals(input, output);
	}

}