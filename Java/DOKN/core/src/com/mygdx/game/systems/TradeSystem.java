package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.mygdx.game.components.AccelerationComponent;
import com.mygdx.game.components.RectComponent;
import com.mygdx.game.components.TradeComponent;
import com.mygdx.game.components.TransformComponent;

import static com.badlogic.gdx.Gdx.input;
import static com.mygdx.game.DOKN.HEIGHT;
import static com.mygdx.game.base.PlayState.*;

public class TradeSystem extends IteratingSystem {

    public TradeSystem() {
        super(Family.all(TradeComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComp = entity.getComponent(TransformComponent.class);
        TradeComponent tradeComp = entity.getComponent(TradeComponent.class);
        RectComponent rectComp = entity.getComponent(RectComponent.class);

        if(rectComp.rect.contains(input.getX(),HEIGHT - input.getY()) && input.justTouched()) {
            switch (tradeComp.type) {
                case 1: if(X >= TRADE1){
                    tradeX();
                }
                break;
                case 2: if(Y >= TRADE2){
                    tradeY();
                }
                break;
                case 3: if(Y >= TRADE3){
                    tradeY2();
                }
                break;
                case 4: if(Z >= TRADE4){
                    tradeZ();
                }
                break;
            }
        }
    }
}
