package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    private boolean flag;


    public Vector2 getPosition() {
        return position;
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

    public PowerUp() {
        this.active = false;
        this.position = new Vector2();
        this.putArea = new Circle();
    }

    public void activate(float x, float y) {
        switch (MathUtils.random(2)) {
            case 0: texture = Assets.getInstance().getAtlas().findRegion(TYPE.HEAL.name);
                putArea.setRadius(TYPE.HEAL.radius);
                name = TYPE.HEAL.name;
                break;
            case 1: texture = Assets.getInstance().getAtlas().findRegion(TYPE.AMMO.name);
                putArea.setRadius(TYPE.AMMO.radius);
                name = TYPE.AMMO.name;
                break;
            case 2: texture = Assets.getInstance().getAtlas().findRegion(TYPE.MONEY.name);
                putArea.setRadius(TYPE.MONEY.radius);
                name = TYPE.MONEY.name;
        }
        position.set(x, y);
        putArea.setPosition(x, y);
        size = 0;
        angle = 0;
        flag = true;
        active = true;
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
