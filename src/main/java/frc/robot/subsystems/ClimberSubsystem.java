package frc.robot.subsystems;

import com.revrobotics.REVLibError;
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

    private static int MAIN_EXTEND_RANGE = -1;
    private static int SECONDARY_EXTEND_RANGE = -1;
    private static int MAIN_CLIMB_RANGE = 1;
    private static int SECONDARY_CLIMB_RANGE = 1;
    private static double MAIN_TOLERANCE = 0.1;
    private static double SECONDARY_TOLERANCE = 0.1;

    private SparkMax mainClimber;
    private SparkMax secondaryClimber;

    private SparkMaxConfig mainClimberConfig;
    private SparkMaxConfig secondaryClimberConfig;

    private ClosedLoopConfig mainClimberClosedLoopConfig;
    private ClosedLoopConfig secondaryClimberClosedLoopConfig;

    public ClimberSubsystem() {
        // main climber configuration:

        // TODO: Get motor ID
        mainClimber = new SparkMax(0, MotorType.kBrushless);

        mainClimberConfig = new SparkMaxConfig();

        // TODO: Update PID constants
        mainClimberClosedLoopConfig = new ClosedLoopConfig()
                .pid(0.1, 0, 0)
                .outputRange(MAIN_EXTEND_RANGE, MAIN_CLIMB_RANGE)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder);

        mainClimberConfig
                // TODO: Find range of rotations needed
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast);

        mainClimberConfig.apply(mainClimberClosedLoopConfig);

        mainClimber.configure(mainClimberConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Secondary climber configuration:
        // TODO: get real motor ID
        secondaryClimber = new SparkMax(1, MotorType.kBrushless);

        secondaryClimberConfig = new SparkMaxConfig();

        // TODO: Update PID constants
        secondaryClimberClosedLoopConfig = new ClosedLoopConfig()
                .pid(0.1, 0, 0)
                .outputRange(MAIN_EXTEND_RANGE, MAIN_CLIMB_RANGE)
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder);

        secondaryClimberConfig
                // TODO: Find range of rotations needed
                .smartCurrentLimit(40)
                .idleMode(IdleMode.kCoast);

        secondaryClimberConfig.apply(secondaryClimberClosedLoopConfig);

        secondaryClimber.configure(secondaryClimberConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public void extend(int stage) {

        if (stage == 0) {
            var controller = mainClimber.getClosedLoopController();
            controller.setSetpoint(MAIN_EXTEND_RANGE, ControlType.kPosition);
        }

        if (stage == 1) {
            var controller = secondaryClimber.getClosedLoopController();
            controller.setSetpoint(SECONDARY_EXTEND_RANGE, ControlType.kPosition);
        }

    }

    public void retract(int stage) {
        if (stage == 0) {
            var controller = mainClimber.getClosedLoopController();
            controller.setSetpoint(MAIN_CLIMB_RANGE, ControlType.kPosition);
        }

        if (stage == 1) {
            var controller = secondaryClimber.getClosedLoopController();
            controller.setSetpoint(SECONDARY_CLIMB_RANGE, ControlType.kPosition);
        }
    }

    public boolean isExtended(int stage) {// mehtod cheks if the motor in extended position

        if (stage == 0) {
            var position = mainClimber.getEncoder().getPosition();
            return position < MAIN_EXTEND_RANGE + MAIN_TOLERANCE
                    || position > MAIN_EXTEND_RANGE - MAIN_TOLERANCE;// adds range for motor to return its position

        }
        if (stage == 1) {
            var position = secondaryClimber.getEncoder().getPosition();

            return position < SECONDARY_EXTEND_RANGE + MAIN_TOLERANCE
                    || position > SECONDARY_EXTEND_RANGE - SECONDARY_TOLERANCE;// adds range for motor to return its position

        }
        return false;
    }

    public boolean isRetracted(int stage) {// mehtod cheks if the motor in retracted position
        if (stage == 0) {
            var position = mainClimber.getEncoder().getPosition();
            return position < MAIN_EXTEND_RANGE + MAIN_TOLERANCE
                    || position > MAIN_EXTEND_RANGE - MAIN_TOLERANCE;// adds range for motor to return its position
        }
        if (stage == 1) {
            var position = secondaryClimber.getEncoder().getPosition();

            return position < SECONDARY_EXTEND_RANGE + SECONDARY_TOLERANCE
                    || position > SECONDARY_EXTEND_RANGE - SECONDARY_TOLERANCE;// adds range for motor to return its position

        }
        return false;

    }

    public void stop() {
        var controller = mainClimber.getClosedLoopController();
        controller.setSetpoint(0, ControlType.kPosition);

    }

}
