INSERT INTO projet.utilisateurs(pseudo,  mdp, nom,  prenom,   departement   ,date_naissance,  adresse,  telephone, email,  nationalite,  titre,  sexe,  nbr_annee_ens_sup,  titulaire_compte, num_compte,  nom_banque,  code_bic,  date_inscription, type_user , num_version ) 
VALUES('gigi','$2a$10$iTTHCyu29cK9kXmUnw1AjOhlpHcUFmwW3w0ENxudRUygn4UwIRFjG','Lehmann','Brigitte','BIN','1970-05-12','Rue Jacques Yakoub 47','/','brigitte.lehmann@ipl.be','BE','Mme','F',10,'','','','',NOW(),'PROF',0);

INSERT INTO projet.utilisateurs(pseudo,  mdp, nom,  prenom,   departement   ,date_naissance,  adresse,  telephone, email,  nationalite,  titre,  sexe,  nbr_annee_ens_sup,  titulaire_compte, num_compte,  nom_banque,  code_bic,  date_inscription, type_user , num_version ) 
VALUES('dodo','$2a$10$iTTHCyu29cK9kXmUnw1AjOhlpHcUFmwW3w0ENxudRUygn4UwIRFjG','Grolaux','Donatien','BIN','1970-05-12','Rue Jacques Yakoub 47','/','donatien.grolaux@ipl.be','BE','M','M',10,'','','','',NOW(),'PROF',0);


INSERT INTO projet.utilisateurs(pseudo,  mdp, nom,  prenom,   departement   ,date_naissance,  adresse,  telephone, email,  nationalite,  titre,  sexe,  nbr_annee_ens_sup,  titulaire_compte, num_compte,  nom_banque,  code_bic,  date_inscription, type_user , num_version ) 
VALUES('pierrot','$2a$10$iTTHCyu29cK9kXmUnw1AjOhlpHcUFmwW3w0ENxudRUygn4UwIRFjG','Kiroule','Pierre','BDI','1970-05-12','Rue Jacques Yakoub 47','/','kiroule.pierre@ipl.be','BE','M','M',10,'','','','',NOW(),'ETUD',0);

INSERT INTO projet.utilisateurs(pseudo,  mdp, nom,  prenom,   departement   ,date_naissance,  adresse,  telephone, email,  nationalite,  titre,  sexe,  nbr_annee_ens_sup,  titulaire_compte, num_compte,  nom_banque,  code_bic,  date_inscription, type_user , num_version ) 
VALUES('nana','$2a$10$iTTHCyu29cK9kXmUnw1AjOhlpHcUFmwW3w0ENxudRUygn4UwIRFjG','Pamousse','Namasse','BIN','1970-05-12','Rue Jacques Yakoub 47','/','pamousse.namasse@ipl.be','BE','M','M',10,'','','','',NOW(),'ETUD',0);

INSERT INTO projet.utilisateurs(pseudo,  mdp, nom,  prenom,   departement   ,date_naissance,  adresse,  telephone, email,  nationalite,  titre,  sexe,  nbr_annee_ens_sup,  titulaire_compte, num_compte,  nom_banque,  code_bic,  date_inscription, type_user , num_version ) 
VALUES('tonthe','$2a$10$iTTHCyu29cK9kXmUnw1AjOhlpHcUFmwW3w0ENxudRUygn4UwIRFjG','Tatilotetatou','Tonthe','BDI','1970-05-12','Rue Jacques Yakoub 47','/','tatilotetatou.tonthe@ipl.be','BE','M','M',10,'Tonthe Tatilotetatou','BE73000000006060','BPOTBEB1','/',NOW(),'ETUD',0);




INSERT INTO projet.partenaires (nom_affaire, nom_legal, nombre_employes, adresse, createur, code_pays, region, code_postal, ville, email, site_web, telephone, stage, agree, nom_complet, type_entreprise, num_version , supprime) 
VALUES('Dublin Institute of Technology','Dublin Institute of Technology', 2000, 'DIT International Office, 143- 149 Rathmines Road, Rathmines',  1, 'IE','/','Dublin 6', 'Dublin','peter.dalton@dit.ie' , 'http://www.dit.ie', '/', 'SMS', TRUE,'Dublin Institute of Technology', 'TGE', 0 , FALSE);

INSERT INTO projet.partenaires (nom_affaire, nom_legal, nombre_employes, adresse, createur, code_pays, region, code_postal, ville, email, site_web, telephone, stage, agree, nom_complet, type_entreprise, num_version , supprime) 
VALUES('UNIVERSITY COLLEGE LEUVEN LIMBURG','UNIVERSITY COLLEGE LEUVEN LIMBURG', 0, '/',  1, 'BE','Vlaams Brabant','3000', 'Leuven','Griet.tservranckx@ucll.be' , 'http://www.ucll.be/', '32 16 375 245', 'SMS', TRUE,'UNIVERSITY COLLEGE LEUVEN LIMBURG', 'TGE', 0 , FALSE);

INSERT INTO projet.partenaires (nom_affaire, nom_legal, nombre_employes, adresse, createur, code_pays, region, code_postal, ville, email, site_web, telephone, stage, agree, nom_complet, type_entreprise, num_version , supprime) 
VALUES('Université du Sanglier','Université du Sanglier', 1984, 'Rue du Faing, 10',  3, 'LU','Luxembourg','6810', 'Jamoigne','jamoigne@lux.be' , '/', '/', 'SMS', FALSE,'Université du Sanglier', 'TGE', 0 , FALSE);



INSERT INTO projet.partenairespardepartement (partenaire, code_departement, num_version)
VALUES (1,'BIN',0);

INSERT INTO projet.partenairespardepartement (partenaire, code_departement, num_version)
VALUES (2,'BIN',0);
INSERT INTO projet.partenairespardepartement (partenaire, code_departement, num_version)
VALUES (2,'BDI',0);

INSERT INTO projet.partenairespardepartement (partenaire, code_departement, num_version)
VALUES (3,'BIN',0);



INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(1,'Dublin','SMS',1,1,4,'IE',FALSE,'ERASMUS',NOW(),0,'2016-2017');

INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(2,'Leuven','SMS',1,2,4,'BE',FALSE,'ERABEL',NOW(),0,'2016-2017');

INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(3,'','SMP',1,NULL,4,'GB',FALSE,'ERASMUS',NOW(),0,'2016-2017');


INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(1,'Leuven','SMS',1,2,3,'BE',FALSE,'ERABEL',NOW(),0,'2016-2017');

INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(1,'','SMP',1,NULL,3,NULL,FALSE,'ERABEL',NOW(),0,'2016-2017');

INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(2,'','SMP',1,NULL,3,'CH',FALSE,'ERASMUS',NOW(),0,'2016-2017');


INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(1,'Leuven','SMS',1,2,5,'BE',FALSE,'ERABEL',NOW(),0,'2016-2017');

INSERT INTO projet.demandes(preference, ville,  stage,  num_quadri,  num_partenaire,  num_etudiant,  code_pays, validee,  programme,  date_introduction, num_version, annee_academique) 
VALUES(2,'','SMP',1,NULL,5,NULL,FALSE,'ERASMUS',NOW(),0,'2016-2017');




-- salt  =$2a$10$iTTHCyu29cK9kXmUnw1AjO
-- hash = $2a$10$iTTHCyu29cK9kXmUnw1AjOhlpHcUFmwW3w0ENxudRUygn4UwIRFjG