package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.BlockComponent;
import com.mygdx.game.components.BlockStopComponent;
import com.mygdx.game.components.TransformComponent;

import java.util.Comparator;

import static com.mygdx.game.base.PlayState.ENTITY_SIZE;
import static com.mygdx.game.base.PlayState.GRAVITY;

public class BlockSystem extends SortedIteratingSystem {

    private static ComponentMapper<TransformComponent> pm;

    public BlockSystem() {
        super(Family.all(BlockStopComponent.class).get(), new ZComparator());

        pm = ComponentMapper.getFor(TransformComponent.class);
    }

    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        AccelerationComponent gravityComp = entity.getComponent(AccelerationComponent.class);
        BlockStopComponent blockComp = entity.getComponent(BlockStopComponent.class);

        ImmutableArray<Entity> blocks = this.getEngine().getEntitiesFor(Family.all(BlockComponent.class).get());

        blockComp.numberUnder = 0;
        for(int i = blocks.size() - 1; i >= 0; i--){

            TransformComponent blockTransformComp = blocks.get(i).getComponent(TransformComponent.class);


            if(blocks.get(i) != entity) {
                if (transformComp.position.x - ENTITY_SIZE/2 < blockTransformComp.position.x + ENTITY_SIZE / 2 &&
                        transformComp.position.x + ENTITY_SIZE/2 > blockTransformComp.position.x - ENTITY_SIZE / 2 &&
                        transformComp.position.y - ENTITY_SIZE/2 -1 < blockTransformComp.position.y + ENTITY_SIZE/2 &&
                        transformComp.position.y + ENTITY_SIZE/2 > blockTransformComp.position.y - ENTITY_SIZE/2) {
                    transformComp.position.y = blockTransformComp.position.y + ENTITY_SIZE;
                    blockComp.numberUnder += 1;
                    blockComp.falling = false;
                    break;
                }
            }
        }

        if(blockComp.numberUnder == 0) {
            blockComp.falling = true;
        }
        else if(blockComp.numberUnder > 0){
            blockComp.falling = false;
        }

        if(blockComp.falling){
            gravityComp.acceleration = GRAVITY;
        }

        else {
            gravityComp.acceleration = 0;
            transformComp.dy = 0;
        }

        if(transformComp.position.y <= ENTITY_SIZE/2){
            transformComp.dy = 0;
            transformComp.position.y = ENTITY_SIZE/2;
            gravityComp.acceleration = 0;
        }

    }

    private static class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int)Math.signum(pm.get(e2).position.y - pm.get(e1).position.y);
        }
    }
}
