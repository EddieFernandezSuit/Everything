package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.TowerComponent;
import com.defensetower.game.components.TransformComponent;
import com.defensetower.game.components.UpgradeComponent;

import static com.badlogic.gdx.Gdx.input;
import static com.defensetower.game.gamestates.PlayState.*;
import static com.defensetower.game.systems.TowerSystem.showStats;

public class UpgradeSystem extends IteratingSystem {
    public UpgradeSystem() {
        super(Family.all(UpgradeComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        UpgradeComponent upComp = entity.getComponent(UpgradeComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);

        TowerComponent towerComp = upComp.tower.getComponent(TowerComponent.class);

        upComp.upCost = towerComp.upgradeCost;

        if (score >= upComp.upCost &&
                input.justTouched() &&
                input.getX() < transformComp.position.x + 20 &&
                input.getX() > transformComp.position.x - 20 &&
                MainGame.HEIGHT - input.getY() > transformComp.position.y - 20 &&
                MainGame.HEIGHT - input.getY() < transformComp.position.y + 20){

            switch(towerComp.type){
                case 0:
                    towerComp.damage += towerComp.upgradeCost/20;
                    towerComp.attackSpeed += towerComp.upgradeCost/20;
                    towerComp.bulletSpeed += towerComp.upgradeCost;
                    towerComp.range += towerComp.upgradeCost;
                    break;
                case 1:
                    towerComp.attackSpeed += towerComp.upgradeCost/10;
                    break;
                case 2:
                    towerComp.damage += towerComp.upgradeCost/6;
                    break;
                case 3:
                    towerComp.range += towerComp.upgradeCost;
                    towerComp.attackSpeed += towerComp.upgradeCost/9;
                    break;
                case 4:
                    towerComp.bulletSpeed += towerComp.upgradeCost * 3;
                    towerComp.attackSpeed += towerComp.upgradeCost/12;
                    break;
            }

            createTowerLevel(this.getEngine(), upComp.tower.getComponent(TransformComponent.class).position.x - 14 + towerComp.level * 7 , upComp.tower.getComponent(TransformComponent.class).position.y + 21);

            towerComp.level += 1;
            towerComp.upgradeCost += 10;
            score -= upComp.upCost;
            showStats = false;
            this.getEngine().removeEntity(entity);
        }
    }
}
