// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.TankDriveSubsystem;

public class DriveCommand extends Command {

  private DoubleSupplier m_x;
  private DoubleSupplier m_y;
  private BooleanSupplier m_stop;


  /** Creates a new Drive. */
  public DriveCommand(DoubleSupplier _x, DoubleSupplier _y, BooleanSupplier _stop) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(TankDriveSubsystem.getInstance());

    this.m_x = _x;
    this.m_y = _y;
    this.m_stop = _stop;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    double deadband = 0.05;
    
    double m_xDouble = m_x.getAsDouble();
    double m_yDouble = m_y.getAsDouble();
    
    if(m_xDouble >= 0 && m_xDouble < deadband){
      m_xDouble = 0;
    }
    if(m_xDouble < 0 && m_xDouble > -deadband){
      m_xDouble = 0;
    }
    if(m_yDouble >= 0 && m_yDouble < deadband){
      m_yDouble = 0;
    }
    if(m_yDouble < 0 && m_yDouble > -deadband){
      m_yDouble = 0;
    }
    
    TankDriveSubsystem.getInstance().drive(m_xDouble, m_yDouble, m_stop.getAsBoolean());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
