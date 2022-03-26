CREATE
USER nick WITH PASSWORD 'nick';
CREATE
DATABASE health_data_db_2;
GRANT ALL PRIVILEGES ON DATABASE
health_data_db_2 TO nick;

CREATE TABLE health_data_collection_2
(
    id               SERIAL PRIMARY KEY,
    nationality      VARCHAR(255) NOT NULL,
    gender              VARCHAR(2)   NOT NULL,
    blood_type       VARCHAR(2)   NOT NULL,
    zipCode          VARCHAR(255) NOT NULL,
    disease VARCHAR(255)
);

INSERT INTO health_data_collection_2
    (id, nationality, gender, blood_type, zipCode, disease)
VALUES (1, 'European', 'M', 'A', '130**', 'Cancer'),
       (2, 'European', 'M', 'B', '150**', 'Broken Arm'),
       (3, 'European', 'M', 'A', '130**', 'Diabetes'),
       (4, 'Asian', 'F', 'A', '450**', 'Broken Arm'),
       (5, 'American', 'M', 'O', '771**', 'Diabetes'),
       (6, 'European', 'M', 'B', '150**', 'HIV'),
       (7, 'European', 'F', 'AB', '150**', 'Cancer'),
       (8, 'American', 'M', 'O', '771**', 'Cancer'),
       (9, 'European', 'M', 'A', '130**', 'Heart Disease'),
       (10, 'European', 'F', 'O', '160**', 'Heart Disease'),
       (11, 'American', 'M', 'O', '771**', 'Cancer'),
       (12, 'European', 'F', 'A', '160**', 'Heart Disease'),
       (13, 'European', 'F', 'AB', '150**', 'Diabetes'),
       (14, 'Asian', 'F', 'A', '450**', 'Broken Leg'),
       (15, 'European', 'F', 'O', '160**', 'HIV'),
       (16, 'European', 'F', 'A', '160**', 'Broken Arm')
;