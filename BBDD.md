BASE DE DADES - DATOS:
- url: "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04"
- Usuari: "root"
- Contrasenya: ""

BASE DE DADES - TABLES:
- Usuaris: [id - int(5) autoincremental] [contrasenya - varchar(255)] [nom - varchar(100)] [cognoms - varchar(150)] [imatge - mediumblob] [poblacio - varchar(255)] [email - varchar(255)]
- Pixelart: [id - int(5)] [idPixelart - int(5) autoincremental] ...
- Pescamines: [id - int(5)] [idPescamines - int(5) autoincremental] [nivell - varchar(10)] [temps - int(10)]
- ParaulesWordle: [id - int(5)] [paraula - varchar(5)] [idWordle - int(5) autoincremental]
- PartidesWordle: [id - int(5)] [victoria1 - int(11)] [victoria2 - int(11)] [victoria3 - int(11)] [victoria4 - int(11)] [victoria5 - int(11)] [victoria6 - int(11)] [derrotes - int(11)] 
