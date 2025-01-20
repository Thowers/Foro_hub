create table profiles(
    id int auto_increment primary key ,
    nombre varchar(50)
);

create table user_roles(
    id_perfil int,
    id_usuario int,
    constraint fk_usuario foreign key (id_usuario) references users(id),
    constraint fk_roles foreign key (id_perfil) references profile(id)
)