Wilos Is a cLever process Orchestration Software - http://wilos.berlios.de
Copyright (C) 2006-2007 Paul Sabatier University, IUP ISI (Toulouse, France) <aubry@irit.fr>

This program is free software; you can redistribute it and/or modify it under the terms of the GNU
General Public License as published by the Free Software Foundation; either version 2 of the License,
or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program; if not,
write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA. 



merge_wilos.xml :
-----------------

Pour rassembler les sources des 3 ss projets utiliser les taches update_sources, update_branches (tr�s long car importe
toutes les libraires), merge_all. Cette derni�re "mergera" preparera les sources du projet wilos pour la construction 
d'archive web (build_war.xml).
La construction du WAR, abouti � une archive dans le repertoire /src_wilos/dist.


Note sur AntSVN :
-----------------

AntSVN est une dll pour windows qui permet d'utiliser les fonctions de subversion sans installer SVN. Sur d'autres
OS, la solution reste d'installer SVN dans l'environnement.