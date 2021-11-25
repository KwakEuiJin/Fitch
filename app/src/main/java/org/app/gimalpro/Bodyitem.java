package org.app.gimalpro;

public class Bodyitem {
    private int NUMBER;
    private String ID;
    private Double Height;
    private Double Weight;
    private Double Muscle;
    private int Muscle_level;
    private int Fat_level;
    private Double Kcal;

    public Double getKcal() {
        return Kcal;
    }

    public void setKcal(Double kcal) {
        Kcal = kcal;
    }

    public int getMuscle_level() {
        return Muscle_level;
    }

    public void setMuscle_level(int muscle_level) {
        Muscle_level = muscle_level;
    }

    public int getFat_level() {
        return Fat_level;
    }

    public void setFat_level(int fat_level) {
        Fat_level = fat_level;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(int NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getHeight() {
        return Height;
    }

    public void setHeight(Double height) {
        Height = height;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public Double getMuscle() {
        return Muscle;
    }

    public void setMuscle(Double muscle) {
        Muscle = muscle;
    }

    public Double getFat() {
        return Fat;
    }

    public void setFat(Double fat) {
        Fat = fat;
    }

    private Double Fat;

    public Bodyitem() {
    }


}
