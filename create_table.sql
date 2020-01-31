create table PATIENT
(
    ID               BIGINT identity
                constraint PATIENT_PK
                          primary key,
    NAME       CHARACTER(50) not null,
    SURNAME    CHARACTER(50) not null,
    PATRONYMIC CHARACTER(50) not null,
    PHONE      CHARACTER(10) not null
);

insert into PATIENT (id, name, patronymic, surname, phone) values (1,'Artem','Aleksandrovich','Volkov','89279999999');
insert into PATIENT (id, name, patronymic, surname, phone) values (2,'Grigoriy','Vazgenovich','Dydkov','89279494875');
insert into PATIENT (id, name, patronymic, surname, phone) values (3,'Oleg','Gennadievich','Dobryi','89649857854');
insert into PATIENT (id, name, patronymic, surname, phone) values (4,'Vasya','Konstantinovich','Ivanov','89096547789');
insert into PATIENT (id, name, patronymic, surname, phone) values (5,'Alina','Vitalievna','Zaporozhec','89647778544');

create table DOCTOR
(
    ID                    BIGINT identity
                     constraint DOCTOR_PK
                              primary key,
    NAME           CHARACTER(50) not null,
    SURNAME        CHARACTER(50) not null,
    PATRONYMIC     CHARACTER(50) not null,
    SPECIALIZATION CHARACTER(50) not null
);

insert into DOCTOR (id, name, patronymic, surname, specialization) values (1,'Albert','Albertovich','Traxtenberg','PSYCHOTHERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (2,'Alexey','Alexeevich','Glushkov','THERAPIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (3,'Anastasia','Valerevna','Mishkina','DENTIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (4,'Ekaterina','Alexeevna','Somova','NEUROLOGIST');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (5,'Albina','Dmitryevna','Kondashova','PEDIATRICIAN');
insert into DOCTOR (id, name, patronymic, surname, specialization) values (6,'Jorik','Vazgenovich','Novikov','SURGEON');

create table RECIPE
(
    ID                  BIGINT identity
               constraint TABLE_NAME_PK
                            primary key,
    DESCRIPTION   VARCHAR(200) not null,
    PATIENT_ID          BIGINT not null
        constraint RECIPE_PATIENT_ID_FK
                     references PATIENT,
    DOCTOR_ID           BIGINT not null
         constraint RECIPE_DOCTOR_ID_FK
                      references DOCTOR,
    DATE_CREATION                  DATE,
    VALIDITY                       DATE,
    PRIORITY      CHARACTER(20) not null
);

insert into RECIPE (id, description, patient_id, doctor_id, date_creation, validity, priority) values (1, 'Drinking vodka every day', 1, 1, '2020-01-29', '2020-02-29','NORMAL');



