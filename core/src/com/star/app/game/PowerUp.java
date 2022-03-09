package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.utils.Assets;

public class PowerUp implements Poolable {

    public enum TYPE{
        HEAL("heal", 16),
        AMMO("ammo", 16),
        MONEY("bitcoin", 16);

        public String name;
        public float radius;

        TYPE(String name, float radius) {
            this.name = name;
            this.radius = radius;
        }
    }

    private Vector2 position;
    private boolean active;
    private Circle putArea;
    private TextureRegion texture;
    private String name;
    private float size = 1;
    private float time;
    private float angle;
    private float rotation = 60;
    private Vector3 colorParticular;
    private boolean flag;
    private PowerUpController powerUpController;


    public Vector2 getPosition() {
        return position;
    }

    public Vector3 getColorParticular() {
        return colorParticular;
    }

    public float getSize() {
        return size;
    }

    public Circle getPutArea() {
        return putArea;
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public PowerUp(PowerUpController powerUpController) {
        this.active = false;
        this.position = new Vector2();
        this.putArea = new Circle();
        this.colorParticular = new Vector3();
        this.powerUpController = powerUpController;
    }

    public void activate(float x, float y) {
        switch (MathUtils.random(2)) {
            case 0: texture = Assets.getInstance().getAtlas().findRegion(TYPE.HEAL.name);
                putArea.setRadius(TYPE.HEAL.radius);
                name = TYPE.HEAL.name;
                colorParticular.set(1, 0, 0);
                break;
            case 1: texture = Assets.getInstance().getAtlas().findRegion(TYPE.AMMO.name);
                putArea.setRadius(TYPE.AMMO.radius);
                name = TYPE.AMMO.name;
                colorParticular.set(0, 1, 0);
                break;
            case 2: texture = Assets.getInstance().getAtlas().findRegion(TYPE.MONEY.name);
                putArea.setRadius(TYPE.MONEY.radius);
                colorParticular.set(0, 0, 1);
                name = TYPE.MONEY.name;
        }
        position.set(x, y);
        putArea.setPosition(x, y);
        size = 0;
        angle = 0;
        flag = true;
        active = true;

        for (int i = 0; i < 360; i+=30) {
            powerUpController.getParticleController().setup(position.x, position.y, 60*MathUtils.cosDeg(i),
                    60*MathUtils.sinDeg(i), 1, 1, 2, colorParticular.x, colorParticular.y,
                    colorParticular.z ,1, colorParticular.x, colorParticular.y,
                    colorParticular.z, 0);
        }
    }

    public void deactivate() {
        active = false;
    }

    public void update(float dt) {
        time += dt;

        angle += rotation*dt;
        if(Math.abs(angle) >= 10) {
            angle = Math.signum(angle)*10;
            rotation *= -1;
        }

        if(flag) {
            size += 0.05;
            if (size >= 1) {
                flag = false;
            }
        } else {
            size = 1;
            if (time >= 1) {
                size = 0.8f;
            }
            if (time >= 1.2) {
                time = 0;
            }
        }
    }
}
