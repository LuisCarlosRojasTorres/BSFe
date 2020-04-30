/*
 * BEND STIFFENER - RISER SYSTEM
 */
package pckgBSF;

/**
 *
 * @author Luis Carlos A. Rojas Torres <luiscarlos.bsf@oceanica.ufrj.br>
 */
public class BsfRsr {
    protected Riser rsr;
    protected BendStiffener bsf;
    protected double forceBC,angleBC;
    
    
    public BsfRsr(){
        this.rsr = new Riser();
        this.bsf = new BendStiffener();
        this.forceBC = 62500;
        this.angleBC = bsf.getPi()/4;
    }
    public BsfRsr(double forceBC){
        this.rsr = new Riser();
        this.bsf = new BendStiffener();
        this.forceBC = forceBC;
        this.angleBC = bsf.getPi()/4;
    }
    public BsfRsr(double forceBC,double angleBC){
        this.rsr = new Riser();
        this.bsf = new BendStiffener();
        this.forceBC = forceBC;
        this.angleBC = angleBC;
    }
    public BsfRsr(Riser dummy_rsr,BendStiffener dummy_bsf,double forceBC,double angleBC){
        this.rsr = dummy_rsr;
        this.bsf = dummy_bsf;
        this.forceBC = forceBC;
        this.angleBC = angleBC;
    }
    
    public void setForceBC(double force){
        this.forceBC = force;
    }
    public void setAngleBC(double angle){
        this.angleBC = angle;
    }
    public double getForceBC(){
        return this.forceBC;
    }
    public double getAngleBC(){
        return this.angleBC;
    }
    public double getEIs(double s){
        return (rsr.getEI()+bsf.getEIs(s));
    }
    public double getDEIs(double s){
        if(s == this.bsf.getTotalLength()){
            return 0;
        }
        return ((bsf.getEIs(s+0.001)-bsf.getEIs(s))/0.001);
    }
    @Override
    public String toString(){
        return String.format("%s%n%s%n%s%n%s%.2f%n%s%.4f%n",bsf,rsr,
                "Boundary Conditions: ",
                ">> Force (N): ",this.getForceBC(),
                ">> Angle (rad): ",this.getAngleBC());
    }
    
    
    
    
    
}
