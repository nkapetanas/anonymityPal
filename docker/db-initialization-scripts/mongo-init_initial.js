db = db.getSiblingDB('health_data_db_1');

db.createCollection('health_data_collection_1');

db.health_data_collection_1.insertMany([
    {
        zip: '130**',
        age: '20-30',
        marital_status: 'single',
        health_condition: 'Heart Disease'
    },
    {
        zip: '130**',
        age: '20-30',
        marital_status: 'married',
        health_condition: 'HIV'
    },
    {
        zip: '130**',
        age: '20-30',
        marital_status: 'single',
        health_condition: 'Diabetes'
    },
    {
        zip: '771**',
        age: '30-40',
        marital_status: 'single',
        health_condition: 'Diabetes'
    },
    {
        zip: '450**',
        age: '40-50',
        marital_status: 'single',
        health_condition: 'Heart Disease'
    },
    {
        zip: '150**',
        age: '30-40',
        marital_status: 'single',
        health_condition: 'Broken Arm'
    },
    {
        zip: '160**',
        age: '50-60',
        marital_status: 'married',
        health_condition: 'Eye Disease'
    },
    {
        zip: '450**',
        age: '40-50',
        marital_status: 'single',
        health_condition: 'Diabetes'
    },
    {
        zip: '160**',
        age: '50-60',
        marital_status: 'married',
        health_condition: 'Heart Disease'
    },
    {
        zip: '150**',
        age: '60-70',
        marital_status: 'married',
        health_condition: 'Broken Leg'
    },
    {
        zip: '450**',
        age: '40-50',
        marital_status: 'single',
        health_condition: 'HIV'
    },
    {
        zip: '771**',
        age: '30-40',
        marital_status: 'single',
        health_condition: 'Cancer'
    },
    {
        zip: '130**',
        age: '20-30',
        marital_status: 'married',
        health_condition: 'Diabetes'
    },
    {
        zip: '771**',
        age: '30-40',
        marital_status: 'single',
        health_condition: 'HIV'
    },
    {
        zip: '160**',
        age: '50-60',
        marital_status: 'married',
        health_condition: 'Broken Arm'
    },
    {
        zip: '150**',
        age: '60-70',
        marital_status: 'married',
        health_condition: 'Broken Arm'
    },
    {
        zip: '150**',
        age: '30-40',
        marital_status: 'married',
        health_condition: 'Broken Pelvis'
    }
]);