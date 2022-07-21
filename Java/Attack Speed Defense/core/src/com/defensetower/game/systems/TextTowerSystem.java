package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.TextTowerComponent;
import com.defensetower.game.components.TextureComponent;
import com.defensetower.game.components.TowerComponent;
import com.defensetower.game.components.TransformComponent;
import com.defensetower.game.entities.RadiusEntity;
import com.defensetower.game.entities.TowerEntity;

import static com.badlogic.gdx.Gdx.input;

public class TextTowerSystem extends IteratingSystem {

    public TextTowerSystem() {
        super(Family.all(TextureComponent.class, TransformComponent.class, TextTowerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        TextTowerComponent textTowerComp = entity.getComponent(TextTowerComponent.class);

        if (input.justTouched() &&
                        input.getX() < transformComp.position.x + 16 &&
                        input.getX() > transformComp.position.x - 16 &&
                        MainGame.HEIGHT - input.getY() > transformComp.position.y - 16 &&
                        MainGame.HEIGHT - input.getY() < transformComp.position.y + 16){

            Entity tower = null;

            switch(textTowerComp.type){
                case 0: tower = TowerEntity.createBasic(this.getEngine(), null);
                    break;
                case 1: tower = TowerEntity.createFast(this.getEngine(), null);
                    break;
                case 2: tower = TowerEntity.createDamage(this.getEngine(), null);
                    break;
                case 3: tower = TowerEntity.createRange(this.getEngine(), null);
                    break;
                case 4: tower = TowerEntity.createSpeed(this.getEngine(), null);
                    break;
            }

            float range = tower.getComponent(TowerComponent.class).range;
            Entity radius = RadiusEntity.create(this.getEngine(), range, tower);
            tower.getComponent(TowerComponent.class).radius = radius;
        }
    }
}
