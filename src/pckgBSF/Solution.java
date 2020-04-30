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
public class Solution extends BsfRsr{
    private int size = 100;
    private double h;
    private double s[] = new double[size+1];
    private double x[] = new double[size+1];
    private double y[] = new double[size+1];
    private double phi[] = new double[size+1];
    private double k[] = new double[size+1];
    
    public Solution(){
        super();
        
        x[0] = 0;
        y[0] = 0;
        phi[0] = 0;
        k[0] = (this.getAngleBC()-phi[0])/this.rsr.getTotalLength();
        
        h =  this.rsr.getTotalLength()/size;
        for(int i = 0;i <= size ; i++){
            s[i] = h*i;
        } 
    }
    
    public Solution(double forceBC){
        super(forceBC);
        
        x[0] = 0;
        y[0] = 0;
        phi[0] = 0;
        k[0] = (this.getAngleBC()-phi[0])/this.rsr.getTotalLength();
        
        h =  this.rsr.getTotalLength()/size;
        for(int i = 0;i <= size ; i++){
            s[i] = h*i;
        } 
    }
    
    public Solution(double forceBC,double angleBC){
        super(forceBC,angleBC);
        
        x[0] = 0;
        y[0] = 0;
        phi[0] = 0;
        k[0] = (this.getAngleBC()-phi[0])/this.rsr.getTotalLength();
        
        h =  this.rsr.getTotalLength()/size;
        for(int i = 0;i <= size ; i++){
            s[i] = h*i;
        } 
    }
    
    public Solution(Riser dummy_rsr,BendStiffener dummy_bsf,double forceBC,double angleBC){
        super(dummy_rsr,dummy_bsf,forceBC,angleBC);
        x[0] = 0;
        y[0] = 0;
        phi[0] = 0;
        k[0] = (this.getAngleBC()-phi[0])/this.rsr.getTotalLength();
        
        h =  this.rsr.getTotalLength()/size;
        for(int i = 0;i <= size ; i++){
            s[i] = h*i;
        } 
    }
    
    public double fx(double phi){
        return Math.cos(phi);
    }
    public double fy(double phi){
        return Math.sin(phi);
    }
    public double fphi(double k){
        return k;
    }
    public double fk(double s,double phi,double k){
        return -(this.getDEIs(s)*k 
                +this.getForceBC()*Math.sin(this.angleBC-phi))/this.getEIs(s);
    }
    
    //Runge Kutta K's
    public double kx1(int i){
        return this.h*this.fx(this.phi[i]);
    }
    public double ky1(int i){
        return this.h*this.fy(this.phi[i]);
    }
    public double kphi1(int i){
        return this.h*this.fphi(this.k[i]);
    }
    public double kk1(int i){
        return this.h*this.fk(this.s[i],this.phi[i],this.k[i]);
    }
    
    public double kx2(int i,double kphi1){
        return this.h*this.fx(this.phi[i]+0.5*kphi1);
    }
    public double ky2(int i,double kphi1){
        return this.h*this.fy(this.phi[i]+0.5*kphi1);
    }
    public double kphi2(int i,double kk1){
        return this.h*this.fphi(this.k[i]+0.5*kk1);
    }
    public double kk2(int i,double kphi1,double kk1){
        return this.h*this.fk(this.s[i]+0.5*this.h,this.phi[i]+0.5*kphi1,this.k[i]+0.5*kk1);
    }
    
    public double kx3(int i,double kphi2){
        return this.h*this.fx(this.phi[i]+0.5*kphi2);
    }
    public double ky3(int i,double kphi2){
        return this.h*this.fy(this.phi[i]+0.5*kphi2);
    }
    public double kphi3(int i,double kk2){
        return this.h*this.fphi(this.k[i]+0.5*kk2);
    }
    public double kk3(int i,double kphi2,double kk2){
        return this.h*this.fk(this.s[i]+0.5*this.h,this.phi[i]+0.5*kphi2,this.k[i]+0.5*kk2);
    }
    
    public double kx4(int i,double kphi3){
        return this.h*this.fx(this.phi[i]+kphi3);
    }
    public double ky4(int i,double kphi3){
        return this.h*this.fy(this.phi[i]+kphi3);
    }
    public double kphi4(int i,double kk3){
        return this.h*this.fphi(this.k[i]+kk3);
    }
    public double kk4(int i,double kphi3,double kk3){
        return this.h*this.fk(this.s[i]+this.h,this.phi[i]+kphi3,this.k[i]+kk3);
    }
    
    public void solve(){
        double kx1,kx2,kx3,kx4;
        double ky1,ky2,ky3,ky4;
        double kphi1,kphi2,kphi3,kphi4;
        double kk1,kk2,kk3,kk4;
        
        double numberOfShoots = 0;
        double sk=0,sk1=0,sk2=0; // s_{k}, s_{k+1} , s_{k+2}
        double phiNsk=0,phiNsk1=0;
        
        do{
            
            if (numberOfShoots == 1){
                phiNsk = phi[this.size];
                sk = k[0];
                sk1 = sk + (this.angleBC - phiNsk )/(s[this.size]-s[0]);
                
                k[0] = sk1;
                
            }
            else if(numberOfShoots > 1){
                phiNsk1 = phi[this.size];
                sk2 = sk +(sk1-sk)*(this.angleBC-phiNsk)/(phiNsk1-phiNsk);
                k[0] = sk2;
                
                sk = sk1;
                sk1 = sk2;
                
                phiNsk = phiNsk1;
            }
            
            System.out.println(">> Number of Shoot: "+numberOfShoots);
            System.out.println("  k[0] = "+k[0]);
            for(int i = 0; i < this.size; i++){
                
                kphi1 = this.kphi1(i);
                kk1 = this.kk1(i);
                kx1 = this.kx1(i);
                ky1 = this.ky1(i);
                
                
                kphi2 = this.kphi2(i,kk1);
                kk2 = this.kk2(i,kphi1,kk1);
                kx2 = this.kx2(i,kphi1);
                ky2 = this.ky2(i,kphi1);
                
                
                kphi3 = this.kphi3(i,kk2);
                kk3 = this.kk3(i,kphi2,kk2);
                kx3 = this.kx3(i,kphi2);
                ky3 = this.ky3(i,kphi2);
                
                
                kphi4 = this.kphi4(i,kk3);
                kk4 = this.kk4(i,kphi3,kk3);
                kx4 = this.kx4(i,kphi3);
                ky4 = this.ky4(i,kphi3);
                
                
                
                phi[i+1] = phi[i] + (kphi1+2*kphi2+2*kphi3+kphi4)/6;
                k[i+1]   = k[i]   + (kk1+2*kk2+2*kk3+kk4)/6;
                x[i+1]   = x[i]   + (kx1+2*kx2+2*kx3+kx4)/6;
                y[i+1]   = y[i]   + (ky1+2*ky2+2*ky3+ky4)/6;
                
                                
            }
            numberOfShoots++;
            System.out.printf(" i = %d, phiBC = %.4f, phi = %.4f, k = %.4f\n",
                    this.size, this.getAngleBC(),phi[this.size],k[this.size]);
            
            
        }
        while(numberOfShoots<10);
        // while(Math.abs(phi[this.size]-this.getAngleBC())>0.01);
        
    }
        
    public void show_s(){
        for(double i:this.s){
            System.out.printf("%.4f%n",i);
        }
    }
    public void show_x(){
        for(double i:this.x){
            System.out.printf("%.4f%n",i);
        }
    }
    public void show_y(){
        for(double i:this.y){
            System.out.printf("%.4f%n",i);
        }
    }
    public void show_phi(){
        for(double i:this.phi){
            System.out.printf("%.4f%n",i);
        }
    }
    public void show_k(){
        for(double i:this.k){
            System.out.printf("%.4f%n",i);
        }
    }
    
    
    
}
