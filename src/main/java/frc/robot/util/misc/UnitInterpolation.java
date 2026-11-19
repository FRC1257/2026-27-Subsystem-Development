package frc.robot.util.misc;

import java.util.Comparator;

import org.wpilib.math.interpolation.Interpolator;
import org.wpilib.math.interpolation.InverseInterpolator;
import org.wpilib.units.Measure;
import org.wpilib.units.Unit;

public final class UnitInterpolation {
    public static <M extends Measure<?>> InverseInterpolator<M> inverseInterpolate() {

        return (start, end, query) -> {

            double startBase = start.baseUnitMagnitude();
            double endBase = end.baseUnitMagnitude();
            double queryBase = query.baseUnitMagnitude();

            if(endBase - startBase <= 0) return 0;

            return Math.max(0.0, (queryBase - startBase) / (endBase - startBase));

        };
    }

    @SuppressWarnings("unchecked")
    public static <M extends Measure<?>> Interpolator<M> Interpolator(Unit unit) {

        Interpolator<M> interpolator = (start, end, t) -> (M) unit.ofBaseUnits(
            start.baseUnitMagnitude() + t*(end.baseUnitMagnitude()-start.baseUnitMagnitude())
        );
        
        return interpolator;
    }

    public static <M extends Measure<?>> Comparator<M> comparator(){
        return (a, b) -> Double.compare(a.baseUnitMagnitude(), b.baseUnitMagnitude());
    }
}
