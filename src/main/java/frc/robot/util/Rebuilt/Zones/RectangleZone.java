package frc.robot.util.Rebuilt.Zones;

import java.util.function.Supplier;

<<<<<<< HEAD
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.button.Trigger;
=======
import org.wpilib.math.geometry.Translation2d;
import org.wpilib.command2.button.Trigger;
>>>>>>> 7c3eb71 (init)

public class RectangleZone implements Zone {
    private final Translation2d topLeft;
    private final Translation2d bottomRight;

    /**
     * Creates a rectangular zone with the given top left and bottom right corners.
     * @param topLeft the top left corner of the rectangle
     * @param bottomRight the bottom right corner of the rectangle
     */
    public RectangleZone(Translation2d topLeft, Translation2d bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * Returns a Trigger that is active when the given translation is within the rectangle.
     * @param translation the translation to check
     * @return a Trigger that is active when the given translation is within the rectangle
     */
    
    @Override
    public Trigger contains(Supplier<Translation2d> translation) {
        return new Trigger(() -> {
            Translation2d t = translation.get();
            return t.getX() >= topLeft.getX() && t.getX() <= bottomRight.getX() &&
                   t.getY() >= bottomRight.getY() && t.getY() <= topLeft.getY();
        });
    }
    
}
