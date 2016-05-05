package org.usfirst.frc.team1241.robot;

public class Test {
	public static double pixelToDegree(double pixel){
//		return Math.atan(((pixel-320)*Math.tan(0.55518925))/320);
    	return Math.toDegrees(2*Math.atan(((pixel-320)*Math.tan(Math.toRadians(31.81)))/(2*320)));
    }
	
	public static void main(String[]args) {
		System.out.println(pixelToDegree(620));
		System.out.println(Math.toDegrees(Math.atan(((620-320)*Math.tan(Math.toRadians(31.81)))/320)));
		
//		System.out.println(Math.toRadians(31.81));
//		System.out.println(Math.tan(Math.toRadians(31.81)));
//		System.out.println(Math.tan(Math.toRadians(31.81))/320.0);
	}

}
