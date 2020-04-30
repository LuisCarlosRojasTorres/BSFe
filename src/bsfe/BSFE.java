/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsfe;
import pckgBSF.*;
/**
 *
 * @author Luis Carlos A. Rojas Torres <luiscarlos.bsf@oceanica.ufrj.br>
 */
public class BSFE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BendStiffener BSF = new BendStiffener();
        Riser RSR = new Riser();
        BsfRsr sys1 = new BsfRsr();
        
        
        Solution sol = new Solution(62500,0.7854);
        sol.solve();
        
        //sol.show_phi();
    }
    
}
