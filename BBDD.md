BASE DE DADES - DATOS:
- url: "jdbc:mariadb://192.168.14.11:3306/ProjecteDAWEquip04"
- Usuari: "root"
- Contrasenya: ""

BASE DE DADES - TABLES:
- Usuaris: [usuari - varchar(255)] [contrasenya - varchar(255)] [nom - varchar(100)] [cognoms - varchar(150)] [imatge - blob] [poblacio - varchar(255)] [email - varchar(255)]
- Pixelart: [usuari - varchar(255)] [idPixelart - int(5) autoincremental] ...
- Pescamines: [usuari - varchar(255)] [idPescamines - int(5) autoincremental] [nivell - varchar(10)] [temps - int(10)]
- Wordle: [usuari - varchar(255)] [paraula - varchar(5)] [idWordle - int(5) autoincremental]
