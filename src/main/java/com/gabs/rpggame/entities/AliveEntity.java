package com.gabs.rpggame.entities;

import java.util.ArrayList;
import java.util.List;

import com.gabs.rpggame.entities.collectables.Equipment;

public class AliveEntity extends Entity {
	
	private List<Equipment> equipments = new ArrayList<>();
	
	public AliveEntity() {
		super();
		for(int i = 0; i < 6; i++)
			getEquipments().add(null);
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}
}
