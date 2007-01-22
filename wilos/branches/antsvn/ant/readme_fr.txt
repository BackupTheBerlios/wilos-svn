merge_wilos.xml :
-----------------

Pour rassembler les sources des 3 ss projets utiliser les taches update_sources, update_branches (trés long car importe
toutes les libraires), merge_all. Cette dernière "mergera" preparera les sources du projet wilos pour la construction 
d'archive web (build_war.xml).
La construction du WAR, abouti à une archive dans le repertoire /src_wilos/dist.


Note sur AntSVN :
-----------------

AntSVN est une dll pour windows qui permet d'utiliser les fonctions de subversion sans installer SVN. Sur d'autres
OS, la solution reste d'installer SVN dans l'environnement.