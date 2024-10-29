// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TankDriveConstants;


public class TankDriveSubsystem extends SubsystemBase {
  

  private static TankDriveSubsystem m_instance;

  private TalonFX m_leftLeader;
  private TalonFX m_leftFollower;
  private TalonFX m_rightLeader;
  private TalonFX m_rightFollower;

  private final DutyCycleOut leftOut ;
  private final DutyCycleOut rightOut;

  /** Creates a new TankDriveSubsystem. */
  public TankDriveSubsystem() {

    m_leftLeader = new TalonFX(TankDriveConstants.k_leftTankDriveMotor1);
    m_leftFollower = new TalonFX(TankDriveConstants.k_leftTankDriveMotor2);
    m_rightLeader = new TalonFX(TankDriveConstants.k_rightTankDriveMotor1); 
    m_rightFollower = new TalonFX(TankDriveConstants.k_rightTankDriveMotor2);

    leftOut = new DutyCycleOut(0);
    rightOut = new DutyCycleOut(0);

    m_leftLeader.setNeutralMode(NeutralModeValue.Brake);
    m_leftFollower.setNeutralMode(NeutralModeValue.Brake);
    m_rightLeader.setNeutralMode(NeutralModeValue.Brake);
    m_rightFollower.setNeutralMode(NeutralModeValue.Brake);


    var leftConfiguration = new TalonFXConfiguration();
    var rightConfiguration = new TalonFXConfiguration();

    /* User can optionally change the configs or leave it alone to perform a factory default */
    leftConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    rightConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    m_leftLeader.getConfigurator().apply(leftConfiguration);
    m_leftFollower.getConfigurator().apply(leftConfiguration);
    m_rightLeader.getConfigurator().apply(rightConfiguration);
    m_rightFollower.getConfigurator().apply(rightConfiguration);

    /* Set up followers to follow leaders */
    m_leftFollower.setControl(new Follower(m_leftLeader.getDeviceID(), false));
    m_rightFollower.setControl(new Follower(m_rightLeader.getDeviceID(), false));

    
  
    m_leftLeader.setSafetyEnabled(true);
    m_rightLeader.setSafetyEnabled(true);
  }

  public void drive(double x, double y, boolean stop){
    double fwd = -x;
    double rot = y;
    /* Set output to control frames */
    leftOut.Output = fwd + rot;
    rightOut.Output = fwd - rot;
    /* And set them to the motors */
    if (!stop) {
      m_leftLeader.setControl(leftOut);
      m_rightLeader.setControl(rightOut);
    }
  }

  public static TankDriveSubsystem getInstance(){
    if(m_instance == null){
      m_instance = new TankDriveSubsystem();
    }
    return m_instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
}
}