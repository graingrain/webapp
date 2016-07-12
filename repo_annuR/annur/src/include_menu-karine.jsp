<% 
System.out.println("--------------- debut include menu -----------------:");
System.out.println("debut menu:"   + userinfo.getUser() + " --- profil:  " + userinfo.getProfil());
System.out.println("--------------------------------------------------------------------------------------" );
%>
<script language="JavaScript">
function control1() {
if (document.form1.recherche.value == "." ) {     return true ;  }
if (document.form1.recherche.value.length < 3) {
   alert("3 lettres minimum ...")
   document.form1.recherche.focus()
   return false
}
}
function control2() {
if (document.form2.recherche.value == "." ) {     return true ;  }
if (document.form2.recherche.value.length < 3) {
   alert("3 lettres minimum ...")
   document.form2.recherche.focus()
   return false
}
}
</script>
<%  { %> 
<div id="titre"><a href="http://ac-reunion.fr"><img   src="http://www.ac-reunion.fr/fileadmin/ANNEXES-ACADEMIQUES/CHARTE-GRAPHIQUE/logos-academie/logovert-e-lareunion.png"></a><h1 class="menu-organigramme">Annuaire du rectorat de 
    La R&Eacute;union</h1>
	 
	 <p class="date-nom">
	 <script language="JavaScript">
                  <!-- 
                  var monthNames = new Array( "janvier","fevrier","mars","avril","mai","juin","juillet","août","septembre","octobre","novembre","décembre");
                  var dayNames = new Array( "Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi");
                  var now= new Date();
                  thisYear = now.getYear();
                  thisHour= now.getHours();
                  if(thisHour<=9) { thisHour="0"+thisHour;    }
                  thisMinute= now.getMinutes();
                  if(thisMinute<=9) { thisMinute="0"+thisMinute;  }
                  if(thisYear < 1900) {thisYear += 1900}; // corrections if Y2K display problem
                  document.write("&nbsp;"+dayNames[now.getDay()]+ " " +now.getDate() + " " + monthNames[now.getMonth()] + " " + thisYear+"  - "+thisHour+"h"+thisMinute);
                  // -->
                  </script>
&nbsp;&brvbar;&nbsp;<span><% if(userinfo.getUser()!=null) {%><%=userinfo.getUser()%><% } %></span> <a href="javascript:history.back()">Retour</a></p>
</div>

<div id="formulaire">
<form class="menu" name="form1" method="post" action="Dispatcher?operation=Recherche" onsubmit='return control1()'>
                <input name="recherche"   type="text"     size="10">   
                <input name="table"       type="hidden"   value="liste">
				<input name="type"        type="hidden"   value=" "> 
                <input name="code"        type="hidden"   value="-1"> 
                <input type="submit" name="rechercheagt"  value="Recherche"  >
<span class="vert">Nom/mission des agents, 3 lettres minimum, ou un point pour tous les agents</span>
</form>
<% } %> 

<form class="menu" name="form2" method="post"  action="Dispatcher?operation=RechercheDiv" onsubmit='return control2()' >

                <input name="recherche"   type="text"     size="10">   
                <input name="table" type="hidden"   value="liste">
				<input name="type"  type="hidden"   value=" "> 
                <input name="code"  type="hidden"   value="-1"> 
                <input type="submit" name="recherchediv" value="Recherche Division"  >
                <span class="vert">Nom/missions des divisions ou des services, 3 lettres minimum, ou un point pour toutes les divisions</span>
                  
</form> 	  
</div> 	  
	   
<div id="menu-annuaire">
<% if((userinfo.getProfil()<= 0))  { %>      |<a href="Dispatcher?operation=login" > Identification</a> 
<% } else { %>                               |<a href="Dispatcher?operation=logout" > D&eacute;connexion</a> 
<% } %>
<% if((userinfo.getProfil()> 1)&&(userinfo.getMenu_admin()== 2))  { %> 
|<a href="Dispatcher?operation=menu_admin" > Menu administrateur</a>    
<% } %>
<% if((userinfo.getProfil()> 1)&&(userinfo.getMenu_admin()== 1))  { %> 
|<a href="Dispatcher?operation=menu_normal" > Menu standard</a>  
<% } %>
 

|<a href="Dispatcher?operation=listediv" > Liste des divisions</a>

<% if((userinfo.getVoir_mission()==1)) { %>  
|<a href="Dispatcher?operation=voirmission&table=annuaire" > Cacher  les missions</a>    
<% } else { %>                               
|<a href="Dispatcher?operation=voirmission&table=annuaire" > Missions des divisions</a>            
<% } %>              
<% if((userinfo.getProfil()>= 6) && userinfo.getMenu_admin()== 1) { %> |<a href="Dispatcher?operation=partage&table=listepartage" > Partage Scribe </a>   <% } %>                  
<% if((userinfo.getProfil() >= 0)                                ) { %> |<a href="Dispatcher?operation=trombi&table=trombi&recherche=." > Trombinoscope </a>   <% } %>                  

|<a href="organigramme-ac-reunion.jsp" > Organigramme</a> 

<% if((userinfo.getProfil()>= 6) && userinfo.getMenu_admin()== 1) { %>
| <a href="Dispatcher?operation=agent&table=agentkarine&codepl=-1&codedv=-1&codesv=-1&codebu=-1&codeag=-1" >+ agent</a> 
|<a href="Dispatcher?operation=bureau&codedv=-1&codesv=-1&codebu=-1" > + bureau</a>                               
|<a href="Dispatcher?operation=service&codedv=-1&codesv=-1" > + service</a>                                       
|<a href="Dispatcher?operation=division&codedv=-1" > + division</a>     
|<a href="Dispatcher?operation=sms&table=sms"   > Sms</a>                                           
|<a href="Dispatcher?operation=partage&codedv=-1&codepa=-1&table=partage" > + partage</a>                                       
|<a href="Dispatcher?operation=libelle&table=libelle&code=0&type=division" > + Libell&eacute; chef</a>   
|<a href="Dispatcher?operation=synchroallldap"  > Synchro LDAP</a>
|<a href="Dispatcher?operation=sunscribe&table=sunscribe"  > Sun%Scribe</a>
|<a href="Dispatcher?operation=allagents&table=listesansaff"  > Agents Sans Affectation</a>                
<% } %> 
  
<% if((userinfo.getProfil()>= 5  ) && userinfo.getMenu_admin()== 1) { %> 
|<a href="Dispatcher?operation=listemission"  target="_blank" > Liste des missions</a>| 
|<a href="Dispatcher?operation=ldapasa&table=asa"  > Clé OTP</a> 
|<a href="Dispatcher?operation=chorus&table=chorus&code=0"  > Clé Chorus</a>
<% } %> 
	 
<a style="float:right; color:red; font-size:0.8em; padding-top:2px" href="https://www.ac-reunion.fr/fileadmin/ANNEXES-ACADEMIQUES/01-SERVICES-ACADEMIQUES/service-communication/03-rectorat/organigramme/organigramme-rectorat-de-la-reunion.pdf"> T&eacute;l&eacute;charger l'organigramme de l'acad&eacute;mie de la R&eacute;union en PDF </a>

</div>
 