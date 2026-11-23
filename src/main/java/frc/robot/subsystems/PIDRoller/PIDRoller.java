public Trigger isJammed() {
  return new Trigger(
      () -> inputs.leaderVelocityRadsPerSec.lte(PIDRollerConstants.PIDROLLER_JAMMED_VELOCITY)
          && inputs.leaderCurrentAmps.gte(PIDRollerConstants.PIDROLLER_JAMMED_CURRENT)
  ).debounce(PIDRollerConstants.PIDROLLER_JAMMED_TIME, DebounceType.kRising);
}
