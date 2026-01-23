package commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends Command{

//TODO imploment/ create command for buttons 
ClimberSubsystem climberSubsystem;
boolean forwards;
public ClimberCommand( ClimberSubsystem climberSubsystem, boolean forwards){
    this.forwards = forwards;
    this.climberSubsystem= climberSubsystem;
}
 @Override
public void initialize() {
    if (forwards){ 
         climberSubsystem.climb();

    }else{
        climberSubsystem.dumbClimb();
    }
}

@Override
    public boolean isFinished() {
        return climberSubsystem.extend();
        
    }

    @Override
    public void end(boolean interrupted) {
        climberSubsystem.stop();
    }



    
}
