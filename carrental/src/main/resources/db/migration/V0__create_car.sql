CREATE TABLE public.car (
	id int8 GENERATED ALWAYS AS IDENTITY,
    available bool NOT NULL,
	brand varchar(255) NULL,
	model varchar(255) NOT NULL,
	CONSTRAINT car_pk PRIMARY KEY (id)
);