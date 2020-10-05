# RMI

## Collaborateurs
Rémi Gosselin
Thomas Cenci

## Objectif
Création d'un répartiteur de charge basé sur la conception d'un TD déjà travaillé.
Des clients effectuent des lectures et des écritures sur des fichiers via des serveurs.
L’accès aux serveurs se fait via une machine
« aiguilleur » qui va répartir la charge entre les serveurs. Le niveau de
difficulté peut se « régler » :
o En faisant l’hypothèse que tous les serveurs on accès au même
disque contenant les fichiers ou au contraire que chaque serveur
possède une copie de chaque fichier.
o En proposant l’ajout / retrait dynamique de serveurs sur le
répartiteur de charge.
o En optimisant pour que le retour des lectures ne passe pas par le
répartiteur de charge.
Vous pourrez comparer différents algorithmes d’équilibrage de charge.
Tester que votre système interdit bien les écritures concurrentes sur un
même fichier…
