package hu.unideb.inf.webfejlesztesprojekt.entity;

public enum Workout {
    Running(10),
    Cycling(8),
    Swimming(7),
    Climbing(9),
    WeightLifting(6),
    PushUps(5),
    SitUps(4);


    public final Integer MetValue;

    Workout(Integer met) {
        this.MetValue = met;
    }

    public Integer getMetValue() {
        return MetValue;
    }
}
