package com.gabs.rpggame.entities.collectables;

import java.util.ArrayList;
import java.util.List;

import com.gabs.rpggame.entities.AliveEntity;
import com.gabs.rpggame.graphics.Animation;
public class Equipment extends Collectable{
	private EquipmentType equipmentType;
	private List<Animation> animations = new ArrayList<>();
	
	public Equipment() {}
	
	public void equipTo(AliveEntity entity) {
		switch(equipmentType) {
			case HEAD:
				entity.getEquipments().set(0, this);
				break;
			case BODY:
				entity.getEquipments().set(1, this);
				break;
			case LEGS:
				entity.getEquipments().set(2, this);
				break;
			case FOOT:
				entity.getEquipments().set(3, this);
				break;
			case HANDS:
				entity.getEquipments().set(4, this);
				break;
			case RING:
				entity.getEquipments().set(5, this);
				break;
			default:
				break;
		}
	}
	
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public Equipment setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
		return this;
	}

	public List<Animation> getAnimations() {
		return animations;
	}
}
