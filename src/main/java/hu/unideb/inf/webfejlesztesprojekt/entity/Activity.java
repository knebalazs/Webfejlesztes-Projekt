package hu.unideb.inf.webfejlesztesprojekt.entity;

import hu.unideb.inf.webfejlesztesprojekt.repository.UserRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "user_email")
    private String userEmail;

    @Column(nullable = false, name = "workout_type")
    private Workout workoutType;

    @Column(nullable = false, name = "workout_length")
    private Integer workoutLength;

    @Column
    private Integer distance;

    @Column
    private Integer repetitions;

    @Column
    private Double caloriesBurned;

}
