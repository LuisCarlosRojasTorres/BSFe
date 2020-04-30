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
public class Riser extends Beam{
    private double EI;
    
    public Riser(){
        super(3.2);
        this.EI = 10000;
    }
    public Riser(double totalLength,double EI){
        super(totalLength);
        this.EI = EI;
    }
    public Riser(Riser rsr){
        this(rsr.getTotalLength(),rsr.getEI());
    }
    
    public double getEI(){
        return this.EI;
    }
    public void setEI(double EI){
        if(EI > 0){
            this.EI = EI;
        }
    }
    
    @Override
    public double getEIs(double s){
        return this.getEI();
    }
    @Override
    public String toString(){
        return String.format("%s%n%s%.2f%n%s%.2f%n",
                "RISER:",
                ">> Total length (m): ",this.getTotalLength(),
                ">> EI (Nm^2): ",this.getEI());
    }
    
    
}
