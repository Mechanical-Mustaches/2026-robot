package frc.robot.subsystems;

import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class ClimberSubsystem implements Subsystem {

    private SparkMax climber;
    private SparkMaxConfig climberConfig;
    private ClosedLoopConfig climberClosedLoopConfig;
        

    public ClimberSubsystem() {
        // TODO: Get motor ID
        climber = new SparkMax(0, MotorType.kBrushless);

        climberConfig = new SparkMaxConfig();

        // TODO: Update PID constants
        climberClosedLoopConfig = new ClosedLoopConfig()
                .pid(0.1, 0, 0)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
                
            climberConfig
                // TODO: Find range of rotations needed 
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast);

            climberConfig.apply(climberClosedLoopConfig);

       climber.configure(climberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }


    public void climb(){

       var controller= climber.getClosedLoopController();
        controller.setSetpoint(1, ControlType.kPosition);
    } 


    public void dumbClimb(){
         var controller= climber.getClosedLoopController();
        controller.setSetpoint(-1, ControlType.kPosition);

    } 
    public boolean extend(){

         var controller=climber.getClosedLoopController();
        if(controller.setSetpoint(-1, ControlType.kPosition) != null){
            return true;

        }else if(controller.setSetpoint(1, ControlType.kPosition) != null){
            return false;
        }
                return false;
    }
    

    public void stop(){
var controller=climber.getClosedLoopController();
controller.setSetpoint(0, ControlType.kPosition);

    }
}
