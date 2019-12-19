import java.util.Scanner;

public class RLCparaleloDeg {
    public double fonteCorrente=4;
    public double R1=2.0;
    public double C1=0.5;
    public double L1=1.0;
    public double I0=1.0;//Para deixar explícito
    public double V0=0;
    public double A,B,alfa,omega;//Variavies
    public double s1,s2;//Expoentes da exponencial
    

    
   
    public double calcularALFA(double R, double C) {
        if(C==0 || R==0){
            System.out.println("Impossivel dividir por zero");
            return -1.0;
        }
        
        return 1/(2*R*C);
    }
    public double calcularOMEGA(double L,double C){
        if( C==0 || L==0){
             System.out.println("Impossivel dividir por zero");
             return -1.0;
        }
        return(1/(Math.sqrt(C*L)));
        
    }
    
   public int Amortecimento(double alfa,double omega){
       if(alfa>omega)//super critico
           return 1;
       if(alfa==omega)
           return 2;
       if(alfa<omega)
           return 3;
       return 0;//IDE reclamando
       
       
   } 
    
   
   public void calcularS1(double alfa,double omega){
       double X,Y;
       //Precisamos tratar um possível numero imaginario, e guardar os valores X +-Y*i
       X=-alfa;
       Y=(alfa*alfa)-(omega*omega);//Calcular valor de sqrt(Y) a seguir,no if se entrar
       //No caso de numero imaginario, E^(X*t) *(G*cos(Y) + H*sen(Y)); G e H são constantes, e E->euler
       if(Y<0)//Numero imaginario, pois sqrt(Y<0)->numero imaginario;
       {
           Y=Math.sqrt(-(alfa*alfa)+(omega*omega));//Inverteu-se os valores, entao dara positivo. Poderia ser usado um absoluto.Se A-B<0 , entao B-A > 0
           //Guarda-se X e Y em s1 e s2, que será conhecido pelo algoritmo o seu uso, pois verifica-se o amortecimento.
           this.s1=X;//Parte real
           this.s2=Y;
           
       }
       else{//Situação "Sem numeros imaginarios"
           this.s1=(-alfa + Math.sqrt((alfa*alfa)-(omega*omega)));
           this.s2=(-alfa - Math.sqrt((alfa*alfa)-(omega*omega)));
       }
           
       
            
       
   }
   
     
   
   public double calcularWD(double alfa,double omega){
       return Math.sqrt( -(alfa*alfa)+(omega*omega)); //- primeiro + depois
             
   }
   public double sistemaLinear(double a11,double a12,double b1,double a21,double a22,double b2){
       
   
       double Rx1,Rx2;//Resultado
       double M;//utilizado para resolucao
       //Sistema linear 2x2
       //começando pivoteamento, nao verificando se a11 ou a22 =0
       //L2=L2-M*L1;
         M=a21/a11;
         a22 -= a12*M;    b2=b2-b1*M;
         
         return b2/a22;      
         
       
   } 
   
   public double calcularA(double b1, double a12, double B,double a11){
       return (b1-(a12*B))/a11; //Fazendo separado para retornar dois valores
       
   }
   
 
   public void variarValores(){
       //Funcao para mudar os valores do problema, mantendo o circuito
       //no mesmo formato
       Scanner scan= new Scanner(System.in);
       
       System.out.println("Insira um valor para a fonte ");
       this.fonteCorrente=scan.nextDouble();
       
       System.out.println("Insira um valor para o resistor 1(30 ohm) ");
       this.R1=scan.nextDouble();
       
       System.out.println("Insira um valor para o capacitor 1");
       this.C1=scan.nextDouble();
       
       System.out.println("Insira um valor para o indutor 1");
       this.L1=scan.nextDouble();
             
       this.I0=this.fonteCorrente;
       
   }
   
   
    
    
}
