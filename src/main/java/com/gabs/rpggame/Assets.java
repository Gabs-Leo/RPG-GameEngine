package com.gabs.rpggame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.gabs.rpggame.entities.collectables.Collectable;
import com.gabs.rpggame.entities.collectables.Equipment;
import com.gabs.rpggame.entities.collectables.EquipmentType;
import com.gabs.rpggame.world.DamageType;

class Zap {
	public EquipmentType equipmentType;
	public DamageType damageType;
	public int damage;
	
	@Override
	public String toString() {
		return "Zap [equipmentType=" + equipmentType + ", damageType=" + damageType + ", damage=" + damage + "]";
	}
}

public class Assets {
	//public static Equipment sword1 = new Equipment(EquipmentType.HANDS, DamageType.PHYSICAL_DAMAGE, 20);
	public List<Equipment> equipments = new ArrayList<>();
	public List<Collectable> collectables = new ArrayList<>();
	//public static Enemy purpleDemon = new Enemy(20, 20, DamageType.PHYSICAL_DAMAGE);
	
	public Optional<Equipment> findEquipmentByName(String name) {
		for(int i = 0; i < equipments.size(); i++)
			if(equipments.get(i).getName().equals(name))
				return Optional.of(equipments.get(i));
		
		return Optional.empty();
	}
}
