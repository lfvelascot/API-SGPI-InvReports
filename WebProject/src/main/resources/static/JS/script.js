function displayProyectos(){
    document.getElementById("menuI").setAttribute("src","/inicio/proyectos.html");
}

function displayGrupos(){
    document.getElementById("menuI").setAttribute("src","/inicio/grupos_investigacion.html");
}

function displaySemilleros(){
    document.getElementById("menuI").setAttribute("src","/inicio/semilleros.html");
}

function menuGrupos(){
    document.getElementById("menuS").setAttribute("src","/menu_sistema/grupos_investigacion/modulos_gi.html")
}

function menuSemilleros(){
    document.getElementById("menuS").setAttribute("src","/menu_sistema/semilleros/modulos_sem.html")
}

function menuProyectos(){
    document.getElementById("menuS").setAttribute("src","/menu_sistema/proyectos/modulos_proy.html")
}

function menuUsuarios(){
    document.getElementById("menuS").setAttribute("src","/menu_sistema/gestion_usuarios/modulo_usuarios.html")
}
