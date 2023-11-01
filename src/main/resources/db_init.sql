CREATE TABLE patient
(
    id      BIGINT       NOT NULL,
    name    VARCHAR(255) NULL,
    family  VARCHAR(255) NULL,
    address VARCHAR(500) NULL,
    phone   VARCHAR(255) NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

CREATE TABLE doctor
(
    id      BIGINT       NOT NULL,
    name    VARCHAR(255) NULL,
    family  VARCHAR(255) NULL,
    address VARCHAR(500) NULL,
    phone   VARCHAR(255) NULL,
    field   VARCHAR(255) NULL,
    CONSTRAINT pk_doctor PRIMARY KEY (id)
);

CREATE TABLE appointment
(
    id         BIGINT   NOT NULL,
    doctor_id  BIGINT   NOT NULL,
    patient_id BIGINT   NOT NULL,
    start_time datetime NULL,
    end_time   datetime NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES doctor (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patient (id);