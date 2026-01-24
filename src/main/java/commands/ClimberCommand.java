package commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends Command {

    // TODO imploment/ create command for buttons
    ClimberSubsystem climberSubsystem;
    boolean extend;

    public ClimberCommand(ClimberSubsystem climberSubsystem, boolean extend) {
        this.extend = extend;
        this.climberSubsystem = climberSubsystem;
    }

    @Override
    public void initialize() {
        if (extend) {
            climberSubsystem.climb();

        } else {
            climberSubsystem.dumbClimb();
        }
    }

    @Override
    public boolean isFinished() { // cheks is the motors are whitin their range and report
        if (this.extend) {
            return climberSubsystem.isExtended();
        }

        return climberSubsystem.isRetracted();

    }

    @Override
    public void end(boolean interrupted) {
        climberSubsystem.stop();
    }
}
