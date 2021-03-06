package com.kmecpp.osmium.api.entity;

import java.util.UUID;

import com.kmecpp.osmium.api.Abstraction;
import com.kmecpp.osmium.api.World;
import com.kmecpp.osmium.api.location.Location;

public interface Entity extends Abstraction {

	UUID getUniqueId();

	World getWorld();

	String getDisplayName();

	void setDisplayName(String name);

	Location getLocation();

}
