/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pckgBSF;

/**
 *
 * @author Luis Carlos A. Rojas Torres <luiscarlos.bsf@oceanica.ufrj.br>
 */
public class BendStiffener extends Beam{
    private double outerDiameter,innerDiameter,cylindricalLength,thickness;
    private double E; //Young Modulus [Pa]
    private final double Pi = 3.141592;
    
    public BendStiffener(){
        super(1.9);
        this.outerDiameter = 0.65;
        this.innerDiameter = 0.18;
        this.cylindricalLength = 0.2;
        this.thickness = 0.01;
        this.E = 45E6;
    }
    public BendStiffener(double tL,double oD,double iD,double cL,double t,double E){
        super(tL);
        if(oD > 0){
            this.outerDiameter = oD;
        } 
        if(iD > 0){
            this.innerDiameter = iD;
        } 
        if(cL < this.getTotalLength()){
            this.cylindricalLength = cL;
        }
        if(t > 0){
            this.thickness = t;
        }
        if(E > 0){
            this.E = E;
        }
    }
    public BendStiffener(BendStiffener bsf){
        this(bsf.getTotalLength(),bsf.getOuterDiameter(),bsf.getInnerDiameter(),
                bsf.getCylindricalLength(),bsf.getThickness(),bsf.getE());
    }
    
    //SETTERS
    public void setOuterDiameter(double oD){
        if(oD > 0){
            this.outerDiameter = oD;
        }        
    }
    public void setInnerDiameter(double iD){
        if(iD > 0){
            this.innerDiameter = iD;
        }        
    }
    public void setCylindricalLength(double cL){
        if(cL < this.getTotalLength()){
            this.cylindricalLength = cL;
        }        
    }
    public void setThickness(double t){
        if(t > 0){
            this.thickness = t;
        }        
    }
    public void setE(double E){
        if(E > 0){
            this.E = E;
        }
    }   
    //GETTERS
    public double getOuterDiameter(){
        return this.outerDiameter;
    }
    public double getOuterDiameter(double x){
        if(x>=0 && x <=  this.getCylindricalLength()){
            return this.getOuterDiameter();
        }
        else if(x > this.getCylindricalLength() && x <= this.getTotalLength() ){
            double xR;
            double iR = this.getInnerDiameter()/2;
            double oR = this.getOuterDiameter()/2;
            xR = (iR+this.getThickness()) + 
                    ((this.getTotalLength()-x)/(this.getTotalLength()-this.getCylindricalLength()))*
                    (oR-(iR+this.getThickness()));
            return 2*xR;
        }
        else{
            return 0;
        }
    }
    public double getInnerDiameter(){
        return this.innerDiameter;
    }
    public double getCylindricalLength(){
        return this.cylindricalLength;
    }
    public double getThickness(){
        return this.thickness;
    }
    public double getPi(){
        return this.Pi;
    }
    public double getE(){
        return this.E;
    } 
    //Abstract functions
    @Override
    public double getEIs(double s){
        if(this.getOuterDiameter(s)==0){
            return 0;
        }
        else{
            return this.getE()*this.getPi()*(Math.pow(this.getOuterDiameter(s)/2,4)-Math.pow(this.getInnerDiameter()/2,4))/4;
        }
        
    }
    @Override
    public String toString(){
        return String.format("%s%n%s%.2f%n%s%.2f%n%s%.2f%n%s%.2f%n%s%.2f%n%s%.2f%n",
                "BEND STIFFENER: ",
                ">> Total length (m): ",this.getTotalLength(),
                ">> Outer diameter (m): ",this.getOuterDiameter(),
                ">> Inner diameter (m): ",this.getInnerDiameter(),
                ">> Cylindrical length (m): ",this.getCylindricalLength(),
                ">> Thickness (m): ",this.getThickness(),
                ">> Elastic modulus (Pa): ",this.getE());
    }
    
}