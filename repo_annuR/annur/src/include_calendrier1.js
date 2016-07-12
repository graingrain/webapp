  
var duree_jour = 24 * 60 * 60 * 1000;
 
var calendrier = new Calendrier();
 
function Calendrier() {
 	this.date_jour = dateAMidi();
 	this.feries = null;
 }
  
Calendrier.prototype.afficher = function() {
 	// affiche le mois concernant la date demand�e (en argument, facultative)
	//alert('proto6');
 	this.joursFeries();
 	html = "<table id='calendrier' cellspacing='0' cellpadding='0' border='1' summary='calendrier'>";
    html += "<tr><td>";
 	html += "<table id='calendrier-haut' cellspacing='0' cellpadding='0' border='0' summary='mois et ann�e du calendrier'>";
 	html += this.afficherEntete();
 	html += "</table>";
 	html += "</td></tr>";
 	html += "<tr><td>";
 	html += "<table id='calendrier-jours' cellspacing='0' cellpadding='0' border='0' summary='jour du mois'>";
 	html += this.afficherJoursSemaine();
 	html += this.afficherJoursMois();
 	html += "</table>";
 	html += "</td></tr>";
 	html += "</table>";
 	document.write(html);
 }
 
Calendrier.prototype.joursFeries = function() {
 	// indique les jours f�ri�s en fonction de l'ann�e du calendrier
    var feries = this.date_jour.joursFeriesMobiles();
 	this.feries = "|" + feries.join("|") + "|";
 }

Calendrier.prototype.afficherEntete = function() {
 	// affiche le mois (en lettres) et l'ann�e avec des fl�ches pour les incr�menter ou les d�cr�menter
    // affichage de 2 fl�ches prec 
    var html = "<tr><td class='calendrier-fleche'>";
 	html += "<a href='javascript:calendrier.anneePrecedente()' title='ann�e pr�c�dente'>";
 	html += "<img src=images/minus.gif    border=0></a>";
 	html += "</td>";
 	html += "<td class='calendrier-fleche'>";
 	html += "<a href='javascript:calendrier.moisPrecedent()' title='mois pr�c�dent'>";
 	html += "<img src=images/minus.gif    border=0></a>";
 	// affichage du mois avec son id qui permettra de modifier le contenu du <td>
 	html += "<td id='calendrier-mois-an'>" + this.date_jour.moisLitteral() + " " + this.date_jour.getFullYear() + "</td>";
 	// affichage de 2 fl�ches suiv 
 	html += "</td>";
 	html += "<td class='calendrier-fleche'>";
 	html += "<a href='javascript:calendrier.moisSuivant()' title='mois suivant'>";
 	html += "<img src=images/plus.gif    border=0></a>";
 	html += "</td>";
 	html += "<td class='calendrier-fleche'>";
 	html += "<a href='javascript:calendrier.anneeSuivante()' title='ann�e suivante'>";
 	html += "<img src=images/plus.gif    border=0></a>";
 	html += "</td></tr>";
 	return html;
 }
 
Calendrier.prototype.afficherJoursSemaine = function() {
    // affiche les jours de la semaine sur une ligne
 	var jours_sem = new Array("lun", "mar", "mer", "jeu", "ven", "sam", "dim");
 	var html = "<tr>";
 	for (var i=0;i<jours_sem.length;i++) {
 		if (i < 5) html += "<td class='calendrier-sem'>" + jours_sem[i] + "</td>";
 		else html += "<td class='calendrier-fin-sem'>" + jours_sem[i] + "</td>";
 	}
 	html += "</tr>";
 	return html;
 }
 
Calendrier.prototype.afficherJoursMois = function() {
       // affiche les jours du mois en fonction du mois et de l'ann�e en cours
       // limites indique le d�but et la fin des jours visibles dans le calendrier
       // de fa�on � ce que le calendrier contienne le 1er du mois sur la 1�re ligne
  var limites = this.limitesMoisCalendrier();
  var jour = limites[0];
  var auj = dateAMidi();
  var col = 0;
  var ligne = 0;
  var html = "<tr>";
  while (jour <= limites[1]) {
      if (col == 7) {
          html += "</tr><tr>";
          col = 0;
          ligne++;
      }
     
      if (jour.memeJour(auj)) html += "<td   class='calendrier-jour-auj";
      else if (jour.dansLeMois(this.date_jour)) {
          if (col < 5) html += "<td class='calendrier-jour";
          else html += "<td class='calendrier-jour-we";
      }
      else html += "<td class='calendrier-jour-horsmois";
      if (this.estFerie(jour)) html += " calendrier-jour-ferie";
      html += "' onclick='calendrier.selectJour(" + ligne + ", " + col + ")'>" + jour.getDate() + "</td>";
     
      jour.jourSuivant();
      col++;
  }
  html += "</tr>";
  ligne++;
  while (ligne < 6) {    
      html += "<tr  >";
      for (col=0;col<7;col++) html += "<td class='' onclick='calendrier.selectJour(" + ligne + ", " + col + ")'>&nbsp;</td>";            
      html += "</tr>";
      ligne++;
  }              
  return html;
}
 
Calendrier.prototype.selectJour = function(ligne, col)
{  var jour = document.getElementById("calendrier-jours").lastChild.childNodes[ligne+1].childNodes[col].firstChild.nodeValue;
   if(jour==0) return;
   
   var mois = this.date_jour.getMonth();    
   var annee = this.date_jour.getFullYear();
   var vmois=mois+101;
   var xmois=""+vmois;
   var xannee=""+annee;
   var xjour=jour;
   if(jour.length==1) xjour="0"+jour;
    
   //alert("m"+mois+"M"+vmois+"j"+jour+"J"+xjour+"L"+jour.length);
   var date_a_afficher = new Date(annee, mois, jour);
      document.getElementById("moncalque").style.visibility="hidden"; 
     document.forms[0].ddtdatepre.value=""+xannee.substring(2,4)+"." +xmois.substring(1,4)+"."+xjour;
     Cacher();
   // si on est hors mois (classe calendrier-jour-horsmois ou calendrier-jour-joursferies)
   if(document.getElementById("calendrier-jours").lastChild.childNodes[ligne+1].childNodes[col].attributes["class`"].nodeValue.indexOf("calendrier-jour-horsmois") > -1)
   {
       // on n'est pas sur le m�me mois, le jour s�lectionn� est le jour suivant ou pr�c�dent
       if(ligne==0)
       {
           // premi�re ligne, on est sur le mois pr�c�dent
           date_a_afficher.moisPrecedent();
       }
       else
       {
           // autres lignes (5 ou 6), on est sur le mois pr�c�dent
           date_a_afficher.moisSuivant();
       }
   };
   
   mois=date_a_afficher.getMonth()+1;
   annee=date_a_afficher.getFullYear();
    
   jour = ((jour<=9) ? "0" : "") + jour;
   mois = ((mois <= 9) ? "0" : "") + mois;
    var vmois=mois+101;
   var xmois=""+vmois;
   var xannee=""+annee;
   if(jour.length==1) xjour="0"+jour;
   //alert("m"+mois+"M"+vmois+"j"+jour+"J"+xjour);
    document.getElementById("moncalque").style.visibility="hidden"; 
    document.forms[0].ddtdatepre.value=""+xannee.substring(2,4)+"." +xmois.substring(1,4)+"."+xjour ;
      
    
   Cacher();
}

Calendrier.prototype.limitesMoisCalendrier = function() {
 	// d�termine les limites du calendrier relatif au mois de la date en cours, de telle fa�on � ce que le 1er du mois
 	// soit sur la 1�re ligne, que le dernier du mois sur la derni�re ligne, 
 	// et que le d�but du calendrier commence un lundi et la fin finisse un dimanche
 	// on cherche la date du d�but du calendrier
 	var jour = new Date(this.date_jour.getTime());
 	jour.debutMois(); // date du 1er du mois
 	var jour_semaine = jour.getDay();
 	jour_semaine = jour_semaine ? jour_semaine - 1 : 6;
 	var debut_cal = new Date();
 	debut_cal.setTime(jour.getTime() - jour_semaine * duree_jour);
 	// puis la date de la fin du calendrier
 	jour.finMois(); 
 	jour_semaine = jour.getDay();
 	jour_semaine = jour_semaine ? jour_semaine - 1 : 6;
 	var fin_cal = new Date();
 	fin_cal.setTime(jour.getTime() + (6 - jour_semaine)  * duree_jour);
 
	var limites = new Array(debut_cal, fin_cal);
 	return limites;
 }
 
Calendrier.prototype.moisSuivant = function() {
 	// fait passer le calendrier au mois suivant
 	// il faut modifier le jour courant du calendrier,
 	// puis modifier le mois et �ventuellement l'ann�e en haut du calendrier
 	// et les jours, tout �a via la structure du DOM
 	this.date_jour.moisSuivant();
 	this.joursFeries();
 	this.majEntete();
 	this.majJoursMois();
 }



Calendrier.prototype.moisPrecedent = function() {

	// fait passer le calendrier au mois suivant

	// il faut modifier le jour courant du calendrier,

	// puis modifier le mois et �ventuellement l'ann�e en haut du calendrier

	// et les jours, tout �a via la structure du DOM

	this.date_jour.moisPrecedent();

	this.joursFeries();

	this.majEntete();

	this.majJoursMois();

}



Calendrier.prototype.anneeSuivante = function() {

	// fait passer le calendrier au mois suivant

	// il faut modifier le jour courant du calendrier,

	// puis modifier le mois et �ventuellement l'ann�e en haut du calendrier

	// et les jours, tout �a via la structure du DOM

	this.date_jour.anneeSuivante();

	this.joursFeries();

	this.majEntete();

	this.majJoursMois();

}



Calendrier.prototype.anneePrecedente = function() {

	// fait passer le calendrier au mois suivant

	// il faut modifier le jour courant du calendrier,

	// puis modifier le mois et �ventuellement l'ann�e en haut du calendrier

	// et les jours, tout �a via la structure du DOM

	this.date_jour.anneePrecedente();

	this.majEntete();

	this.majJoursMois();

}



Calendrier.prototype.majEntete = function() {

	// met � jour le mois et l'ann�e en-t�te du calendrier

	document.getElementById("calendrier-mois-an").lastChild.nodeValue = this.date_jour.moisLitteral() + " " + this.date_jour.getFullYear();

}



Calendrier.prototype.majJoursMois = function() {

	// met � jour les jours du mois

	// racine est le noeud TBODY dont les lignes du tableau sont les childNodes

	var racine = document.getElementById("calendrier-jours").lastChild;

//	alert("fils 1 de racine = " + racine.childNodes[1].nodeName);

	var limites = this.limitesMoisCalendrier();

// alert("limites du calendrier = " + limites[0].afficher() + ", " + limites[1].afficher() + " date du calendrier = " + this.date_jour.afficher());

	var jour = limites[0];

	var auj = dateAMidi();

	var iligne = 1;

	var ligne = racine.childNodes[iligne];

	var cell = ligne.firstChild;

	var col = 0;

	do {

		if (col == 7) {

			ligne = racine.childNodes[++iligne];

			cell = ligne.firstChild;

			col = 0;

		}

		else cell = ligne.childNodes[col];

		if (jour.memeJour(auj)) cell.attributes["class"].nodeValue = "calendrier-jour-auj";

		else if (jour.dansLeMois(this.date_jour)) { 

			if (col < 5) cell.attributes["class"].nodeValue = "calendrier-jour";

			else cell.attributes["class"].nodeValue = "calendrier-jour-we";

		}

		else cell.attributes["class"].nodeValue = "calendrier-jour-horsmois";

		if (this.estFerie(jour)) cell.attributes["class"].nodeValue += " calendrier-jour-ferie";

		cell.firstChild.nodeValue = jour.getDate();

		jour.jourSuivant();

// alert("jour en cours = " + jour.afficher() + " diff = " + (limites[1].getTime() - jour.getTime()) / duree_jour);

		col++;

	} while ((limites[1].getTime() - jour.getTime()) >= 0);

	// cas o� les jours s'affichent sur moins de 6 lignes

	while (iligne < 6) {

		ligne = racine.childNodes[++iligne];

		for (col=0;col<7;col++) {

			cell = ligne.childNodes[col];

			cell.attributes["class"].nodeValue = "";

			cell.firstChild.nodeValue = " ";

		}

	}

}



Calendrier.prototype.estFerie = function(jour) {

	// d�termine si un jour est f�ri� (rajouter la date de P�ques plus tard)

	var ch_date = rajoute0(jour.getDate()) + "/" + rajoute0(jour.getMonth()+1);

 	if (this.feries.indexOf("|" + ch_date + "|") == -1) return false;

 	else return true;

}





// FONCTIONS UTILITAIRES





Date.prototype.dansLeMois = function(date_ref) {

	// indique si la date en cours et date_ref sont du m�me mois et de la m�me ann�e

	if ((this.getMonth() == date_ref.getMonth()) && (this.getFullYear() == date_ref.getFullYear()))

		return true;

	else return false;

}



Date.prototype.jourSuivant = function() {

	// incr�mente le jour de la date en fonction du nombre de jours en argument (si pas d'argument, l'incr�ment = 1)

	// p. ex. var jour = new Date(2004, 0, 1) (soit 01/01/2004), jour.jourSuivant() donnera le 02/01/2004

	// et jour.jourSuivant(31) donnera le 01/02/2004

	if (arguments.length) var increment = parseInt(arguments[0]);

	else var increment = 1;

	this.setDate(this.getDate() + increment);

}



Date.prototype.moisSuivant = function() {

	// passe le jour au mois suivant

	if (this.getMonth() < 11) this.setMonth(this.getMonth() + 1);

	else {

   		this.setMonth(0);

   		this.setFullYear(this.getFullYear() + 1);

 	}

}



Date.prototype.moisPrecedent = function() {

	// passe le jour au mois pr�c�dent

	if (this.getMonth()) this.setMonth(this.getMonth() - 1);

	else {

   		this.setMonth(11);

   		this.setFullYear(this.getFullYear() - 1);

 	}

}



Date.prototype.anneeSuivante = function() {

	// passe le jour � l'ann�e suivante

	this.setFullYear(this.getFullYear() + 1);

}



Date.prototype.anneePrecedente = function() {

	// passe le jour � l'ann�e pr�c�dente

	this.setFullYear(this.getFullYear() - 1);

}



Date.prototype.debutMois = function() {

	// met la date en cours au premier du mois

	this.setTime(this.getTime() - (this.getDate() - 1) * duree_jour); 

}



Date.prototype.finMois = function() {

	// met la date en cours � la fin du mois

	this.moisSuivant();

 	this.setDate(1);

 	this.setTime(this.getTime() - duree_jour);

}



Date.prototype.memeJour = function(une_date) {

	// si la date courante est le m�me jour que une_date, retourne "true", sinon retourne "false"

	var diff = Math.abs(this.getTime() - une_date.getTime());

	if (diff < 60*60*1000) return true;

	else return false;

}



Date.prototype.moisLitteral = function() {

	var nom_mois = new Array("janvier", "f�vrier", "mars", "avril", "mai", "juin", "juillet", "ao�t", "septembre", "octobre", "novembre", "d�cembre");

	return nom_mois[this.getMonth()];

}



Date.prototype.joursFeriesMobiles = function() {

	// ajoute aux jours f�ri�s fixes les jours f�ri�s mobiles : lundi de P�ques, Ascension et lundi de Pentec�te

	var jours_feries = new Array("01/01", "01/05", "14/07", "15/08", "01/11", "11/11", "25/12");

	var jour = paques(this.getFullYear());

	// lundi de P�ques

	jour.jourSuivant();

	jours_feries.splice(1, 0, jour.afficherJourMois());

	// ascension

	jour.jourSuivant(38);

	if (jour.getMonth() > 3) jours_feries.splice(3, 0, jour.afficherJourMois());

	else jours_feries.splice(2, 0, jour.afficherJourMois());

	// lundi de Pentec�te

	jour.jourSuivant(11);

	jours_feries.splice(4, 0, jour.afficherJourMois());

	return jours_feries;

}

 
Date.prototype.afficherJourMois = function() {

	return rajoute0(this.getDate()) + "/" + rajoute0(eval(this.getMonth() + 1));

}



function dateAMidi() {

	// cr�e une date � midi (12h 0mn 0sec)

	if (arguments.length) ladate = arguments[0];

	else ladate = new Date();

	ladate.setHours(12);

	ladate.setMinutes(0);

	ladate.setSeconds(0);

	return ladate;

}

	

function rajoute0(valeur) {

	return (valeur < 10) ? "0" + valeur : valeur;

}



function paques(annee) {

    // retourne une date Javascript, qui est la date de P�ques en fonction de l'ann�e

    annee = parseInt(annee);

    var date_paques = null;

    var b = annee - 1900;

    var c = annee % 19;

    var d = Math.floor((7*c+1)/19);

    var e = (11*c+4-d) % 29;

    var f = Math.floor(b/4);

    var g = (b+f+31-e) % 7;

    var avril = 25-e-g;

    if (avril > 0) date_paques = new Date(annee, 3, avril);

    else date_paques = new Date(annee, 2, avril + 31);

    return date_paques;

}