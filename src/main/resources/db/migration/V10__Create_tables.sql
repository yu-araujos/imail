-- Criação da tabela t_email com id_email como NUMBER
CREATE TABLE t_email (
                         id_email             NUMBER(10,0) NOT NULL,
                         assunto_email        VARCHAR2(1000 CHAR),
                         destinatario_email   VARCHAR2(100 CHAR) NOT NULL,
                         remetente_email      VARCHAR2(100 CHAR) NOT NULL,
                         corpo_do_email       VARCHAR2(255 CHAR) NOT NULL,
                         dt_receb_email       DATE NOT NULL,
                         t_usuario_id_usuario NUMBER(10,0) NOT NULL
);

ALTER TABLE t_email ADD CONSTRAINT t_email_pk PRIMARY KEY (id_email);

-- Criação da tabela t_marcador com id_marcador como NUMBER
CREATE TABLE t_marcador (
                            id_marcador          NUMBER(10,0) NOT NULL,
                            nm_marcador          VARCHAR2(20 CHAR) NOT NULL,
                            t_usuario_id_usuario NUMBER(10,0)
);

ALTER TABLE t_marcador ADD CONSTRAINT t_marcador_pk PRIMARY KEY (id_marcador);

-- Criação da tabela t_usuario com id_usuario como NUMBER
CREATE TABLE t_usuario (
                           id_usuario       NUMBER(10,0) NOT NULL,
                           nome_usuario     VARCHAR2(100 CHAR),
                           email_usuario    VARCHAR2(100 CHAR) NOT NULL,
                           senha_usuario    VARCHAR2(255 CHAR) NOT NULL,
                           tema_cor_usuario VARCHAR2(20 CHAR) NOT NULL
);

ALTER TABLE t_usuario ADD CONSTRAINT t_usuario_pk PRIMARY KEY (id_usuario);

-- Adição das constraints de chave estrangeira
ALTER TABLE t_email
    ADD CONSTRAINT t_email_t_usuario_fk FOREIGN KEY (t_usuario_id_usuario)
        REFERENCES t_usuario(id_usuario);

ALTER TABLE t_marcador
    ADD CONSTRAINT t_marcador_t_usuario_fk FOREIGN KEY (t_usuario_id_usuario)
        REFERENCES t_usuario(id_usuario);

-- Criação da tabela de junção entre t_marcador e t_email
CREATE TABLE t_marcador_email (
                                  id_marcador NUMBER(10,0) NOT NULL,
                                  id_email    NUMBER(10,0) NOT NULL,
                                  PRIMARY KEY (id_marcador, id_email),
                                  CONSTRAINT fk_marcador FOREIGN KEY (id_marcador) REFERENCES t_marcador(id_marcador) ON DELETE CASCADE,
                                  CONSTRAINT fk_email FOREIGN KEY (id_email) REFERENCES t_email(id_email) ON DELETE CASCADE
);
