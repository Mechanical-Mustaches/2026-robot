package frc.robot.subsystems;

import java.lang.annotation.Target;
import java.util.function.Supplier;

import org.opencv.core.Point;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class TurretSubsystem implements Subsystem {

    private enum TargetPosition {
        Hub(new Point(0, 0)),
        FeedLeft(new Point(-1, 0)),
        FeedRight(new Point(1, 0));

        private final Point point;

        private TargetPosition(Point point) {
            this.point = point;
        }

        public Point getPoint() {
            return this.point;
        }

    }

    private SparkMax flywheelMotor;
    private SparkMax elevationMotor;
    private SparkMax azimuthMotor;

    private SparkMaxConfig azimuthMotorConfig;
    private ClosedLoopConfig azimuthClosedLoopConfig;

    private Supplier<SwerveDriveState> getSwerveState;

    public TurretSubsystem(Supplier<SwerveDriveState> getSwerveState) {
        this.flywheelMotor = new SparkMax(0, MotorType.kBrushless);
        this.elevationMotor = new SparkMax(0, MotorType.kBrushless);
        this.azimuthMotor = new SparkMax(0, MotorType.kBrushless);

        azimuthClosedLoopConfig = new ClosedLoopConfig().pid(0.1, 0, 0);
        azimuthMotorConfig = new SparkMaxConfig();

        azimuthMotorConfig
                .apply(azimuthClosedLoopConfig);

        azimuthMotor.configure(azimuthMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.getSwerveState = getSwerveState;
    }

    private TargetPosition getTargetPosition() {
        // TODO: Return the right position
        return null;
    }

    @Override
    public void periodic() {
        var targetPosition = getTargetPosition();

        if (targetPosition == null) {
            return;
        }

        var currentState = this.getSwerveState.get();

        var angle = Math.atan2(targetPosition.point.y - currentState.Pose.getY(),
                targetPosition.point.x - currentState.Pose.getX());
        azimuthMotor.getClosedLoopController().setSetpoint(angle, ControlType.kPosition);

    }

}
