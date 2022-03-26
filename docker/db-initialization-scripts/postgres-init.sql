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

INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (1, 'European', 'M', 'A', '130**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (2, 'European', 'M', 'A', '130**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (3, 'European', 'M', 'A', '130**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (4, 'American', 'F', 'B', '150**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (5, 'American', 'F', 'B', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (6, 'American', 'F', 'B', '150**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (7, 'Asian', 'M', 'O', '160**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (8, 'Asian', 'M', 'O', '160**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (9, 'Asian', 'M', 'O', '160**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (10, 'African', 'F', 'AB', '450**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (11, 'African', 'F', 'AB', '450**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (12, 'African', 'F', 'AB', '450**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (13, 'Middle East', 'M', 'A', '771**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (14, 'Middle East', 'M', 'A', '771**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (15, 'Middle East', 'M', 'A', '771**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (16, 'European', 'F', 'B', '130**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (17, 'European', 'F', 'B', '130**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (18, 'European', 'F', 'B', '130**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (19, 'American', 'M', 'O', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (20, 'American', 'M', 'O', '150**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (21, 'American', 'M', 'O', '150**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (22, 'Asian', 'F', 'AB', '160**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (23, 'Asian', 'F', 'AB', '160**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (24, 'Asian', 'F', 'AB', '160**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (25, 'African', 'M', 'A', '450**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (26, 'African', 'M', 'A', '450**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (27, 'African', 'M', 'A', '450**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (28, 'Middle East', 'F', 'B', '771**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (29, 'Middle East', 'F', 'B', '771**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (30, 'Middle East', 'F', 'B', '771**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (31, 'European', 'M', 'O', '130**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (32, 'European', 'M', 'O', '130**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (33, 'European', 'M', 'O', '130**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (34, 'American', 'F', 'AB', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (35, 'American', 'F', 'AB', '150**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (36, 'American', 'F', 'AB', '150**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (37, 'Asian', 'M', 'A', '160**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (38, 'Asian', 'M', 'A', '160**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (39, 'Asian', 'M', 'A', '160**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (40, 'African', 'F', 'B', '450**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (41, 'African', 'F', 'B', '450**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (42, 'African', 'F', 'B', '450**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (43, 'Middle East', 'M', 'O', '771**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (44, 'Middle East', 'M', 'O', '771**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (45, 'Middle East', 'M', 'O', '771**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (46, 'European', 'F', 'AB', '130**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (47, 'European', 'F', 'AB', '130**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (48, 'European', 'F', 'AB', '130**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (49, 'American', 'M', 'A', '150**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (50, 'American', 'M', 'A', '150**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (51, 'American', 'M', 'A', '150**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (52, 'Asian', 'F', 'B', '160**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (53, 'Asian', 'F', 'B', '160**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (54, 'Asian', 'F', 'B', '160**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (55, 'African', 'M', 'O', '450**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (56, 'African', 'M', 'O', '450**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (57, 'African', 'M', 'O', '450**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (58, 'Middle East', 'F', 'AB', '771**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (59, 'Middle East', 'F', 'AB', '771**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (60, 'Middle East', 'F', 'AB', '771**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (61, 'European', 'M', 'A', '130**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (62, 'European', 'M', 'A', '130**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (63, 'European', 'M', 'A', '130**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (64, 'American', 'F', 'B', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (65, 'American', 'F', 'B', '150**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (66, 'American', 'F', 'B', '150**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (67, 'Asian', 'M', 'O', '160**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (68, 'Asian', 'M', 'O', '160**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (69, 'Asian', 'M', 'O', '160**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (70, 'African', 'F', 'AB', '450**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (71, 'African', 'F', 'AB', '450**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (72, 'African', 'F', 'AB', '450**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (73, 'Middle East', 'M', 'A', '771**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (74, 'Middle East', 'M', 'A', '771**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (75, 'Middle East', 'M', 'A', '771**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (76, 'European', 'F', 'B', '130**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (77, 'European', 'F', 'B', '130**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (78, 'European', 'F', 'B', '130**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (79, 'American', 'M', 'O', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (80, 'American', 'M', 'O', '150**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (81, 'American', 'M', 'O', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (82, 'Asian', 'F', 'AB', '160**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (83, 'Asian', 'F', 'AB', '160**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (84, 'Asian', 'F', 'AB', '160**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (85, 'African', 'M', 'A', '450**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (86, 'African', 'M', 'A', '450**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (87, 'African', 'M', 'A', '450**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (88, 'Middle East', 'F', 'B', '771**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (89, 'Middle East', 'F', 'B', '771**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (90, 'Middle East', 'F', 'B', '771**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (91, 'European', 'M', 'O', '130**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (92, 'European', 'M', 'O', '130**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (93, 'European', 'M', 'O', '130**', 'Cardiovascular');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (94, 'American', 'F', 'AB', '150**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (95, 'American', 'F', 'AB', '150**', 'Broken Arm');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (96, 'American', 'F', 'AB', '150**', 'HIV');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (97, 'Asian', 'M', 'A', '160**', 'Broken Leg');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (98, 'Asian', 'M', 'A', '160**', 'Diabetes');
INSERT INTO health_data_collection_2 (id, nationality, gender, blood_type, zipCode, disease) VALUES (99, 'Asian', 'M', 'A', '160**', 'Diabetes');
