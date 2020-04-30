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
public abstract class Beam {
    private double totalLength;
    
    
    public Beam(double tL){
        this.totalLength = tL;
    }
    
    public void setTotalLength(double tL){
        if(tL > 0){
            this.totalLength = tL;
        }        
    }
    public double getTotalLength(){
        return this.totalLength;
    }
    
   public abstract double getEIs(double s);
    
}
