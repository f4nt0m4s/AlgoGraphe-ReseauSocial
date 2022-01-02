#!/bin/sh

if [ javac @compile.list -encoding utf8 ]
then
   echo "Compilation réussi !"
   echo "Execution du programme..."
   cd class
   java fr.algographe.Controleur
else
   echo "Echec de la compilation"
fi
echo "Fin de l'installation."