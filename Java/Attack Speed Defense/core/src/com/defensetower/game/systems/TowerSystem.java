package com.defensetower.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.defensetower.game.Jukebox;
import com.defensetower.game.MainGame;
import com.defensetower.game.components.EnemyComponent;
import com.defensetower.game.components.TowerComponent;
import com.defensetower.game.components.TransformComponent;
import com.defensetower.game.entities.BulletEntity;
import com.defensetower.game.entities.TowerStatsText;
import com.defensetower.game.entities.RadiusEntity;
import com.defensetower.game.managers.GameKeys;

import static com.badlogic.gdx.Gdx.input;
import static com.defensetower.game.gamestates.PlayState.createUpgradeBox;
import static com.defensetower.game.gamestates.PlayState.score;
import static javax.swing.text.StyleConstants.Family;

public class TowerSystem extends IteratingSystem {

    public static boolean showStats = false;
    private Entity textStats;
    private Entity upBox;
    public TowerSystem() {
        super(com.badlogic.ashley.core.Family.all(TowerComponent.class, TransformComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TowerComponent towerComp = entity.getComponent(TowerComponent.class);
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);

        towerComp.attackTimer += deltaTime;

        if(towerComp.state == 0){

            transformComp.position.y = MainGame.HEIGHT - input.getY();
            transformComp.position.x = input.getX();

            if (input.justTouched() && input.getX() < MainGame.WIDTH - 64 - 16 && score >= towerComp.cost) {
                score -= towerComp.cost;
                this.getEngine().removeEntity(towerComp.radius);
                towerComp.state = 1;
            }

            if(GameKeys.isPressed(GameKeys.ESCAPE)){
                this.getEngine().removeEntity(entity);
                this.getEngine().removeEntity(towerComp.radius);
            }
        }

        if(towerComp.state == 1) {

            String[] stats = new String[]{
                    "Co: " + Integer.toString(towerComp.cost),
                    "Da: " + Integer.toString(towerComp.damage),
                    "AS: " + Float.toString(towerComp.attackSpeed),
                    "Ra: " + Float.toString(towerComp.range),
                    "Sp: " + Float.toString(towerComp.bulletSpeed),
                    "Up: " + Float.toString(towerComp.upgradeCost)
            };

            if (input.justTouched() &&
                input.getX() < transformComp.position.x + 16 &&
                input.getX() > transformComp.position.x - 16 &&
                MainGame.HEIGHT - input.getY() > transformComp.position.y - 16 &&
                MainGame.HEIGHT - input.getY() < transformComp.position.y + 16) {

                if (textStats == null) {
                    for (int i = 0; i < stats.length; i++) {
                        TowerStatsText.create(this.getEngine(), MainGame.WIDTH - 64, 200 - 20 * i, stats[i], false, true);
                    }
                    if(towerComp.type != 0) {
                        upBox = createUpgradeBox(this.getEngine(), MainGame.WIDTH - 32, 32, entity);
                    }
                    textStats = towerComp.radius = RadiusEntity.create(this.getEngine(), towerComp.range, entity);
                    towerComp.stats = true;
                    showStats = true;

                }
                else{
                    this.getEngine().removeEntity(textStats);
                    if(upBox != null) {
                        this.getEngine().removeEntity(upBox);
                    }
                    textStats = null;
                    towerComp.stats = false;
                    showStats = false;
                }
            }

            if (towerComp.attackTimer * towerComp.attackSpeed > towerComp.attackTime  ) {
                ImmutableArray<Entity> enemies = this.getEngine().getEntitiesFor(com.badlogic.ashley.core.Family.all(EnemyComponent.class).get());

                Array<Entity> sortedEnemies = new Array<Entity>();
                sortedEnemies.addAll(enemies.toArray());
                sortedEnemies.sort(EnemyComponent.enemyDistanceSorter); // Last enemy in array is guarenteed to be furthest

                for(int i = sortedEnemies.size - 1; i >= 0; i--) {
                    TransformComponent enTransComp = sortedEnemies.get(i).getComponent(TransformComponent.class);
                    float d = (float) Math.sqrt(((enTransComp.position.x - transformComp.position.x) * (enTransComp.position.x - transformComp.position.x)) + ((enTransComp.position.y - transformComp.position.y) * (enTransComp.position.y - transformComp.position.y)));
                    if(d < towerComp.range) {
                        BulletEntity.create(new Vector2(transformComp.position.x, transformComp.position.y), this.getEngine(), towerComp.damage, sortedEnemies.get(i), towerComp.bulletSpeed);
                        towerComp.attackTimer = 0;
                        Jukebox.play("bow");
                        break;
                    }
                }
            }
        }
    }
}
