package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.star.app.game.helpers.ObjectPool;

public class PowerUpController extends ObjectPool<PowerUp> {
    private ParticleController particleController;

    public PowerUpController(ParticleController particleController) {
        this.particleController = particleController;
    }

    public ParticleController getParticleController() {
        return particleController;
    }

    @Override
    protected PowerUp newObject() {
        return new PowerUp(this);
    }

    public void render (SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            PowerUp p = activeList.get(i);
            batch.draw(p.getTexture(), p.getPosition().x-16, p.getPosition().y-16, 16, 16, 32, 32, p.getSize(), p.getSize(), p.getAngle());
        }
    }

    public void setup(float x, float y) {
        getActiveElement().activate(x, y);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
