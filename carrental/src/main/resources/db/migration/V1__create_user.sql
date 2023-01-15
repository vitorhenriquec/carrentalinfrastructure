CREATE TABLE public.user_system (
	id int8 GENERATED ALWAYS AS IDENTITY,
	email varchar(255) NULL,
	password varchar(255) NOT NULL,
	CONSTRAINT user_system_pk PRIMARY KEY (id)
);
