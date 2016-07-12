<%@ page import="java.util.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="java.sql.*"  %>
<%@ page import="javabeans.*" %>
<%@ page import="java.io.*" %>
<% 
ServletContext sc = getServletContext();
UserInfo userinfo = (UserInfo)session.getValue("currentUser");
String table="annuaire";
System.out.println("debut organigramme jsp :" + table +".jsp"  );
System.out.println("debut jsp :" + table +".jsp" +" profil="+userinfo.getProfil());
System.out.println("--------------------------------------" );
ArrayList alldivision       = (ArrayList)session.getValue("alldivision");
%>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6 oldie"> <![endif]-->
<!--[if IE 7]>    <html class="ie7 oldie"> <![endif]-->
<!--[if IE 8]>    <html class="ie8 oldie"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="">
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>organigramme.jsp</title>
<link rel="shortcut icon" type="images/x-icon" href="favicon-annuaire.png" />
<link href="boiler.plate.css" rel="stylesheet" type="text/css">
<link href="organigramme.ac-reunion.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="menu.organigramme.css" type="text/css" media="screen" />
<!--[if lt IE 9]>
<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<script src="respond.min.js"></script>
 
</head>
<body>
<%@ include file="include_menu-karine.jsp" %>
 
 
<div class="gridContainer clearfix">
  <div id="content">
    <div id="content-row1">
      <div id="recteur" class="row1-fond-couleur">
       <h1><a href="Dispatcher?operation=division&amp;codedv=76">Recteur de l'acad&Eacute;mie de La r&Eacute;union<br>
          Chancelier des universit&Eacute;s
        </a></h1>
        <h2><span class="nom">Thierry&nbsp;Terret</span></h2>
      </div>
      <div id="cabinet" class="row1-contour-couleur">
       <h1><a href="Dispatcher?operation=division&amp;codedv=66">cabinet</a></h1>
        <h2>Directeur&nbsp;:<span class="nom"> Jean-Luc&nbsp;NGuyen Phuoc</span></h2>
        <h2>Charg&eacute;e de communication&nbsp;:<span class="nom"> Amelie&nbsp;Lerat</span></h2>
      </div>
      <div id="mediatrice" class="row1-contour-gris">
      <h2>M&eacute;diatrice&nbsp;: Christiane&nbsp;Andr&eacute;</h2>
      </div>
    </div>
    <div id="content-row2"><div id="daasen" class="row2-fond-couleur">
       <h1><a href="Dispatcher?operation=division&amp;codedv=73">Directeur acad&Eacute;mique adjoint<br>
        des services de l'&Eacute;ducation nationale</a> </h1>
        <h2><span class="nom">Jacques&nbsp;Briand</span></h2>
        <p>&nbsp;</p>
        <p class="fonction">Adjoint</p>
        <p class="nom">Jack&nbsp;Corr&eacute;</p>
      </div>
      <div id="sg" class="row2-fond-couleur">
       <h1><a href="Dispatcher?operation=division&amp;codedv=65">Secr&Eacute;taire g&Eacute;n&Eacute;ral</a> </h1>
        <h2><span class="nom">Xavier&nbsp;Le&nbsp;Gall</span></h2>
        <p class="fonction">&nbsp;</p>
        <p class="fonction">Adjoints</p>
        <p class="nom">Yann&nbsp;Couedic</p>
        <p class="nom">Sylvie&nbsp;Thirard</p>
        <p class="nom">Didier&nbsp;Coll-Mournet      </p>
      </div>
    </div>
  
    <div id="content-row4"><div id="pedagogique" class="row4-fond-rouge">
       <h2 class="pole">P&eacute;dagogie
        </h2><div class="pole-content">
        <p><a href="Dispatcher?operation=agent&table=agent&codepl=53&codedv=72&codesv=0&codebu=0&codeag=1177">Doyen des IEN des &eacute;coles du 1er degr&eacute;&nbsp;: <span class="nom">Pascal Geslin</span></a></p>
        <p><a href="Dispatcher?operation=agent&table=agent&codepl=53&codedv=71&codesv=0&codebu=0&codeag=960">Doyen des IEN enseignement technique et g&eacute;n&eacute;ral&nbsp;: <span class="nom">Christian&nbsp;Heo-Hsien-Kai</span></a><span class="nom"></span></p>
        <p><a href="Dispatcher?operation=agent&table=agent&codepl=53&codedv=70&codesv=0&codebu=0&codeag=1526">Doyen des IA-IPR&nbsp;: <span class="nom">Daniel&nbsp;Herv&eacute;</span></a></p>
        <p><a href="Dispatcher?operation=agent&table=agent&codepl=53&codedv=70&codesv=0&codebu=0&codeag=771">IA-IPR Vie scolaire&nbsp;: <span class="nom">Marie-Claude&nbsp;Boyer-Roche / Ir&egrave;ne Ilef-Penhouet</span></a><span class="nom"></span></p>
        <p><a href="Dispatcher?operation=division&amp;codedv=75">Pr&eacute;-&eacute;l&eacute;mentaire : <span class="nom">Denis&nbsp;Ouin</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="Dispatcher?operation=division&amp;codedv=75">Vie scolaire des &eacute;l&egrave;ves (PVS) : <span class="nom">&eacute;ric&nbsp;Couleau</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="Dispatcher?operation=division&amp;codedv=75">Scolarisation des &eacute;l&egrave;ves handicap&eacute;s (ASH) : <span class="nom">Maximin&nbsp;Astourne</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="Dispatcher?operation=division&amp;codedv=75">Orientation (SAIO)&nbsp;: <span class="nom">Michel&nbsp;Georges-Skelly</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="Dispatcher?operation=division&amp;codedv=75">Apprentissage (SAIA)&nbsp;: <span class="nom">Solange&nbsp;Le&nbsp;Buffe</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Action culturelle (DAAC)&nbsp;: <span class="nom">Yannick&nbsp;Lepoan</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Num&eacute;rique et Tice (DAN)&nbsp;: <span class="nom">Fran&ccedil;ois&nbsp;Millet</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Enseignement technique (DAET)&nbsp;: <span class="nom">Thierry&nbsp;Delahaye</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Relations internationales (DAREIC)&nbsp;: <span class="nom">Marjorie&nbsp;Coste</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Formation continue tout au long de la vie (DAFCO)&nbsp;: <span class="nom">Marie-Claude&nbsp;Guyon</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Infrastructures (DAISU) : <span class="nom">Yves&nbsp;Bossard</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Formation des personnels d'encadrement (DAFPE)&nbsp;: <span class="nom">Maurice&nbsp;Berne</span></a><span class="nom"></span></p>
<p><a href="Dispatcher?operation=division&amp;codedv=75">Recherche-d&eacute;veloppement, Innovation et exp&eacute;rimentation (CARDIE)&nbsp;: <span class="nom">Pascal&nbsp;Chabernaud</span></a><span class="nom"></span>        </p>
        </div>
      </div>
            <div id="scolarite" class="row4-fond-corail">
        <h2 class="pole">Scolarit&eacute;-partenariats</h2>
        <p><span class="sg">Sylvie Thirard</span></p>
        <div class="pole-content">
        <p><a href="Dispatcher?operation=division&codedv=68">&eacute;l&egrave;ves et scolarit&eacute; (DES)&nbsp;: <span class="nom">Marie-Sabine&nbsp;Lauret</span></a><span class="nom"></span></p>
<p><a href="Dispatcher?operation=division&codedv=30">Examens et concours (DEC)&nbsp;: <span class="nom">Samir&nbsp;Dib</span></a><span class="nom"></span></p>
<p><a href="Dispatcher?operation=division&codedv=41">Structures et moyens (DSM)&nbsp;: <span class="nom">J&eacute;r&ocirc;me&nbsp;Cl&eacute;ment</span></a><span class="nom"></span>        </p>
        </div>
      </div>
      <div id="budget" class="row4-fond-prune">
        <h2 class="pole">Budget-modernisation</h2>
        <p><span class="sg">Didier Coll-Mournet</span></p>
        <div class="pole-content">
        <p><a href="Dispatcher?operation=division&codedv=15">Logistique (DL)&nbsp;: <span class="nom">Pascal&nbsp;Bordelais</span></a><span class="nom"></span> </p>
        <p><a href="Dispatcher?operation=division&codedv=22">Services informatiques (DSI)&nbsp;: <span class="nom">Jean-Louis&nbsp;Forestier</span></a></p>
        <p><a href="Dispatcher?operation=division&codedv=47">Finances et prestations (DFP)&nbsp;: <span class="nom">Val&eacute;rie Fruteau-De-Laclos</span></a><span class="nom"></span></p>
        </div>
      </div>
      <div id="ressources-humaines" class="row4-fond-marine">
        <h2 class="pole">Ressources humaines</h2>
        <p> <span class="sg">Yann&nbsp;Cou&eacute;dic</span></p>
        <div class="pole-content">
        <p><a href="Dispatcher?operation=division&codedv=36">Personnels de l'enseignement primaire (DPEP)&nbsp;: <span class="nom">Maryvonne&nbsp;Cl&eacute;ment</span></a><span class="nom"></span> </p>
          <p><a href="Dispatcher?operation=division&codedv=35">Personnels de l'enseignement 
            secondaire (DPES)&nbsp;: </a><a href="Dispatcher?operation=division&codedv=35"><span class="nom">Pierre-Olivier 
            Sempere </span></a><span class="nom"></span></p>
        <p><a href="Dispatcher?operation=division&codedv=38">Personnels administratifs, techniques et d'encadrement (DPATE)&nbsp;: <span class="nom">Nicolas&nbsp;Guillard</span></a><span class="nom"></span></p>
        <p><a href="Dispatcher?operation=division&codedv=40">Formation continue des personnels (DIFOR)&nbsp;: <span class="nom">Christian&nbsp;Le-Tiec</span></a><span class="nom"></span>        </p>
        </div>
      </div>
      <div id="sante-social" class="row4-fond-gris">
        <h2 class="pole">Sant&eacute; social</h2>
        <p>&nbsp;</p>
        <div class="pole-content">
        <p><a href="#">M&eacute;decine de pr&eacute;vention (MDP)&nbsp;: <span class="nom">Fr&eacute;d&eacute;ric Le Bot</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="#">Service infirmier : <span class="nom">Ana-Maria&nbsp;Ebro</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="Dispatcher?operation=division&codedv=64">Service m&eacute;dical en faveur des &eacute;l&egrave;ves (SPSFE)&nbsp;: <span class="nom">Jo&euml;l&nbsp;Paugam</span></a><span class="nom"></span></p>
        <p><a href="#">Service social des personnels (SSP)&nbsp;: <span class="nom">Amina&nbsp;Banian</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p><a href="#">Service social en faveur des &eacute;l&egrave;ves (SSFE)&nbsp;: <span class="nom">Fran&ccedil;oise&nbsp;Penent</span></a> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></p>
        <p>Hygi&egrave;ne et s&eacute;curit&eacute; (DAHS)&nbsp;: <span class="nom">Christian&nbsp;Ecolivet</span> <a href="Dispatcher?operation=division&amp;codedv=75" class="conseiller-technique">(CT*)</a></span>        </p>
        </div>
      </div>
      <p><span class="conseiller-technique">CT* = Conseiller tehnique du recteur</span></p></div>
      <div id="content-row5"><div id="instances" class="row1-contour-gris">
        <h2><span class="nom">Instances de consultation</span></h2>
        <p>Comit&eacute; acad&eacute;mique du pilotage de l'&eacute;ducation prioritaire</p>
<p>Comit&eacute; acad&eacute;mique de la maternelle</p>
<p>Comit&eacute; acad&eacute;mique de pilotage du num&eacute;rique</p>
<p>Conseil acad&eacute;mique des langues vivantes</p>
<p>Conseil acad&eacute;mique de l'&eacute;ducation artistique et culturelle</p>
<p>Conseil acad&eacute;mique de la formation</p>
<p>Conseil acad&eacute;mique des langues vivantes r&eacute;gionales</p></div></div>
  </div>
</div>
</body>
</html>
