
package org.usfirst.frc.team1241.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static Ultrasonic ultra;
	
	public static OI oi;

    Command autonomousCommand;
    public SendableChooser autoChooser;
    
    DataOutput output;
    
    DoubleSolenoid shooterPiston;
    DoubleSolenoid outtakePiston;
    DoubleSolenoid hood;
    
    CANTalon intake;
    CANTalon arm;
    CANTalon conveyor;
    CANTalon shooter;
    
    Encoder flywheel;
    Encoder turret;
    Potentiometer pot;
    
    int counter = 0;
    boolean atSpeed = false;
    double cogx = 0;
    String direction = "";
    
    PIDController speed;
    
    double[] targetNum = new double[8];

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	ultra = new Ultrasonic(6,7);	
    	ultra.setAutomaticMode(true);
    	
		oi = new OI();
        // instantiate the command used for the autonomous period
		output = new DataOutput("data.txt");
		autoChooser = new SendableChooser();
		SmartDashboard.putData("Auto Mode", autoChooser);
		
		intake = new CANTalon(ElectricalConstants.INTAKE);
		arm = new CANTalon(ElectricalConstants.ARM);
		conveyor = new CANTalon(ElectricalConstants.CONVEYOR);
		shooter = new CANTalon(ElectricalConstants.SHOOTER);
		
		shooterPiston = new DoubleSolenoid(ElectricalConstants.SHOOTERPISTON_A,
											ElectricalConstants.SHOOTERPISTON_B);
		outtakePiston = new DoubleSolenoid(ElectricalConstants.OUTTAKEPISTON_A,
											ElectricalConstants.OUTTAKEPISTON_B);
		hood = new DoubleSolenoid(ElectricalConstants.HOOD_A,
											ElectricalConstants.HOOD_B);
		
		flywheel = new Encoder(0,1,false,Encoder.EncodingType.k4X);
		turret = new Encoder(8,9,false,Encoder.EncodingType.k4X);
		//turret.setDistancePerPulse(ElectricalConstants.elevEncoderDegPerTick);
		
		speed =  new PIDController(0.000015,0.0,0.00001);
		
		pot = new AnalogPotentiometer(new AnalogInput(0), 360);
		//flywheel.setDistancePerPulse(1/128);
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        // schedule the autonomous command (example)
    	autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
    	if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {
    }

    /**
     * This function is called periodically during operator control
     */
    @SuppressWarnings("deprecation")
	public void teleopPeriodic() {
    	arm.set(oi.getDriveLeftY());
    	
    	if(oi.getDriveRightBumper()) {
    		shooterPiston.set(DoubleSolenoid.Value.kReverse);
    	}
    	else {
    		shooterPiston.set(DoubleSolenoid.Value.kForward);
    	}
    	
    	if(oi.getDriveLeftBumper()) {
    		outtakePiston.set(DoubleSolenoid.Value.kReverse);
    	}
    	else {
    		outtakePiston.set(DoubleSolenoid.Value.kForward);
    	}
    	
    	if(oi.getDriveXButton())
    		output.close();
    	
    	if(oi.getDriveAButton()) {
    		intake.set(1);
    		conveyor.set(-1);
    	}
    	else if(oi.getDriveBButton()) {
    		intake.set(-1);
    		conveyor.set(1);
    	}
    	else if(oi.getDriveLeftTrigger()) {
    		setSpeed(3400);
    		if(flywheel.getRate()*60/128 < 3450 && flywheel.getRate()*60/128 > 3350)
    			atSpeed = true;
    		else
    			atSpeed = false;	
    	}
    	else if(oi.getDriveRightTrigger()) {
    		if(flywheel.getRate()*60/128 < 4050 && flywheel.getRate()*60/128 > 3950)
    			atSpeed = true;
    		else
    			atSpeed = false;
    		setSpeed(4000);
    	}
    	else if(oi.getDriveLeftBumper())
    	{
    		shooter.set(0.75);
    	}
    	else if(oi.getDriveRightBumper())
    	{
    		shooter.set(1);
    	}
    	else {
    		intake.set(0);
    		shooter.set(0);
    		conveyor.set(0);
    		atSpeed = false;
    		speed.resetPID();
    	}
    	
    	updateSmartDashboard();
    	
    	NetworkTable server = NetworkTable.getTable("SmartDashboard");
    	double clock;
        try{
        	clock = server.getDouble("CLOCK_TIME");
        	SmartDashboard.putNumber("clock", clock);
//    		targetNum = server.getNumberArray("MEQ_COORDINATES");
//    		SmartDashboard.putNumber("number0", (double) targetNum[0]);
//    		SmartDashboard.putNumber("number1", (double) targetNum[1]);
//    		SmartDashboard.putNumber("number2", (double) targetNum[2]);
//    		SmartDashboard.putNumber("number3", (double) targetNum[3]);
//    		SmartDashboard.putNumber("number4", (double) targetNum[4]);
//    		SmartDashboard.putNumber("number5", (double) targetNum[5]);
//    		SmartDashboard.putNumber("number6", (double) targetNum[6]);
//    		SmartDashboard.putNumber("number7", (double) targetNum[7]);
        }
        catch(Exception ex) {
        	System.out.println("Unable to get Coordinates");
        }
        try {
        	double distance = ((targetNum[0] - targetNum[2]) + (targetNum[6]-targetNum[4]))/2;
        	distance = 20*640/(2*distance*Math.tan(Math.toRadians(27.98)));
            SmartDashboard.putNumber("Distance", distance);
        } catch(Exception e){}
        
        
        
        /*this.cogx = server.getNumber("COG_X",0);
        if(cogx==0) {
        	direction = "OUT";
        }
        if(cogx < 300) {
        	direction = "LEFT";
        }
        else if(cogx > 400) {
        	direction = "RIGHT";
        }
        else {
        	direction = "CENTER";
        }*/
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    double timer;
    public void updateSmartDashboard() {
    	SmartDashboard.putNumber("Turret",turret.get()/43.8857);
    	SmartDashboard.putNumber("Ultrasound", ultra.getRangeInches());
    	
    	timer++;
    	SmartDashboard.putNumber("Timer", timer);
    	if (counter % 10 == 0){
    		output.writeString(counter,""+flywheel.getRate()*60/128);
    	}
    	counter++;
    	SmartDashboard.putNumber("RPM", flywheel.getRate()*60/128);
    	SmartDashboard.putNumber("Distance", flywheel.getDistance());
    	SmartDashboard.putBoolean("AT SPEED", atSpeed);
    	
    	SmartDashboard.putString("Direction", direction);
    	
    	SmartDashboard.putDouble("Potentiometer", pot.get());
    }
    
    public void setSpeed(double setPoint) {
    	double output = speed.calcPIDVelocity(setPoint, flywheel.getRate()*60/128, 1);
    	
    	output = output/Math.abs(output)*(1 - Math.pow(0.2,(Math.abs(output))));
    	
    	shooter.set(output);
    }
}
