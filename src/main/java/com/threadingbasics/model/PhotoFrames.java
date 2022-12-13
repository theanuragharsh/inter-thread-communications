package com.threadingbasics.model;

import org.springframework.stereotype.Component;

@Component
public class PhotoFrames {

    public int id;
    String material;
    String size;
    String texture;
    String color;

    public PhotoFrames() {
        super();
    }

    public PhotoFrames(int id, String material, String size, String texture, String color) {
        this.id = id;
        this.material = material;
        this.size = size;
        this.texture = texture;
        this.color = color;
    }

    @Override
    public String toString() {
        return "PhotoFrames{" +
                "id=" + id +
                ", material='" + material + '\'' +
                ", size='" + size + '\'' +
                ", texture='" + texture + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
