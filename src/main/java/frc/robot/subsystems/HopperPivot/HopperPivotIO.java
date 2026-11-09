public interface HopperPivotIO {
    @AutoLog
    public static class HopperPivotIOInputs {
        public boolean hopperPivot1Connected = false;
        public Angle hopperPivot1Angle  = Radians.of(0.0);
        public AngularVelocity hopperPivot1AngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage hopperPivot1Voltage = Volts.of(0.0);
        public Current hopperPivot1Current = Amps.of(0.0);
        public Temperature hopperPivot1Temp = Celsius.of(0.0);

        public boolean hopperPivot2Connected = false;
        public Angle hopperPivot2Angle  = Radians.of(0.0);
        public AngularVelocity hopperPivot2AngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage hopperPivot2Voltage = Volts.of(0.0);
        public Current hopperPivot2Current = Amps.of(0.0);
        public Temperature hopperPivot2Temp = Celsius.of(0.0);
    }
    public default void updateInputs(HopperPivotIOInputs inputs) {}
    public default void setAngle(Angle angle) {}
    public default void setVoltage(Voltage voltage) {}
    public default void stop() {}
    public default void setPID(double kP, double kI, double kD) {}
    public default void setFF(double ks, double kv, double kg, double ka) {}
}
