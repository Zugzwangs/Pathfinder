struct noeud
{
 int g, h, f;
 points ref;    
 points parent;
};

class cellule{
      
private :
        int longueur;
        int largueur;
        points reference;
        points data;
        float occupation;

public :
       cellule();
       cellule(int, int, points); 
       points get_ref();
       void  set_ref(points );
       void  set_occupation(float );
       void  set_data(points );
       points cellule::get_data();
       float get_occupation();
       void  set_param(int, int, points);
       bool  test_in(points);
       void  affiche();
                       
};

/************************ planification d'un chemin ***************************/
void A_star(cellule W[100][100], points depart, points arrive)
{
int x,y;
int lg_O_list = 0;
int lg_C_list = 0;      
noeud start, courant, chemin; 
noeud O_list[1000]; 
noeud C_list[1000];

//marquage de l'arrive
W[(int)arrive.get_x()][(int)arrive.get_y()].set_occupation(9); 
 
//initialisation
start.ref = depart;
start.g = -2000;
evaluer(start, start, arrive);
O_list[1] = start;
inserer(O_list, start, lg_O_list);

//corps de l'algo
do
{
courant = extraire(O_list, lg_O_list);
C_list[lg_C_list]=courant; 
lg_C_list++;     
developer(arrive, courant, O_list, lg_O_list, W);
}while( C_list[lg_C_list-1].h > 4000 && lg_C_list < 1000); 

//cout << "lg o list = " << lg_O_list << "\n";
//cout << "lg c list = " << lg_C_list << "\n";

chemin = C_list[lg_C_list-1];

cherche(chemin, C_list, lg_C_list-1, W);

lissage(C_list, W);
}
/**************************** outils A start **********************************/
void affiche(noeud n)
{
cout << "f =" << n.f <<"\n";
cout << "g =" << n.g <<"\n"; 
cout << "h =" << n.h <<"\n"; 
cout << "ref ="  <<"\n";
n.ref.affiche();   
cout << "parent ="  <<"\n"; 
n.parent.affiche();   
}
/******************************************************************************/
void affiche_tas(noeud tas[1000], int taille)
{
int caca ;

 for(int i=1 ; i<taille ; i*=2)
 {     
  for(int j=0 ; j< i ; j++)
    {
     cout << tas[i+j].g  << "\t" ;              
    }
  cout<<"\n"; 
 }   
} 
/******************************************************************************/
void cherche(noeud chemin, noeud liste[1000], int longueur_liste, cellule W[100][100])
{
int x,y;

while( chemin.g >=1 )
     {
      x = (int)chemin.ref.get_x();   
      y = (int)chemin.ref.get_y(); 
      W[x][y].set_occupation(4);
      for(int i = longueur_liste ; i >= 0 ; i--) 
         { 
          if(liste[i].ref.get_x() == chemin.parent.get_x() && liste[i].ref.get_y() == chemin.parent.get_y())
          { chemin = liste[i]; longueur_liste = i-1; break;}
         }
     }
}   
   
void evaluer(noeud & candidat, noeud parent, points arrive)
{
int Dx,Dy;

candidat.g = parent.g + 2000 ;     

Dx = abs( (int)(candidat.ref.get_x()-arrive.get_x()) );
Dy = abs( (int)(candidat.ref.get_y()-arrive.get_y()) ); 
candidat.h = 2010*(Dx+Dy) + abs(Dx-Dy) ; 

candidat.f = candidat.g + candidat.h ;     
}  
/******************************************************************************/
void inserer(noeud O_list[1000], noeud candidat, int & taille)
{
     char you;
if( taille >=999 ) { cout << "la liste a explose";  exit(1); }
else{
     taille++ ;
     int i = taille;
     noeud tmp ;
     O_list[i] = candidat ;
     
     while( i>1 && O_list[i].f < O_list[(int)(i/2)].f )
          {
           tmp = O_list[(int)(i/2)];
           O_list[(int)(i/2)] = O_list[i];
           O_list[i] = tmp;                      
           i = (int)(i/2);       
          }
      } 
 
}
/******************************************************************************/
noeud extraire(noeud O_list[1000], int & taille)
{
noeud racine,tmp ;
int i = 1;

racine = O_list[1];
O_list[1] = O_list[taille];
taille -- ;

while( (i <= taille/2) && (O_list[i].f > O_list[2*i].f || O_list[i].f > O_list[2*i+1].f ) ) 
     {
      if ( (2*i+1 > taille) || O_list[2*i].f  <= O_list[2*i+1].f )
          {
           tmp = O_list[2*i];
           O_list[2*i] = O_list[i]; 
           O_list[i] = tmp ;
           i = 2*i ;                          
          }         
      else
          {
           tmp = O_list[2*i+1];
           O_list[2*i+1] = O_list[i]; 
           O_list[i] = tmp ;
           i = 2*i+1 ;              
          }        
     }
      
return(racine);     
} 
/******************************************************************************/ 
void developer(points arrive, noeud courant, noeud O_list[1000],int & taille, cellule W[100][100])
{
 int i = (int)courant.ref.get_x();
 int j = (int)courant.ref.get_y();
 noeud gauche, droite, haut, bas ;
 
 if( W[i-1][j].get_occupation()== 0 ) 
     {
      W[i-1][j].set_occupation(3);                             
      gauche.parent = courant.ref;
      gauche.ref.set_x(i-1);  gauche.ref.set_y(j);
      evaluer(gauche, courant, arrive);                             
      inserer(O_list,gauche , taille); }
      
 if( W[i+1][j].get_occupation()== 0 )
     {
      W[i+1][j].set_occupation(3);                            
      droite.parent = courant.ref;
      droite.ref.set_x(i+1);  droite.ref.set_y(j);
      evaluer(droite, courant, arrive) ;                                 
      inserer(O_list,droite , taille); }
      
 if( W[i][j-1].get_occupation()== 0 )
     {
      W[i][j-1].set_occupation(3);                            
      bas.parent = courant.ref;
      bas.ref.set_x(i);  bas.ref.set_y(j-1);
      evaluer(bas,  courant, arrive) ;                                 
      inserer(O_list,bas   , taille); }
      
 if( W[i][j+1].get_occupation()== 0 )
     {
      W[i][j+1].set_occupation(3);                            
      haut.parent = courant.ref;
      haut.ref.set_x(i);  haut.ref.set_y(j+1);
      evaluer(haut, courant, arrive) ;                                 
      inserer(O_list, haut , taille); }   
      
}
void lissage(noeud C_list[1000], cellule W[100][100])
{
int x,y ;     

for(int i=0 ; i< 100; i++ )
   {
    x = (int)C_list[i].ref.get_x();
    y = (int)C_list[i].ref.get_y();

    if( (W[x-1][y].get_occupation()==4 && W[x][y+1].get_occupation()==4)||
        (W[x-1][y].get_occupation()==4 && W[x][y-1].get_occupation()==4)||
        (W[x+1][y].get_occupation()==4 && W[x][y+1].get_occupation()==4)||
        (W[x+1][y].get_occupation()==4 && W[x][y-1].get_occupation()==4) ) 
  
      { W[x][y].set_occupation(3); }
   } 
   
}
/******************************************************************************/