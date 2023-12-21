CREATE TABLE "groups"(
    id BIGINT PRIMARY KEY,
    name VARCHAR(5) NOT NULL
);
CREATE TABLE users(
    id BIGINT PRIMARY KEY,
    role VARCHAR(50) NOT NULL,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    login VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    group_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES "groups" (id) ON DELETE SET NULL
);
CREATE TABLE courses(
    id BIGINT PRIMARY KEY,
    name VARCHAR(30)
);
CREATE TABLE teachers_courses(
    teacher_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (teacher_id) REFERENCES "users" (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES "courses" (id) ON DELETE CASCADE
);
CREATE TABLE group_courses(
    group_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES "groups" (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES "courses" (id) ON DELETE CASCADE
);
CREATE TABLE class(
    id BIGINT PRIMARY KEY,
    day VARCHAR(20),
    time VARCHAR(20),
    group_id BIGINT,
    course_id BIGINT,
    teacher_id BIGINT,
    FOREIGN KEY (group_id) REFERENCES "groups" (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES "courses" (id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES "users" (id) ON DELETE SET NULL
);
CREATE SEQUENCE groups_sequence increment 100 owned by groups.id;
CREATE SEQUENCE users_sequence increment 100 owned by users.id;
CREATE SEQUENCE courses_sequence increment 100 owned by courses.id;
CREATE SEQUENCE class_sequence increment 100 owned by class.id;
