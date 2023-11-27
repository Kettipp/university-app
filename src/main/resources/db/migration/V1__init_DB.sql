DROP TABLE IF EXISTS "groups" CASCADE;
CREATE TABLE IF NOT EXISTS "groups"(
    id SERIAL PRIMARY KEY,
    name VARCHAR(5) NOT NULL
);
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
);
DROP TABLE IF EXISTS courses CASCADE;
CREATE TABLE IF NOT EXISTS courses(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(30)
);
DROP TABLE IF EXISTS teacher_courses CASCADE;
CREATE TABLE IF NOT EXISTS teachers_courses(
    teacher_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES "users" (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES "courses" (id) ON DELETE CASCADE
);
DROP TABLE  IF EXISTS group_courses CASCADE;
CREATE TABLE IF NOT EXISTS group_courses(
    group_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES "groups" (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES "courses" (id) ON DELETE CASCADE
);
DROP TABLE IF EXISTS class CASCADE;
CREATE TABLE IF NOT EXISTS class(
    id SERIAL PRIMARY KEY,
    day VARCHAR(20),
    time BIGINT,
    group_id BIGINT,
    course_id BIGINT,
    teacher_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES "groups" (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES "courses" (id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES "users" (id) ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS groups_sequence increment 100 owned by groups.id;
CREATE SEQUENCE IF NOT EXISTS users_sequence increment 100 owned by users.id;
CREATE SEQUENCE IF NOT EXISTS courses_sequence increment 100 owned by courses.id;
CREATE SEQUENCE IF NOT EXISTS class_sequence increment 100 owned by class.id;
