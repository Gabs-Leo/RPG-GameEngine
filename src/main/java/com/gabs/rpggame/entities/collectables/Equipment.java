package com.gabs.rpggame.entities.collectables;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabs.rpggame.Main;
import com.gabs.rpggame.entities.AliveEntity;
import com.gabs.rpggame.graphics.Animation;
import com.gabs.rpggame.world.DamageType;
public class Equipment extends Collectable{
	private EquipmentType equipmentType;
	@JsonIgnore
	private List<Animation> animations = new ArrayList<>();
	
	public Equipment() {}
	
	@JsonCreator
	public Equipment(
			@JsonProperty("name") String name,
			@JsonProperty("mapColor") String mapColor,
			@JsonProperty("equipmentType") EquipmentType equipmentType, 
			@JsonProperty("damageType") DamageType damageType, 
			@JsonProperty("damage") int damage, 
			@JsonProperty("spritePositions") int[] spritePositions,
			@JsonProperty("equippedAnimations") List<Animation> equippedAnimations) {
		this.setName(name);
		this.setMapColor(mapColor);
		this.setEquipmentType(equipmentType);
		this.setDamageType(damageType);
		this.setDamage(damage);
		this.setSprite(Main.spritesheet.getSprite(spritePositions[0], spritePositions[1]));
		equippedAnimations.forEach(animation -> this.getAnimations().add(animation));
	}
	
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
	
	@Override
	public String toString() {
		return "Equipment \n"+
			   "[name=" + this.getName() + "] \n" +
			   "[equipmentType=" + equipmentType + "] \n";
			   //"[spritePositions=" + this.getSpritePositions()[0] + ", " + this.getSpritePositions()[1]+ "] \n";
	}
}
