/*
 * Copyright (c) 2017 Daimler AG / Moovel GmbH
 *
 * All rights reserved
 */

package com.car2go.example;

import android.os.Bundle;

import com.car2go.maps.AnyMap;
import com.car2go.maps.MapFragment;
import com.car2go.maps.OnMapReadyCallback;
import com.car2go.maps.model.LatLng;
import com.car2go.maps.model.MarkerOptions;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private MapFragment mapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			mapFragment = new MapFragment();
			mapFragment.setPriority(new String[]{MapFragment.MAPLIBRE});
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.map, mapFragment, "map")
					.commit();
		} else {
			mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("map");
		}

		mapFragment.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(AnyMap map) {
				map.moveCamera(
						map.getCameraUpdateFactory()
								.newLatLngZoom(
										new LatLng(53.5443465, 9.9289326),
										17f
								)
				);

				map.addMarker(
						new MarkerOptions()
								.position(new LatLng(53.5443465, 9.9289326))
								.anchor(0.5f, 0.5f)
								.icon(map.getBitmapDescriptorFactory().fromResource(R.drawable.marker))
				);

				map.setMapType(AnyMap.Type.HYBRID);
				map.setMapStyle(AnyMap.Style.DARK);
			}
		});
	}

}
